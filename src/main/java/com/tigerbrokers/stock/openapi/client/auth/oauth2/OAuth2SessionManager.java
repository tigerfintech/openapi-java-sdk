package com.tigerbrokers.stock.openapi.client.auth.oauth2;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * Manages OAuth2 authorization, token persistence, and token refresh.
 *
 * <p>Authorization requests all scopes advertised by the authorization server. Access-token
 * refresh is thread-safe and single-flight for concurrent callers.</p>
 */
public class OAuth2SessionManager {

  /** Environment-variable override for the default issuer. */
  public static final String ENV_ISSUER = "TIGEROPEN_OAUTH2_ISSUER";

  /** Production authorization server. */
  private static final String BUILTIN_ISSUER = "https://openapi-oauth2.tigerfintech.com";

  /** Access-token refresh-ahead window. */
  private static final long DEFAULT_REFRESH_AHEAD_MILLIS = 300_000L;

  /**
   * Remaining lifetime below which a token is no longer handed out.
   *
   * <p>Between this and {@link #DEFAULT_REFRESH_AHEAD_MILLIS} a refresh is due but the current
   * token still works, so callers get it immediately rather than waiting on an exchange that
   * may take tens of seconds. Below this the token can expire mid-request, so a caller has to
   * wait for a new one.</p>
   */
  private static final long MIN_USABLE_REMAINING_MILLIS = 30_000L;

  /** Authorization completion timeout in minutes. */
  private static final long DEFAULT_AUTHORIZE_TIMEOUT_MINUTES = 5;

  /** Default device-flow polling interval from RFC 8628, in seconds. */
  public static final long DEFAULT_DEVICE_POLL_INTERVAL_SECONDS = 5;

  /** Polling interval increment for an RFC 8628 {@code slow_down} response. */
  private static final long DEVICE_SLOW_DOWN_INCREMENT_SECONDS = 5;

  /** Default loopback port for clients with an explicitly registered redirect URI. */
  public static final int DEFAULT_MANUAL_CALLBACK_PORT = 18888;

  private static final String GRANT_TYPE_DEVICE_CODE =
      "urn:ietf:params:oauth:grant-type:device_code";

  private static final SecureRandom RANDOM = new SecureRandom();

  private final String issuer;

  /** Client name used for dynamic registration. */
  private static final String CLIENT_NAME = "Tiger Java SDK";

  /** Configured loopback port, or {@code 0} when unspecified. */
  private final int callbackPort;
  private final OAuth2TokenStore store;
  private final long authorizeTimeoutMinutes;

  /** Explicit client ID, or {@code null} when dynamic registration is used. */
  private final String explicitClientId;

  /** The clientId from dynamic registration or read back from {@code clients/}. Always null in explicit mode. */
  private volatile String registeredClientId;

  private volatile OAuth2Metadata metadata;
  private volatile OAuth2Token token;

  /** The full scope parsed from the metadata, cached after the first parse. */
  private volatile String resolvedScopes;

  /**
   * Guards token exchange so concurrent callers produce one request, not one each.
   *
   * <p>An explicit lock rather than {@code synchronized} because a caller holding a still-usable
   * token needs to ask whether a refresh is already running without waiting for it -- see
   * {@link #getAccessToken()}. Reentrant, since {@link #refreshAfterUnauthorized(String)} calls
   * {@link #refresh(OAuth2Token)} while holding it.</p>
   */
  private final ReentrantLock refreshLock = new ReentrantLock();

  private OAuth2SessionManager(Builder builder) {
    if (builder.issuer == null || builder.issuer.isEmpty()) {
      throw new IllegalArgumentException("issuer is required");
    }
    this.issuer = trimTrailingSlash(builder.issuer);
    // Resolve advertised scopes lazily so construction remains available offline.
    this.callbackPort = builder.callbackPort;
    this.explicitClientId = isBlank(builder.clientId) ? null : builder.clientId.trim();
    this.store = builder.store != null
        ? builder.store : new OAuth2TokenStore(builder.home, builder.surface);
    this.authorizeTimeoutMinutes = builder.authorizeTimeoutMinutes;
  }

  public static Builder builder() {
    return new Builder();
  }

  /** The default issuer: the environment variable takes precedence, otherwise the built-in address. */
  public static String defaultIssuer() {
    String env = System.getenv(ENV_ISSUER);
    return env == null || env.isEmpty() ? BUILTIN_ISSUER : env;
  }

  /**
   * Authorizes with default settings and dynamic client registration when no reusable token
   * exists.
   *
   * <p>The callback receives the authorization URL; this method does not open a browser.</p>
   *
   * @param onAuthorizationUrl callback invoked when interactive authorization is required
   */
  public static OAuth2SessionManager loginWithDefaults(
      Consumer<String> onAuthorizationUrl) {
    OAuth2SessionManager sessions = builder().build();
    sessions.loginIfNeeded(onAuthorizationUrl);
    return sessions;
  }

