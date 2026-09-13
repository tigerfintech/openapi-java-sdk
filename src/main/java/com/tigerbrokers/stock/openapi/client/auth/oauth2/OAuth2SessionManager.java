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
import java.util.function.Consumer;

/**
 * OAuth2 session management: authorization, persistence, refresh.
 *
 * <p>Transport-agnostic -- it only handles "how to obtain and keep a valid access token" and
 * knows nothing about business requests. Applying the token to a request is the next layer's
 * job.</p>
 *
 * <p>Simplest usage (dynamic registration, requesting every scope the AS supports):</p>
 * <pre>
 * OAuth2SessionManager sessions = OAuth2SessionManager.loginWithDefaults(
 *     url -&gt; System.out.println("open: " + url));
 *
 * String token = sessions.getAccessToken();   // refreshes automatically when needed
 * </pre>
 *
 * <p>Using a clientId obtained by registering by hand (curl POST /oauth2/register). The
 * callback port defaults to 18888 and must match the redirect_uri of that registration:</p>
 * <pre>
 * OAuth2SessionManager sessions = OAuth2SessionManager.builder()
 *     .clientId("your-client-id")
 *     .callbackPort(18888)          // optional; this is the manual-mode default
 *     .build();
 * sessions.loginIfNeeded(callback);
 * </pre>
 *
 * <p>With no local browser (a server, a container, a machine reached over SSH), use the device
 * flow -- loopback cannot receive the callback in those environments:</p>
 * <pre>
 * OAuth2SessionManager sessions = OAuth2SessionManager.builder().build();
 * sessions.loginWithDeviceCodeIfNeeded(d -&gt; {
 *   System.out.println("open " + d.getVerificationUri());
 *   System.out.println("enter " + d.getUserCode());
 * });
 * </pre>
 *
 * <p>The scope is not chosen by the application: authorization always requests every
 * {@code scopes_supported} entry from the AS metadata, in both modes. Dynamic registration
 * itself declares no scope (the AS decides the range). See {@code docs/oauth2.md}.</p>
 *
 * <p>Thread safety: {@link #getAccessToken()} may be called concurrently, and refresh is
 * single-flight (only one thread actually hits the token endpoint at a time).</p>
 */
public class OAuth2SessionManager {

  /**
   * Environment-variable override for the default issuer.
   *
   * <p>The server's production issuer is itself injected as {@code ${OAUTH2_ISSUER}}, so we
   * leave the same hook here to avoid a code change per environment.</p>
   */
  public static final String ENV_ISSUER = "TIGEROPEN_OAUTH2_ISSUER";

  /**
   * Production authorization server. Override with the {@link #ENV_ISSUER} environment
   * variable, or by passing an issuer explicitly to the builder, to point at another
   * environment.
   */
  private static final String BUILTIN_ISSUER = "https://openapi-oauth2.tigerfintech.com";

  /** Refresh early once inside this window before expiry. */
  private static final long DEFAULT_REFRESH_AHEAD_MILLIS = 300_000L;

  /** Overall timeout for the user completing authorization in the browser. */
  private static final long DEFAULT_AUTHORIZE_TIMEOUT_MINUTES = 5;

  /**
   * Device-flow polling interval in seconds. The default mandated by RFC 8628 §3.5.
   *
   * <p>Tiger's AS <b>does not return {@code interval}</b> in the device authorization response
   * (verified in testing), so this value is used essentially always. If the AS starts returning
   * one, that takes precedence.</p>
   */
  public static final long DEFAULT_DEVICE_POLL_INTERVAL_SECONDS = 5;

  /**
   * How many seconds to add to the interval on {@code slow_down} (RFC 8628 §3.5).
   *
   * <p>The spec says "increase" without giving a number; 5 seconds is its own example and the
   * common practice. It accumulates rather than applying once -- repeated slow_down means we
   * are still going too fast.</p>
   */
  private static final long DEVICE_SLOW_DOWN_INCREMENT_SECONDS = 5;