  /**
   * Authorizes through the device flow with default settings and dynamic client registration
   * when no reusable token exists.
   *
   * @param onDeviceAuthorization callback receiving the user code and verification URI
   */
  public static OAuth2SessionManager loginWithDeviceCodeDefaults(
      Consumer<OAuth2DeviceAuthorization> onDeviceAuthorization) {
    OAuth2SessionManager sessions = builder().build();
    sessions.loginWithDeviceCodeIfNeeded(onDeviceAuthorization);
    return sessions;
  }

  /**
   * Uses the existing local authorization if it is usable, otherwise runs a full loopback +
   * PKCE authorization.
   *
   * <p>Blocks until authorization completes or times out.</p>
   *
   * @param onAuthorizationUrl callback that receives the authorization URL, which the user needs
   *     to open in a browser
   */
  public OAuth2Token loginIfNeeded(Consumer<String> onAuthorizationUrl) {
    OAuth2Token reusable = reusableToken();
    if (reusable != null) {
      return reusable;
    }
    return login(onAuthorizationUrl);
  }

  /**
   * Forces a full authorization, ignoring any existing local token.
   */
  public OAuth2Token login(Consumer<String> onAuthorizationUrl) {
    if (onAuthorizationUrl == null) {
      throw new IllegalArgumentException("onAuthorizationUrl is required");
    }
    OAuth2Metadata meta = metadata();
    // Generate an RFC 7636-compliant verifier and a high-entropy state value.
    String verifier = randomUrlSafe(64);
    String challenge = s256(verifier);
    String state = randomUrlSafe(24);

    // Start the listener before exposing the authorization URL.
    try (LoopbackReceiver receiver = new LoopbackReceiver(effectiveCallbackPort())) {
      String redirectUri = receiver.getRedirectUri();
      String effectiveClientId = resolveClientId(meta);

      // Request the complete advertised scope set in both client registration modes.
      String url = meta.getAuthorizationEndpoint() + "?"
          + query("response_type", "code")
          + "&" + query("client_id", effectiveClientId)
          + "&" + query("redirect_uri", redirectUri)
          + "&" + query("state", state)
          + "&" + query("scope", scopes())
          + "&" + query("code_challenge", challenge)
          + "&" + query("code_challenge_method", "S256");

      onAuthorizationUrl.accept(url);

      Map<String, String> callback =
          receiver.await(authorizeTimeoutMinutes, TimeUnit.MINUTES);

      if (callback.containsKey("error")) {
        throw new OAuth2Exception(OAuth2Exception.Category.CALLBACK_FAILED,
            "authorization denied: " + callback.get("error"));
      }
      String code = callback.get("code");
      if (code == null) {
        throw new OAuth2Exception(OAuth2Exception.Category.CALLBACK_FAILED,
            "callback did not carry an authorization code");
      }
      // state must be validated to prevent cross-site request forgery
      if (!state.equals(callback.get("state"))) {
        throw new OAuth2Exception(OAuth2Exception.Category.CALLBACK_FAILED,
            "state mismatch, discarding callback");
      }

      Map<String, String> form = new LinkedHashMap<>();
      form.put("grant_type", "authorization_code");
      form.put("code", code);
      form.put("redirect_uri", redirectUri);
      form.put("client_id", effectiveClientId);
      form.put("code_verifier", verifier);

      OAuth2Token fresh;
      try {
        fresh = exchange(meta.getTokenEndpoint(), form, effectiveClientId);
      } catch (RejectedGrant rejected) {
        // A refused authorization code says nothing about the persisted token, so that is left
        // alone -- a failed re-authorization must not cost the user a working one. A refused
        // client registration does have to go, or every later attempt reuses it.
        forgetRejectedRegistration(rejected);
        throw rejected;
      }
      persist(fresh);
      return fresh;
    }
  }

  /**
   * Uses a reusable token or performs OAuth2 device authorization as defined by RFC 8628.
   *
   * <p>Device authorization is intended for environments that cannot receive a loopback browser
   * callback. It lacks the redirect binding provided by loopback authorization with PKCE and is
   * more susceptible to phishing.</p>
   *
   * @param onDeviceAuthorization callback receiving the user code and verification URI
   */
  public OAuth2Token loginWithDeviceCodeIfNeeded(
      Consumer<OAuth2DeviceAuthorization> onDeviceAuthorization) {
    OAuth2Token reusable = reusableToken();
    if (reusable != null) {
      return reusable;
    }
    return loginWithDeviceCode(onDeviceAuthorization);
  }

  /**
   * Forces a device-flow authorization, ignoring any existing local token.
   *
   * <p>See {@link #loginWithDeviceCodeIfNeeded(Consumer)} for details.</p>
   */
  public OAuth2Token loginWithDeviceCode(
      Consumer<OAuth2DeviceAuthorization> onDeviceAuthorization) {
    if (onDeviceAuthorization == null) {
      throw new IllegalArgumentException("onDeviceAuthorization is required");
    }
    OAuth2Metadata meta = metadata();
    if (meta.getDeviceAuthorizationEndpoint() == null) {
      throw new OAuth2Exception(OAuth2Exception.Category.METADATA_UNAVAILABLE,
          "AS does not expose device_authorization_endpoint: " + issuer);
    }
    String effectiveClientId = resolveClientId(meta);

    OAuth2DeviceAuthorization device =
        requestDeviceAuthorization(meta.getDeviceAuthorizationEndpoint(), effectiveClientId);

    // Deliver the user code before polling begins.
    onDeviceAuthorization.accept(device);

    OAuth2Token fresh = pollForDeviceToken(meta.getTokenEndpoint(), device, effectiveClientId);
    persist(fresh);
    return fresh;
  }

  /**
   * Returns a usable access token and refreshes it when required.
   *
   * <p>This method does not start interactive authorization. Missing or invalid authorization
   * raises {@link OAuth2Exception.Category#REAUTHORIZATION_REQUIRED}.</p>
   *
   * <p>A refresh being due does not make the caller wait for one. While the current token still
   * has {@link #MIN_USABLE_REMAINING_MILLIS} of life, an exchange already running elsewhere is
   * left to finish and the current token is returned straight away. Waiting would buy nothing --
   * the token in hand works -- and would charge an unrelated caller for a token exchange that
   * can take tens of seconds against a slow authorization server.</p>
   */
  public String getAccessToken() {
    OAuth2Token current = currentToken();
    if (current == null) {
      throw new OAuth2Exception(OAuth2Exception.Category.REAUTHORIZATION_REQUIRED,
          "not authorized yet, call loginIfNeeded() first");
    }
    if (!current.needsRefresh(DEFAULT_REFRESH_AHEAD_MILLIS)) {
      return current.getAccessToken();
    }
    if (current.remainingMillis() > MIN_USABLE_REMAINING_MILLIS) {
      // Refresh due, token still good. Take on the exchange only if nobody else has it.
      if (!refreshLock.tryLock()) {
        return current.getAccessToken();
      }
      try {
        return refresh(current).getAccessToken();
      } finally {
        refreshLock.unlock();
      }
    }
    // Too close to expiry to hand out: this caller has to wait for a usable token.
    return refresh(current).getAccessToken();
  }

  /**
   * Whether asking for a token right now would trigger a refresh. Reads memory only.
   *
   * <p>Exists so that no other component needs a refresh-ahead window of its own. A second copy
   * of that window silently couples to this one -- whoever later tunes either value changes
   * which side wins the race to refresh first, with no compiler error and no failing test to
   * show it.</p>
   */
  public boolean shouldRefreshSoon() {
    OAuth2Token current = currentToken();
    return current != null && current.needsRefresh(DEFAULT_REFRESH_AHEAD_MILLIS);
  }

  /**
   * Exchanges the refresh_token for a new access token.
   *
   * <p>Single-flight: among concurrent callers only one actually hits the token endpoint, the
   * rest wait for the result.</p>
   *
   * @param failed the token that triggered the refresh; if another thread already replaced it,
   *     the new one is returned directly
   */
  public OAuth2Token refresh(OAuth2Token failed) {
    refreshLock.lock();
    try {
      OAuth2Token current = this.token;
      // Another thread already refreshed -- compare access token values, not timestamps
      if (current != null && failed != null
          && !equalsToken(current.getAccessToken(), failed.getAccessToken())) {
        return current;
      }
      if (current == null) {
        current = store.loadToken(requireClientId(), issuer);
      }
      if (current == null || !current.hasRefreshToken()) {
        throw new OAuth2Exception(OAuth2Exception.Category.REAUTHORIZATION_REQUIRED,
            "no refresh_token available, re-authorization required");
      }
      try {
        return exchangeRefreshToken(current);
      } catch (RejectedGrant rejected) {
        return recoverOrGiveUp(current, rejected);
      }
    } finally {
      refreshLock.unlock();
    }
  }

  /**
   * Decides whether a rejected refresh means the authorization is gone, or merely that this
   * process was holding a superseded credential.
   *
   * <p>Refresh tokens rotate: one use invalidates the previous value. So another process sharing
   * this token store may have refreshed first, leaving the value we just presented legitimately
   * dead while the authorization itself is perfectly alive -- and its replacement already on
   * disk. Treating that rejection as terminal would delete a working token and force the user
   * through the browser again, and because it also wipes the shared credential it would take
   * every other user of this session down with it.</p>
   *
   * <p>So the store is re-read before anything is deleted. Only a rejection that survives the
   * newest stored credential is accepted as the end of the authorization.</p>
   */
  private OAuth2Token recoverOrGiveUp(OAuth2Token rejectedToken, RejectedGrant rejected) {
    OAuth2Token stored = reloadFromStore(rejectedToken.getClientId());
    boolean superseded = stored != null
        && !equalsToken(stored.getAccessToken(), rejectedToken.getAccessToken());
    if (!superseded) {
      // Nothing newer exists, so the rejection is about the current state of the authorization.
      forgetRejectedAuthorization(rejected, rejectedToken.getClientId());
      throw rejected;
    }

    ApiLogger.info("refresh was rejected but the token store holds a newer credential,"
        + " adopting it. clientId:{}", rejectedToken.getClientId());
    this.token = stored;
    if (!stored.needsRefresh(DEFAULT_REFRESH_AHEAD_MILLIS)) {
      // Whoever wrote it already did the work; no second request needed.
      return stored;
    }
    if (!stored.hasRefreshToken()) {
      forgetRejectedAuthorization(rejected, rejectedToken.getClientId());
      throw rejected;
    }
    try {
      return exchangeRefreshToken(stored);
    } catch (RejectedGrant secondRejection) {
      // The newest credential on disk was refused too: the authorization really is gone.
      forgetRejectedAuthorization(secondRejection, stored.getClientId());
      throw secondRejection;
    }
  }