  /**
   * The agreed callback port for manual-clientId mode.
   *
   * <p>Registering by hand means writing a concrete redirect_uri into the registration
   * request, so the port has to be known at that moment and this mode cannot use a random
   * one -- there must be a fixed value both sides know. The SDK only receives a client_id
   * back and never learns what redirect_uri was registered, hence the agreed default,
   * corresponding to {@code http://127.0.0.1:18888/callback}.</p>
   *
   * <p>18888 was chosen over the likes of 8080 or 3000 to stay clear of the default Linux
   * ephemeral port range (from 32768) so it cannot collide with a system-assigned port, and
   * clear of common development ports so it does not fight the user's own services.</p>
   *
   * <p>Dynamic registration is unaffected and still uses a system-assigned random port -- there
   * the redirect_uri is sent by the SDK itself without a port, so there is nothing to agree on
   * in advance.</p>
   */
  public static final int DEFAULT_MANUAL_CALLBACK_PORT = 18888;

  private static final String GRANT_TYPE_DEVICE_CODE =
      "urn:ietf:params:oauth:grant-type:device_code";

  private static final SecureRandom RANDOM = new SecureRandom();

  private final String issuer;

  /**
   * The application name declared at dynamic registration, shown on the user's authorization
   * management page.
   *
   * <p>Deliberately not configurable. One issuer stores exactly one registration record and the
   * first registrant wins, so a caller-supplied name would only take effect on a machine that has
   * never registered -- producing a value that differs between users for no reason. Keeping it
   * fixed also makes the name a reliable indicator of which SDK created the record.</p>
   */
  private static final String CLIENT_NAME = "Tiger Java SDK";

  /**
   * The loopback port the application explicitly asked for; 0 means unspecified.
   *
   * <p>When unspecified: dynamic registration uses a system-assigned port, manual clientId uses
   * {@link #DEFAULT_MANUAL_CALLBACK_PORT}. See {@link #effectiveCallbackPort()}.</p>
   */
  private final int callbackPort;
  private final OAuth2TokenStore store;
  private final long authorizeTimeoutMinutes;

  /**
   * The clientId the application specified explicitly; null means dynamic registration.
   *
   * <p>Final -- its origin must not be overwritten by a later assignment. It used to share one
   * mutable field with dynamic registration, which lost track at runtime of "who supplied this
   * clientId".</p>
   */
  private final String explicitClientId;

  /** The clientId from dynamic registration or read back from {@code clients/}. Always null in explicit mode. */
  private volatile String registeredClientId;

  private volatile OAuth2Metadata metadata;
  private volatile OAuth2Token token;

  /** The full scope parsed from the metadata, cached after the first parse. */
  private volatile String resolvedScopes;

  private final Object refreshLock = new Object();