  /** Exchanges {@code base}'s refresh token for a new access token and persists the result. */
  private OAuth2Token exchangeRefreshToken(OAuth2Token base) {
    Map<String, String> form = new LinkedHashMap<>();
    form.put("grant_type", "refresh_token");
    form.put("refresh_token", base.getRefreshToken());
    form.put("client_id", base.getClientId());

    OAuth2Token refreshed = exchange(metadata().getTokenEndpoint(), form, base.getClientId());

    // Preserve the current refresh token if the authorization server omits a replacement.
    if (!refreshed.hasRefreshToken()) {
      refreshed.setRefreshToken(base.getRefreshToken());
    }
    persist(refreshed);
    return refreshed;
  }

  /**
   * Reads the stored token straight from disk, bypassing the in-memory copy.
   *
   * <p>The cached token is exactly what is under suspicion here, so it cannot be consulted.</p>
   *
   * @return the stored token, or null when there is none or it cannot be read
   */
  private OAuth2Token reloadFromStore(String clientId) {
    if (clientId == null) {
      return null;
    }
    try {
      return store.loadToken(clientId, issuer);
    } catch (RuntimeException e) {
      // An unreadable store says nothing either way about whether the grant is still alive,
      // so fall through to treating the rejection at face value.
      ApiLogger.warn("could not re-read the token store while checking a rejected refresh:{}",
          e.getMessage());
      return null;
    }
  }

  /**
   * Refreshes after an unauthorized response using the access token applied to the request.
   *
   * <p>Concurrent responses for an already replaced token reuse the current token, ensuring a
   * single-flight refresh.</p>
   *
   * @param failedAccessToken access token applied to the unauthorized request
   * @return current usable token
   * @throws OAuth2Exception when refresh fails
   */
  public OAuth2Token refreshAfterUnauthorized(String failedAccessToken) {
    refreshLock.lock();
    try {
      OAuth2Token current = currentToken();
      // Reuse a token already replaced by another thread.
      if (current != null && failedAccessToken != null
          && !equalsToken(current.getAccessToken(), failedAccessToken)) {
        return current;
      }
      // The lock preserves the token comparison until refresh completes.
      return refresh(current);
    } finally {
      refreshLock.unlock();
    }
  }

  /**
   * Ends authorization by attempting RFC 7009 revocation and removing the local token.
   *
   * <p>The dynamic client registration is retained. Local token removal occurs even if server
   * revocation fails.</p>
   *
   * @return {@code true} when server revocation succeeds; {@code false} when only local state is
   *     cleared
   */
  public boolean logout() {
    String id = knownClientId();
    if (id == null && this.token != null) {
      id = this.token.getClientId();
    }
    boolean revoked = revoke(currentToken(), id);
    if (id != null) {
      store.deleteToken(id);
    }
    this.token = null;
    ApiLogger.info("oauth2 logged out. clientId:{}, serverRevoked:{}", id, revoked);
    return revoked;
  }

  /**
   * Attempts server-side token revocation without failing local logout.
   *
   * <p>The refresh token is preferred so the authorization server can invalidate associated
   * access tokens as described by RFC 7009. An unknown or previously revoked token may still
   * produce a successful response under RFC 7009.</p>
   */
  private boolean revoke(OAuth2Token current, String clientId) {
    if (current == null || clientId == null) {
      return false;
    }
    String value = current.hasRefreshToken() ? current.getRefreshToken() : current.getAccessToken();
    String hint = current.hasRefreshToken() ? "refresh_token" : "access_token";
    if (isBlank(value)) {
      return false;
    }
    try {
      OAuth2Metadata meta = metadata();
      String endpoint = meta.getRevocationEndpoint();
      if (isBlank(endpoint)) {
        // RFC 8414 defines the revocation endpoint as optional.
        ApiLogger.warn("AS advertises no revocation_endpoint, logging out locally only");
        return false;
      }
      Map<String, String> form = new LinkedHashMap<>();
      form.put("token", value);
      form.put("token_type_hint", hint);
      form.put("client_id", clientId);
      int status = OAuth2HttpUtils.postFormForStatus(endpoint, form);
      if (status >= 200 && status < 300) {
        return true;
      }
      // 401/400 here means the AS refused the client, not that the token is fine
      ApiLogger.warn("oauth2 revoke rejected, httpStatus:{}, logging out locally only", status);
      return false;
    } catch (OAuth2Exception e) {
      ApiLogger.warn("oauth2 revoke failed:{}, logging out locally only", e.getMessage());
      return false;
    } catch (RuntimeException e) {
      ApiLogger.warn("oauth2 revoke error:{}, logging out locally only", e.getMessage());
      return false;
    }
  }

  /** The current state, with no side effects: no refresh, no authorization, no requests. */
  public OAuth2Token status() {
    return currentToken();
  }

  /** Whether there is a non-expired authorization. Sends no requests. */
  public boolean isAuthorized() {
    OAuth2Token current = currentToken();
    return current != null && current.remainingMillis() > 0;
  }

  public String getIssuer() {
    return issuer;
  }

  /**
   * The scope authorization will request, i.e. everything the AS supports. Same in both modes.
   *
   * <p>The first call triggers a metadata request; later calls use the cache.</p>
   *
   * <p>This is the range being <i>requested</i>, not necessarily what is granted -- for the
   * actual range see {@code status().getScope()}, which the AS reports in the token
   * response.</p>
   */
  public String getScopes() {
    return scopes();
  }

  /** The loopback port this authorization uses; 0 means the system assigns one. */
  public int getCallbackPort() {
    return effectiveCallbackPort();
  }

  /**
   * Returns the current client ID without performing network operations.
   *
   * @return client ID, or {@code null} before dynamic registration
   */
  public String getClientId() {
    return knownClientId();
  }

  /** Whether the client_id was passed explicitly by the application (true) or dynamically registered (false). */
  public boolean hasExplicitClientId() {
    return explicitClientId != null;
  }

  /**
   * Whether the local token is directly usable, refreshing it if necessary.
   *
   * <p>Returns null when it cannot be refreshed, leaving the caller to run a full
   * authorization.</p>
   */
  private OAuth2Token reusableToken() {
    OAuth2Token existing = currentToken();
    if (existing == null) {
      return null;
    }
    if (!existing.needsRefresh(DEFAULT_REFRESH_AHEAD_MILLIS)) {
      return existing;
    }
    if (existing.hasRefreshToken()) {
      try {
        return refresh(existing);
      } catch (OAuth2Exception e) {
        if (e.getCategory() != OAuth2Exception.Category.REAUTHORIZATION_REQUIRED) {
          throw e;
        }
        ApiLogger.info("refresh token no longer valid, re-authorizing");
      }
    }
    return null;
  }

  /**
   * Returns the configured or persisted client ID without performing network operations.
   *
   * @return client ID, or {@code null} before dynamic registration
   */
  private String knownClientId() {
    if (explicitClientId != null) {
      return explicitClientId;
    }
    String cached = this.registeredClientId;
    if (cached != null) {
      return cached;
    }
    String saved = store.loadClientId(issuer);
    if (saved != null) {
      this.registeredClientId = saved;
    }
    return saved;
  }

  private OAuth2Token currentToken() {
    OAuth2Token cached = this.token;
    if (cached != null) {
      return cached;
    }
    String id = knownClientId();
    if (id == null) {
      return null;
    }
    OAuth2Token loaded = store.loadToken(id, issuer);
    if (loaded != null) {
      this.token = loaded;
    }
    return loaded;
  }

  private void persist(OAuth2Token fresh) {
    this.token = fresh;
    // Preserve the distinction between explicit and dynamically registered client IDs.
    if (explicitClientId == null) {
      this.registeredClientId = fresh.getClientId();
    }
    store.saveToken(fresh);
  }

  private String requireClientId() {
    String id = knownClientId();
    if (id == null) {
      throw new OAuth2Exception(OAuth2Exception.Category.CLIENT_REGISTRATION_REQUIRED,
          "no client_id available for issuer " + issuer);
    }
    return id;
  }

  /**
   * Selects the configured loopback port.
   *
   * <p>Dynamic registration uses a system-assigned port under RFC 8252. Explicit registration
   * uses {@link #DEFAULT_MANUAL_CALLBACK_PORT} unless a port is configured.</p>
   */
  private int effectiveCallbackPort() {
    if (callbackPort > 0) {
      return callbackPort;
    }
    return explicitClientId != null ? DEFAULT_MANUAL_CALLBACK_PORT : 0;
  }

  /**
   * Returns all scopes advertised in {@code scopes_supported} metadata.
   *
   * <p>RFC 8414 permits omission of this field; this client requires it instead of substituting
   * a hardcoded scope list.</p>
   */
  private String scopes() {
    String cached = this.resolvedScopes;
    if (cached != null) {
      return cached;
    }
    List<String> supported = metadata().getScopesSupported();
    if (supported == null || supported.isEmpty()) {
      throw new OAuth2Exception(OAuth2Exception.Category.METADATA_UNAVAILABLE,
          "AS did not advertise scopes_supported: " + issuer);
    }
    StringBuilder sb = new StringBuilder();
    for (String s : supported) {
      if (s != null && !s.trim().isEmpty()) {
        if (sb.length() > 0) {
          sb.append(' ');
        }
        sb.append(s.trim());
      }
    }
    String all = normalizeScopes(sb.toString());
    this.resolvedScopes = all;
    ApiLogger.info("requesting all scopes supported by AS: {}", all);
    return all;
  }

  private OAuth2Metadata metadata() {
    OAuth2Metadata cached = this.metadata;
    if (cached != null) {
      return cached;
    }
    String url = issuer + "/.well-known/oauth-authorization-server";
    JSONObject json = OAuth2HttpUtils.getJson(url);
    OAuth2Metadata meta = json.toJavaObject(OAuth2Metadata.class);
    if (meta == null || meta.getAuthorizationEndpoint() == null
        || meta.getTokenEndpoint() == null) {
      throw new OAuth2Exception(OAuth2Exception.Category.METADATA_UNAVAILABLE,
          "metadata missing authorization_endpoint or token_endpoint: " + url);
    }
    this.metadata = meta;
    return meta;
  }