  private OAuth2SessionManager(Builder builder) {
    if (builder.issuer == null || builder.issuer.isEmpty()) {
      throw new IllegalArgumentException("issuer is required");
    }
    this.issuer = trimTrailingSlash(builder.issuer);
    // The scope is always the full set, but "which set" has to be asked of the AS. The
    // constructor sends no request, otherwise builder().build() would fail offline
    this.callbackPort = builder.callbackPort;
    this.explicitClientId = isBlank(builder.clientId) ? null : builder.clientId.trim();
    this.store = builder.store != null ? builder.store : new OAuth2TokenStore(builder.home);
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
   * One-line authorization: default issuer, default storage, dynamic registration, and a
   * browser only when needed.
   *
   * <p>If authorization already happened it reuses the local token (refreshing it when close to
   * expiry), and only calls {@code onAuthorizationUrl} when the user genuinely has to consent.
   * So a second run usually pops up nothing.</p>
   *
   * <p><b>It does not open a browser by itself</b> -- the URL goes to the callback and whether
   * to open it is the application's decision. Otherwise an unattended script would hang on an
   * authorization page nobody is looking at.</p>
   *
   * <p>To use a clientId you registered by hand, or a fixed callback port, use
   * {@link #builder()}.</p>
   *
   * @param onAuthorizationUrl callback that receives the URL when user authorization is needed
   */
  public static OAuth2SessionManager loginWithDefaults(
      Consumer<String> onAuthorizationUrl) {
    OAuth2SessionManager sessions = builder().build();
    sessions.loginIfNeeded(onAuthorizationUrl);
    return sessions;
  }

  /**
   * One-line device-flow authorization: default issuer, default storage, dynamic registration.
   *
   * <p>For environments with no local browser. If authorization already happened it reuses the
   * local token without bothering the user. See
   * {@link #loginWithDeviceCodeIfNeeded(Consumer)} for the trade-offs.</p>
   *
   * @param onDeviceAuthorization callback that receives the user_code and verification page address
   */
  public static OAuth2SessionManager loginWithDeviceCodeDefaults(
      Consumer<OAuth2DeviceAuthorization> onDeviceAuthorization) {
    OAuth2SessionManager sessions = builder().build();
    sessions.loginWithDeviceCodeIfNeeded(onDeviceAuthorization);
    return sessions;
  }

  // ------------------------------------------------------------ public API

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
    // 64 and 24 bytes, matching the Python SDK and the cross-SDK spec. RFC 7636 allows a
    // verifier of 43-128 characters; 64 bytes base64url-encodes to 86, comfortably inside.
    String verifier = randomUrlSafe(64);
    String challenge = s256(verifier);
    String state = randomUrlSafe(24);

    // The listener has to be up before the authorization URL is handed out
    try (LoopbackReceiver receiver = new LoopbackReceiver(effectiveCallbackPort())) {
      String redirectUri = receiver.getRedirectUri();
      String effectiveClientId = resolveClientId(meta);

      // Always send the scope, and always the full set: an authorization-code request has to
      // state the range it wants, and that holds in both modes (where the clientId came from
      // makes no difference here)
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

      OAuth2Token fresh = exchange(meta.getTokenEndpoint(), form, effectiveClientId);
      persist(fresh);
      return fresh;
    }
  }

  /**
   * Uses the existing local authorization if it is usable, otherwise runs a device-flow
   * authorization (RFC 8628).
   *
   * <p>For when the machine running the SDK has no browser -- a server, a container, a remote
   * machine reached over SSH. The loopback route does not work in those environments (not
   * "worse experience", it simply cannot receive the callback): the user opens the authorization
   * link on their own laptop, the browser redirects to <b>the laptop's</b> 127.0.0.1, and the
   * SDK is listening on the server where nothing is listening.</p>
   *
   * <p>The device flow needs no redirect_uri, no local listener and no port negotiation. The
   * user opens the verification page on <b>any</b> device with a browser and enters the
   * user_code.</p>
   *
   * <p>Blocks until the user approves, denies, or the user_code expires (the {@code expires_in}
   * from the AS, measured at 600 seconds).</p>
   *
   * <p><b>Less secure than loopback + PKCE</b>: the device flow has no redirect binding and no
   * proof that the initiator and the redeemer are the same party, so it can be phished -- an
   * attacker starts their own device authorization and tricks the user into entering that code,
   * so what the user approves is the attacker's session. Prefer
   * {@link #loginIfNeeded(Consumer)} when a local browser exists; this method is for
   * environments that genuinely have none.</p>
   *
   * @param onDeviceAuthorization callback receiving the user_code and verification page address;
   *     the application is responsible for displaying them to the user
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

    // Hand out the code first so the user can start, and only then begin polling
    onDeviceAuthorization.accept(device);

    OAuth2Token fresh = pollForDeviceToken(meta.getTokenEndpoint(), device, effectiveClientId);
    persist(fresh);
    return fresh;
  }

  /**
   * Returns a currently usable access token, refreshing it when close to expiry.
   *
   * <p>Throws {@link OAuth2Exception.Category#REAUTHORIZATION_REQUIRED} when authorization has
   * never happened -- it <b>never opens a browser implicitly</b>. Authorization is a user
   * interaction and must be initiated explicitly by the application.</p>
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
    return refresh(current).getAccessToken();
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
    synchronized (refreshLock) {
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

      Map<String, String> form = new LinkedHashMap<>();
      form.put("grant_type", "refresh_token");
      form.put("refresh_token", current.getRefreshToken());
      form.put("client_id", current.getClientId());

      OAuth2Token refreshed =
          exchange(metadata().getTokenEndpoint(), form, current.getClientId());

      // We are a public client, so the server's reuseRefreshTokens(!isPublic) is false -- the
      // refresh_token rotates on every refresh and a normal response always carries the new one.
      // This covers the case where the response omits it: carrying the old one forward is safer
      // than dropping it, since dropping it leaves only re-authorization.
      if (!refreshed.hasRefreshToken()) {
        refreshed.setRefreshToken(current.getRefreshToken());
      }
      persist(refreshed);
      return refreshed;
    }
  }

  /**
   * Recovery after a 401: refresh using <b>the access token value that failed</b>.
   *
   * <p>The difference from {@link #refresh} is the parameter -- this only needs "the token
   * string we just used", not an {@link OAuth2Token} object. That string is exactly what the
   * request side recorded.
   *
   * <p>This is what prevents a 401 storm: the moment token A expires there may be 20 in-flight
   * requests all using A, and 20 401s come back one after another. Comparing "I used A, current
   * is already B" returns B directly, so only the first one actually triggers a refresh.
   *
   * @param failedAccessToken the access token value actually used for the request
   * @return a usable token, possibly one another thread just refreshed
   * @throws OAuth2Exception when refresh fails; category {@code REAUTHORIZATION_REQUIRED} means
   *     only re-authorization will help
   */
  public OAuth2Token refreshAfterUnauthorized(String failedAccessToken) {
    synchronized (refreshLock) {
      OAuth2Token current = currentToken();
      // Already replaced by another thread -- do not refresh, just use the new one
      if (current != null && failedAccessToken != null
          && !equalsToken(current.getAccessToken(), failedAccessToken)) {
        return current;
      }
      // Reaching here means current IS the one that failed (or there is none at all). The
      // same-looking check inside refresh() can never fire on this path (it would compare
      // current against itself); the real check is the one above -- and since we already hold
      // refreshLock, the current we read cannot change under us.
      return refresh(current);
    }
  }

  /**
   * Clears the local token. The client_id is kept, so the next authorization needs no
   * re-registration.
   */
  /**
   * Ends the authorization: revokes it on the server (RFC 7009), then clears the local token.
   *
   * <p>The registered client_id is <b>kept</b> -- that is this machine's application identity,
   * not an authorization. Deleting it would register a brand new client on the AS every time
   * the user logs back in.</p>
   *
   * <p><b>The local token is always cleared, even when revocation fails.</b> The user asked to
   * log out; refusing to do so locally because the network is down would be the wrong answer.
   * The return value says whether the server side actually happened.</p>
   *
   * @return {@code true} if the server confirmed the revocation; {@code false} if only the
   *     local token was cleared, meaning <b>the credential is still valid on the server</b>
   *     (up to the refresh_token TTL) and the caller may want to tell the user or retry
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
   * Best-effort server-side revocation. <b>Never throws</b> -- logout must not fail because of it.
   *
   * <p>Revokes the <b>refresh_token</b> when there is one: RFC 7009 §2.1 says the AS SHOULD also
   * invalidate the access tokens issued from it, so one call covers both. Falling back to the
   * access_token alone would leave the refresh_token alive, which is the more dangerous half --
   * it is the one that lives for 30 days.</p>
   *
   * <p>RFC 7009 §2.2 makes the endpoint answer {@code 200} for an unknown or already-revoked
   * token too, so a success here does not prove the token existed -- only that the server has
   * no objection. That is exactly what we want to know.</p>
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
        // Nothing to call. Not an error -- revocation is optional in RFC 8414.
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
   * The client_id currently in use, or null when it has not been settled yet.
   *
   * <p>Sends no request and registers nothing. Use it when diagnosing "which client is actually
   * being used".</p>
   */
  public String getClientId() {
    return knownClientId();
  }

  /** Whether the client_id was passed explicitly by the application (true) or dynamically registered (false). */
  public boolean hasExplicitClientId() {
    return explicitClientId != null;
  }

  // ------------------------------------------------------------- internal

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
   * Which clientId to use right now, with no side effects: no registration, no requests.
   *
   * <p>In explicit mode this is whatever the application supplied and <b>{@code clients/} is not
   * consulted</b> -- the application declared its own identity, so the SDK neither memorizes it
   * on its behalf nor substitutes someone else's registration.</p>
   *
   * @return null when it cannot be settled yet (unspecified and never registered on this machine)
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
    // Explicit mode leaves registeredClientId alone: it records "what was dynamically
    // registered", and overwriting it with an explicit clientId would mean a later run that
    // passes no clientId reads someone else's authorization
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
   * Which loopback port this authorization uses.
   *
   * <p>An explicit value is used as-is. When unspecified there are two cases: dynamic
   * registration uses 0 (system-assigned, and per RFC 8252 §7.3 the AS ignores the port), while
   * a manual clientId uses {@link #DEFAULT_MANUAL_CALLBACK_PORT} -- filling in the redirect_uri
   * by hand requires knowing the port, and a random one cannot be written down in
   * advance.</p>
   */
  private int effectiveCallbackPort() {
    if (callbackPort > 0) {
      return callbackPort;
    }
    return explicitClientId != null ? DEFAULT_MANUAL_CALLBACK_PORT : 0;
  }

  /**
   * The scope requested at authorization: every {@code scopes_supported} entry from the AS metadata.
   *
   * <p>The list is not hardcoded -- when the server adds a new scope the SDK keeps up without a
   * code change. The application cannot specify it either: the SDK serves the "hold a token and
   * call whatever endpoint" case, does not know in advance which will be used, and one missing
   * scope means a 403 at runtime.</p>
   *
   * <p>{@code scopes_supported} is RECOMMENDED rather than REQUIRED in RFC 8414. When the AS
   * omits it we must not substitute a guessed hardcoded list (guess wrong and you either lack
   * permission or request permissions the user never intended to give) -- the only option is to
   * fail loudly.</p>
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
   * Settles the client_id before authorization: explicitly provided &gt; registered on this
   * machine &gt; register a new one.
   *
   * <p>An explicitly provided one is returned immediately and <b>{@code clients/} is not touched
   * at all</b>: not read (no substituting a dynamically registered one) and not written (the
   * application config already has it, so the SDK keeps no second copy). As a result
   * {@code clients/} only ever holds dynamic registrations.</p>
   *
   * <p>Dynamic registration happens once and is then persisted. Registering a new one on every
   * startup is not acceptable -- junk client_ids would pile up on the AS and the user would see
   * dozens of identically named applications on the authorization management page.</p>
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
      // What is stored is the range the AS originally granted this client. We land here once the
      // server has added a new scope -- authorizing with the old client would just be rejected
      // with invalid_scope, so a fresh registration is needed.
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
    // By default no port is written: a random port cannot be registered in advance, so we rely
    // on the AS ignoring the port per RFC 8252 §7.3. When the application fixed a port
    // explicitly we register it verbatim -- that is what an AS doing strict exact matching needs.
    String registeredRedirect = callbackPort > 0
        ? "http://127.0.0.1:" + callbackPort + "/callback"
        : "http://127.0.0.1/callback";
    payload.put("redirect_uris", Collections.singletonList(registeredRedirect));
    // Declare both grants so one client covers loopback and the device flow. clients/ keeps a
    // single record per issuer, so registering them separately would mean that using loopback
    // first and the device flow later sends a client without device_code support and gets
    // rejected by the AS, with no visible reason for the user.
    payload.put("grant_types", Arrays.asList(
        "authorization_code", "refresh_token", GRANT_TYPE_DEVICE_CODE));
    payload.put("response_types", Collections.singletonList("code"));
    payload.put("token_endpoint_auth_method", "none");
    // Declare no scope: registration only records "who this client is", and the range is the
    // AS's own policy (in testing, a client registered without a scope receives all of
    // scopes_supported). Declaring one would instead pin the client to whatever list the SDK saw
    // at the time -- once the server adds a new scope that client could never request it, and
    // since the registration is persisted and reused, one registration lasts a long time.

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
    // Record the range the AS actually granted, not the one we wanted -- the registration
    // request declared no scope, so what we get is the AS's call. Recording it wrong would skew
    // the coverage check below: we would think it suffices and only get rejected at
    // authorization. If the response omits the scope, fall back to the full set, otherwise every
    // startup would decide it is insufficient and register again.
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
      // With no verification page address the user has nowhere to go -- fail early rather than
      // leave them staring at a lone code
      throw new OAuth2Exception(OAuth2Exception.Category.DEVICE_AUTHORIZATION_FAILED,
          "device authorization response missing verification_uri");
    }
    ApiLogger.info("device authorization started, userCode:{} expiresIn:{}s",
        device.getUserCode(), device.getExpiresIn());
    return device;
  }

  /**
   * Polls the token endpoint waiting for the user's approval.
   *
   * <p><b>Deliberately does not reuse {@link #exchange}</b>: the same {@code error} means
   * different things in the two places. During a token exchange or refresh,
   * {@code invalid_grant} means "authorization is gone, delete the local token", whereas during
   * polling it only means "this device_code expired or was denied" -- the local token has nothing
   * to do with this attempt, so deleting it would be collateral damage (the user may still have
   * a perfectly good authorization on disk). Cramming both into one method behind a flag means
   * somebody eventually breaks one side.</p>
   *
   * <p>The interval comes from the AS's {@code interval}, falling back to RFC 8628's 5-second
   * default -- Tiger's AS does not return that field in practice.</p>
   */
  private OAuth2Token pollForDeviceToken(String tokenEndpoint,
      OAuth2DeviceAuthorization device, String usedClientId) {
    long intervalSeconds = device.getInterval() != null && device.getInterval() > 0
        ? device.getInterval() : DEFAULT_DEVICE_POLL_INTERVAL_SECONDS;
    // Trust the lifetime the AS gave us; fall back to a conservative value only if it gave none
    long ttlSeconds = device.getExpiresIn() > 0 ? device.getExpiresIn() : 600;
    long deadline = System.currentTimeMillis() + ttlSeconds * 1000L;

    Map<String, String> form = new LinkedHashMap<>();
    form.put("grant_type", GRANT_TYPE_DEVICE_CODE);
    form.put("device_code", device.getDeviceCode());
    form.put("client_id", usedClientId);

    while (true) {
      // Wait before polling: the user cannot possibly have approved in the instant after device
      // authorization was issued. Never sleep past the deadline -- otherwise we would sit through
      // a full interval after the user_code has already expired, wasting the user's time and
      // possibly making one more doomed poll.
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
        // Accumulate rather than adding once: still getting slow_down means still too fast
        intervalSeconds += DEVICE_SLOW_DOWN_INCREMENT_SECONDS;
        ApiLogger.info("device polling too fast, interval raised to {}s", intervalSeconds);
        continue;
      }
      // Everything else is terminal. expired_token and access_denied are defined by RFC 8628;
      // invalid_grant here also means the device_code is dead (an invalid device_code returns
      // exactly this in testing), which is not the same as the identically named error during a
      // refresh, so the local token is left alone.
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

  /** Calls the token endpoint and converts the response into an {@link OAuth2Token}. */
  private OAuth2Token exchange(String tokenEndpoint, Map<String, String> form,
      String usedClientId) {
    JSONObject json = OAuth2HttpUtils.postForm(tokenEndpoint, form);
    if (json.getString("access_token") == null) {
      String error = json.getString("error");
      // Both of these are terminal: retrying is pointless and only re-authorization helps.
      //   invalid_grant  -- the refresh_token expired, or the user revoked authorization
      //   invalid_client -- the client no longer exists on the AS (cleaned up, or wrong environment)
      // Without clearing the local token, the next startup would pick it up and refresh again,
      // failing for nothing every time.
      if ("invalid_grant".equals(error) || "invalid_client".equals(error)) {
        store.deleteToken(usedClientId);
        this.token = null;
        // The AS no longer recognizes this client. A dynamically registered one must have its
        // record cleared, otherwise the next authorization sends it again, gets rejected again,
        // and the user has no way to recover. But an explicitly passed one must not touch
        // clients/ -- that holds a different thing entirely (dynamic registrations), so deleting
        // it would punish an innocent party; a misconfigured application is the application's own
        // to fix.
        if ("invalid_client".equals(error) && explicitClientId == null) {
          store.deleteClientRegistration(issuer);
          this.registeredClientId = null;
        }
        throw new OAuth2Exception(OAuth2Exception.Category.REAUTHORIZATION_REQUIRED,
            error + ": " + json.getString("error_description"));
      }
      throw new OAuth2Exception(OAuth2Exception.Category.TOKEN_REQUEST_FAILED,
          "token endpoint returned error: " + error
              + " " + json.getString("error_description"));
    }
    return toToken(json, usedClientId);
  }

  /** Token response to {@link OAuth2Token}. */
  private OAuth2Token toToken(JSONObject json, String usedClientId) {
    OAuth2Token result = new OAuth2Token();
    result.setIssuer(issuer);
    result.setClientId(usedClientId);
    result.setAccessToken(json.getString("access_token"));
    result.setRefreshToken(json.getString("refresh_token"));
    // The AS may grant only part of it (the user ticks boxes on the consent page, or the
    // client's registered range is narrower), so the response wins; fall back to what we requested
    // only when it gives none
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

  /** Whether the scope declared at registration covers what we need this time. */
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

  /**
   * Normalizes a scope: sorted and deduplicated.
   *
   * <p>Otherwise {@code "b a"} and {@code "a b"} would count as two different sets, making every
   * startup decide that re-registration is needed.</p>
   */
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

  // -------------------------------------------------------------- builder

  public static class Builder {
    private String issuer = defaultIssuer();
    private String clientId;
    private String home;
    private OAuth2TokenStore store;
    private int callbackPort;
    private long authorizeTimeoutMinutes = DEFAULT_AUTHORIZE_TIMEOUT_MINUTES;

    public Builder issuer(String issuer) {
      this.issuer = issuer;
      return this;
    }

    /**
     * Sets the client_id explicitly (obtained beforehand by calling POST /oauth2/register
     * yourself, see docs/sdk-integration.md §5.1). Leave it
     * unset for dynamic registration.
     *
     * <p>An explicitly passed clientId is <b>not persisted</b> -- it already lives in the
     * application config and the SDK keeps no second copy. So a later construction that omits it
     * cannot find the matching authorization; it has to be passed every time.</p>
     *
     * <p>In this mode the callback port defaults to {@link #DEFAULT_MANUAL_CALLBACK_PORT}, so the
     * registration's redirect_uri must be {@code http://127.0.0.1:18888/callback}. Use
     * {@link #callbackPort(int)} for a different port.</p>
     *
     * <p>The scope requested at authorization is still everything the AS supports, so this
     * client's registered scope range has to cover the full set -- a narrower one
     * gets the authorization rejected with {@code invalid_scope}.</p>
     */
    public Builder clientId(String clientId) {
      this.clientId = clientId;
      return this;
    }

    /**
     * Fixes the loopback callback port; it must match the redirect_uri of your manual registration.
     *
     * <p>When unset: dynamic registration uses a system-assigned port, a manual clientId uses
     * {@link #DEFAULT_MANUAL_CALLBACK_PORT}。</p>
     *
     * @param port 1024-65535; 0 means unspecified
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

    public Builder authorizeTimeoutMinutes(long minutes) {
      this.authorizeTimeoutMinutes = minutes;
      return this;
    }

    public OAuth2SessionManager build() {
      return new OAuth2SessionManager(this);
    }
  }
}