  /**
   * Resolves an explicit or persisted client ID, or performs dynamic registration.
   *
   * <p>Explicit client IDs are not read from or written to registration storage. Dynamic
   * registrations are persisted and reused while their granted scopes remain sufficient.</p>
   */
  private String resolveClientId(OAuth2Metadata meta) {
    if (explicitClientId != null) {
      return explicitClientId;
    }
    OAuth2ClientRegistration saved = store.loadClientRegistration(issuer);
    if (saved != null && saved.getClientId() != null
        && scopesCover(saved.getRegisteredScopes(), scopes())) {
      this.registeredClientId = saved.getClientId();
      return this.registeredClientId;
    }
    if (saved != null) {
      // Register again when the stored client no longer covers the advertised scopes.
      ApiLogger.info("registered scopes no longer cover all scopes supported by AS,"
          + " re-registering");
    }
    if (meta.getRegistrationEndpoint() == null) {
      throw new OAuth2Exception(OAuth2Exception.Category.CLIENT_REGISTRATION_REQUIRED,
          "no client_id provided and AS does not expose registration_endpoint");
    }
    return register(meta.getRegistrationEndpoint());
  }

  private String register(String registrationEndpoint) {
    Map<String, Object> payload = new LinkedHashMap<>();
    payload.put("client_name", CLIENT_NAME);
    // RFC 8252 permits dynamic loopback redirects to use a system-assigned port.
    String registeredRedirect = callbackPort > 0
        ? "http://127.0.0.1:" + callbackPort + "/callback"
        : "http://127.0.0.1/callback";
    payload.put("redirect_uris", Collections.singletonList(registeredRedirect));
    // Register both grants so one persisted client supports loopback and device authorization.
    payload.put("grant_types", Arrays.asList(
        "authorization_code", "refresh_token", GRANT_TYPE_DEVICE_CODE));
    payload.put("response_types", Collections.singletonList("code"));
    payload.put("token_endpoint_auth_method", "none");
    // Registration omits scope so the authorization server determines the permitted range.

    JSONObject json = OAuth2HttpUtils.postJson(registrationEndpoint, payload);
    String newClientId = json.getString("client_id");
    if (newClientId == null) {
      throw new OAuth2Exception(OAuth2Exception.Category.CLIENT_REGISTRATION_REQUIRED,
          "dynamic registration rejected: " + json.getString("error")
              + " " + json.getString("error_description"));
    }
    OAuth2ClientRegistration reg = new OAuth2ClientRegistration();
    reg.setIssuer(issuer);
    reg.setClientId(newClientId);
    reg.setClientName(CLIENT_NAME);
    // Persist the granted scope, falling back to the advertised scope when omitted.
    String grantedAtRegistration = json.getString("scope");
    reg.setRegisteredScopes(
        isBlank(grantedAtRegistration) ? scopes() : normalizeScopes(grantedAtRegistration));
    reg.setClientIdIssuedAt(System.currentTimeMillis());
    store.saveClientRegistration(reg);

    this.registeredClientId = newClientId;
    ApiLogger.info("registered oauth2 client, clientId:{}", newClientId);
    return newClientId;
  }

  /** Starts device authorization to obtain the user_code and device_code. */
  private OAuth2DeviceAuthorization requestDeviceAuthorization(
      String deviceEndpoint, String usedClientId) {
    Map<String, String> form = new LinkedHashMap<>();
    form.put("client_id", usedClientId);
    form.put("scope", scopes());

    JSONObject json = OAuth2HttpUtils.postForm(deviceEndpoint, form);
    OAuth2DeviceAuthorization device = json.toJavaObject(OAuth2DeviceAuthorization.class);
    if (device == null || isBlank(device.getDeviceCode()) || isBlank(device.getUserCode())) {
      throw new OAuth2Exception(OAuth2Exception.Category.DEVICE_AUTHORIZATION_FAILED,
          "device authorization failed: " + json.getString("error")
              + " " + json.getString("error_description"));
    }
    if (isBlank(device.getVerificationUri())) {
      // A verification URI is required to complete device authorization.
      throw new OAuth2Exception(OAuth2Exception.Category.DEVICE_AUTHORIZATION_FAILED,
          "device authorization response missing verification_uri");
    }
    ApiLogger.info("device authorization started, userCode:{} expiresIn:{}s",
        device.getUserCode(), device.getExpiresIn());
    return device;
  }

  /**
   * Polls for a device token according to RFC 8628.
   *
   * <p>Device polling errors are handled separately from refresh errors so an invalid device
   * code does not remove an existing persisted token. The server-provided polling interval is
   * used when present; otherwise the RFC 8628 default applies.</p>
   */
  private OAuth2Token pollForDeviceToken(String tokenEndpoint,
      OAuth2DeviceAuthorization device, String usedClientId) {
    long intervalSeconds = device.getInterval() != null && device.getInterval() > 0
        ? device.getInterval() : DEFAULT_DEVICE_POLL_INTERVAL_SECONDS;
    // Use the server-provided lifetime, with a conservative fallback when omitted.
    long ttlSeconds = device.getExpiresIn() > 0 ? device.getExpiresIn() : 600;
    long deadline = System.currentTimeMillis() + ttlSeconds * 1000L;

    Map<String, String> form = new LinkedHashMap<>();
    form.put("grant_type", GRANT_TYPE_DEVICE_CODE);
    form.put("device_code", device.getDeviceCode());
    form.put("client_id", usedClientId);

    while (true) {
      // Poll only within the device code lifetime.
      long leftMillis = deadline - System.currentTimeMillis();
      if (leftMillis > 0) {
        sleepMillis(Math.min(intervalSeconds * 1000L, leftMillis));
      }

      if (System.currentTimeMillis() >= deadline) {
        throw new OAuth2Exception(OAuth2Exception.Category.DEVICE_AUTHORIZATION_FAILED,
            "user_code expired after " + ttlSeconds
                + "s without approval, start a new device authorization");
      }

      JSONObject json = OAuth2HttpUtils.postForm(tokenEndpoint, form);
      if (json.getString("access_token") != null) {
        return toToken(json, usedClientId);
      }

      String error = json.getString("error");
      if ("authorization_pending".equals(error)) {
        continue;
      }
      if ("slow_down".equals(error)) {
        // Apply the RFC 8628 incremental backoff for each slow_down response.
        intervalSeconds += DEVICE_SLOW_DOWN_INCREMENT_SECONDS;
        ApiLogger.info("device polling too fast, interval raised to {}s", intervalSeconds);
        continue;
      }
      // RFC 8628 terminal errors end this device attempt without modifying persisted tokens.
      String detail = error + (json.getString("error_description") == null ? ""
          : ": " + json.getString("error_description"));
      if ("access_denied".equals(error)) {
        throw new OAuth2Exception(OAuth2Exception.Category.DEVICE_AUTHORIZATION_FAILED,
            "user denied the authorization (" + detail + ")");
      }
      if ("expired_token".equals(error) || "invalid_grant".equals(error)) {
        throw new OAuth2Exception(OAuth2Exception.Category.DEVICE_AUTHORIZATION_FAILED,
            "device_code no longer valid (" + detail
                + "), start a new device authorization");
      }
      throw new OAuth2Exception(OAuth2Exception.Category.TOKEN_REQUEST_FAILED,
          "device token polling failed: " + detail);
    }
  }

  private static void sleepMillis(long millis) {
    try {
      TimeUnit.MILLISECONDS.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new OAuth2Exception(OAuth2Exception.Category.DEVICE_AUTHORIZATION_FAILED,
          "interrupted while waiting for device authorization", e);
    }
  }

  /**
   * Calls the token endpoint and converts the response into an {@link OAuth2Token}.
   *
   * <p>A rejected grant is reported, not acted on. Only the caller knows what the rejected
   * credential was and whether it is still the current one, and deleting local state here would
   * take that decision away -- see {@link #recoverOrGiveUp(OAuth2Token, RejectedGrant)}.</p>
   */
  private OAuth2Token exchange(String tokenEndpoint, Map<String, String> form,
      String usedClientId) {
    JSONObject json = OAuth2HttpUtils.postForm(tokenEndpoint, form);
    if (json.getString("access_token") == null) {
      String error = json.getString("error");
      if ("invalid_grant".equals(error) || "invalid_client".equals(error)) {
        throw new RejectedGrant(error, json.getString("error_description"));
      }
      throw new OAuth2Exception(OAuth2Exception.Category.TOKEN_REQUEST_FAILED,
          "token endpoint returned error: " + error
              + " " + json.getString("error_description"));
    }
    return toToken(json, usedClientId);
  }

  /**
   * A grant the authorization server refused outright.
   *
   * <p>Carries the raw error code so a caller can tell a dead credential from a dead client
   * registration. It is an {@link OAuth2Exception} with
   * {@link OAuth2Exception.Category#REAUTHORIZATION_REQUIRED}, so application code that catches
   * the public type and switches on the category is unaffected by its existence.</p>
   */
  private static final class RejectedGrant extends OAuth2Exception {

    private static final long serialVersionUID = 1L;

    private final String error;

    RejectedGrant(String error, String description) {
      super(Category.REAUTHORIZATION_REQUIRED, error + ": " + description);
      this.error = error;
    }

    boolean isClientRejected() {
      return "invalid_client".equals(error);
    }
  }

  /**
   * Discards the local authorization the server just refused, so later calls fail fast instead
   * of replaying a credential that is known to be dead.
   */
  private void forgetRejectedAuthorization(RejectedGrant rejected, String usedClientId) {
    if (usedClientId != null) {
      store.deleteToken(usedClientId);
    }
    this.token = null;
    forgetRejectedRegistration(rejected);
  }

  /**
   * Discards a dynamic client registration the server refused, so the next authorization
   * registers a new one. Explicit client IDs come from the application and are left alone.
   */
  private void forgetRejectedRegistration(RejectedGrant rejected) {
    if (rejected.isClientRejected() && explicitClientId == null) {
      store.deleteClientRegistration(issuer);
      this.registeredClientId = null;
    }
  }

  /** Token response to {@link OAuth2Token}. */
  private OAuth2Token toToken(JSONObject json, String usedClientId) {
    OAuth2Token result = new OAuth2Token();
    result.setIssuer(issuer);
    result.setClientId(usedClientId);
    result.setAccessToken(json.getString("access_token"));
    result.setRefreshToken(json.getString("refresh_token"));
    // Prefer the granted scope from the token response; use the requested scope when omitted.
    String granted = json.getString("scope");
    result.setScope(isBlank(granted) ? scopes() : granted);
    String type = json.getString("token_type");
    if (type != null) {
      result.setTokenType(type);
    }
    Integer expiresIn = json.getInteger("expires_in");
    long ttl = expiresIn == null ? 3600L : expiresIn.longValue();
    result.setExpiresAt(System.currentTimeMillis() + ttl * 1000L);
    return result;
  }

  /** Returns whether the registered scope contains every requested scope. */
  private static boolean scopesCover(String registered, String requested) {
    if (registered == null) {
      return false;
    }
    Set<String> have = new HashSet<>(Arrays.asList(registered.split(" ")));
    for (String want : requested.split(" ")) {
      if (!want.isEmpty() && !have.contains(want)) {
        return false;
      }
    }
    return true;
  }

  /** Returns a sorted, deduplicated, space-delimited scope string. */
  private static String normalizeScopes(String raw) {
    if (raw == null || raw.trim().isEmpty()) {
      throw new IllegalArgumentException("scopes is required");
    }
    Set<String> set = new HashSet<>();
    for (String s : raw.trim().split("\\s+")) {
      if (!s.isEmpty()) {
        set.add(s);
      }
    }
    List<String> list = new ArrayList<>(set);
    Collections.sort(list);
    StringBuilder sb = new StringBuilder();
    for (String s : list) {
      if (sb.length() > 0) {
        sb.append(' ');
      }
      sb.append(s);
    }
    return sb.toString();
  }

  private static boolean isBlank(String s) {
    return s == null || s.trim().isEmpty();
  }

  private static boolean equalsToken(String a, String b) {
    return a == null ? b == null : a.equals(b);
  }

  private static String trimTrailingSlash(String s) {
    String r = s.trim();
    while (r.endsWith("/")) {
      r = r.substring(0, r.length() - 1);
    }
    return r;
  }

  private static String query(String key, String value) {
    try {
      return URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException("UTF-8 not supported", e);
    }
  }

  private static String randomUrlSafe(int bytes) {
    byte[] buf = new byte[bytes];
    RANDOM.nextBytes(buf);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
  }

  private static String s256(String verifier) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] digest = md.digest(verifier.getBytes("UTF-8"));
      return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    } catch (Exception e) {
      throw new IllegalStateException("compute S256 challenge fail", e);
    }
  }

  public static class Builder {
    private String issuer = defaultIssuer();
    private String clientId;
    private String home;
    private OAuth2TokenStore store;
    private String surface = OAuth2TokenStore.DEFAULT_SURFACE;
    private int callbackPort;
    private long authorizeTimeoutMinutes = DEFAULT_AUTHORIZE_TIMEOUT_MINUTES;

    public Builder issuer(String issuer) {
      this.issuer = issuer;
      return this;
    }

    /**
     * Sets an explicitly registered client ID.
     *
     * <p>The value is not persisted and must be supplied for each session. Its registered scopes
     * must cover all scopes advertised by the authorization server. The default redirect URI is
     * {@code http://127.0.0.1:18888/callback} unless {@link #callbackPort(int)} is set.</p>
     */
    public Builder clientId(String clientId) {
      this.clientId = clientId;
      return this;
    }

    /**
     * Sets the loopback callback port.
     *
     * <p>The port must match the redirect URI of an explicitly registered client. Dynamic
     * registration uses a system-assigned port when this value is unset.</p>
     *
     * @param port port in the range 1024-65535, or {@code 0} when unspecified
     */
    public Builder callbackPort(int port) {
      if (port != 0 && (port < 1024 || port > 65535)) {
        throw new IllegalArgumentException(
            "callbackPort must be 0 or within 1024-65535, got " + port);
      }
      this.callbackPort = port;
      return this;
    }

    /** Overrides the storage directory; defaults to {@code ~/.tiger/openapi/}. */
    public Builder home(String home) {
      this.home = home;
      return this;
    }

    public Builder store(OAuth2TokenStore store) {
      this.store = store;
      return this;
    }

    /**
     * Entry point owning the dynamic registration, e.g. {@code sdk-java}, {@code cli}.
     *
     * <p>Each surface registers its own client, so it appears separately on the user's
     * authorization page and can be revoked on its own. Tools built on this SDK must pass
     * their own value. Ignored when an explicit {@link #store} is supplied -- that store
     * carries its own surface.</p>
     */
    public Builder surface(String surface) {
      this.surface = surface;
      return this;
    }

    public Builder authorizeTimeoutMinutes(long minutes) {
      this.authorizeTimeoutMinutes = minutes;
      return this;
    }

    public OAuth2SessionManager build() {
      return new OAuth2SessionManager(this);
    }
  }
}
