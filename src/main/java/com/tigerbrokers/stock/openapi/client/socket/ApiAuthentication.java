package com.tigerbrokers.stock.openapi.client.socket;

import com.tigerbrokers.stock.openapi.client.auth.AuthenticationType;
import com.tigerbrokers.stock.openapi.client.auth.oauth2.OAuth2Authentication;
import com.tigerbrokers.stock.openapi.client.auth.oauth2.OAuth2SessionManager;
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import com.tigerbrokers.stock.openapi.client.util.TigerSignature;
import com.tigerbrokers.stock.openapi.client.util.builder.HeaderBuilder;

/**
 * Description:
 * Created by lijiawen on 2018/06/06.
 *
 * <p>Carries the credential the socket connect message needs. Two mutually exclusive shapes:
 * <b>tigerId + sign</b> (the legacy way) or <b>access_token</b> (OAuth2).
 *
 * <p>The two are never sent together. The server decides which branch to take by asking
 * "is {@code sign} blank" -- send both and it takes the signature branch, so the token is
 * ignored. Keeping them exclusive here means the decision is made in one place.
 *
 * <p>Signature mode behaves exactly as before: {@link #getSign()} returns the RSA signature
 * computed at build time and {@link #getAccessToken()} returns null, so the connect message
 * is byte-for-byte what it always was.
 */
public class ApiAuthentication {

  private ClientConfig clientConfig;
  private String tigerId;
  private String sign;
  private String version = HeaderBuilder.DEFAULT_VERSION;

  /**
   * The OAuth2 session, non-null only in OAuth2 mode.
   *
   * <p>The session rather than a token string: a socket connection can outlive a token, and
   * every reconnect has to ask for the token that is valid <b>now</b>. Caching the string
   * here is what would make a reconnect present an expired credential.
   */
  private OAuth2SessionManager sessions;

  public ApiAuthentication(ClientConfig clientConfig) {
    this.clientConfig = clientConfig;
    this.tigerId = clientConfig.tigerId;
  }

  public ClientConfig getClientConfig() {
    return clientConfig;
  }

  public String getTigerId() {
    return this.tigerId;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  /** Whether this connection authenticates with an OAuth2 access token. */
  public boolean isOauth2() {
    return sessions != null;
  }

  public OAuth2SessionManager getSessionManager() {
    return sessions;
  }

  /**
   * A currently valid access token, or null in signature mode.
   *
   * <p>Fetched on every call rather than cached, so a reconnect always presents a fresh
   * credential. Refreshes synchronously when the token is close to expiry (single-flight).
   *
   * <p>Returns null instead of throwing when no authorization exists yet: this sits on the
   * netty connect path, where an exception would surface as a bare channel failure. Null
   * lets the caller log the real reason.
   */
  public String getAccessToken() {
    if (sessions == null) {
      return null;
    }
    try {
      return sessions.getAccessToken();
    } catch (Exception e) {
      ApiLogger.error("socket get access token fail:{}", e.getMessage(), e);
      return null;
    }
  }

  /**
   * Builds the credential from the config, picking the mode from
   * {@link ClientConfig#authentication}.
   *
   * <p>OAuth2 when that field holds an {@link OAuth2Authentication}, signature otherwise --
   * including when it is null, which is every existing caller.
   */
  public static ApiAuthentication build(ClientConfig clientConfig) {
    return build(clientConfig, clientConfig.privateKey, HeaderBuilder.DEFAULT_VERSION);
  }

  public static ApiAuthentication build(ClientConfig clientConfig, String privateKey) {
    return build(clientConfig, privateKey, HeaderBuilder.DEFAULT_VERSION);
  }

  public static ApiAuthentication build(ClientConfig clientConfig, String privateKey, String version) {
    OAuth2SessionManager sessions = oauth2SessionOf(clientConfig);
    if (sessions != null) {
      return buildWithOAuth2(clientConfig, sessions, version);
    }
    ApiAuthentication authentication = new ApiAuthentication(clientConfig);
    try {
      String sign = TigerSignature.rsaSign(clientConfig.tigerId, privateKey, TigerApiConstants.UTF_8);
      authentication.setSign(sign);
      authentication.setVersion(version);
    } catch (Exception e) {
      ApiLogger.error("authentication build exception:{}", e.getMessage(), e);
      return null;
    }
    return authentication;
  }

  /**
   * Builds an OAuth2 credential. No private key, no signing, and {@code sign} stays null.
   *
   * <p>Sends no request: whether authorization has happened is not decided here. Building
   * a client must not trigger a browser or a token request.
   */
  public static ApiAuthentication buildWithOAuth2(ClientConfig clientConfig,
      OAuth2SessionManager sessions, String version) {
    if (sessions == null) {
      throw new IllegalArgumentException("OAuth2SessionManager is required");
    }
    ApiAuthentication authentication = new ApiAuthentication(clientConfig);
    authentication.sessions = sessions;
    authentication.setVersion(version);
    // tigerId comes from the token's sub claim on the server side, so it is not sent at all
    authentication.tigerId = null;
    return authentication;
  }

  /**
   * Extracts the session manager when the config asks for OAuth2.
   *
   * <p>Recognizes {@link OAuth2Authentication} specifically rather than just the
   * {@code OAUTH2} type: the session manager is what the socket path needs, and only that
   * class exposes one. Another OAUTH2 implementation would have nothing to hand over.
   */
  private static OAuth2SessionManager oauth2SessionOf(ClientConfig clientConfig) {
    if (clientConfig == null || clientConfig.authentication == null) {
      return null;
    }
    if (AuthenticationType.OAUTH2 != clientConfig.authentication.type()) {
      return null;
    }
    if (clientConfig.authentication instanceof OAuth2Authentication) {
      return ((OAuth2Authentication) clientConfig.authentication).getSessionManager();
    }
    ApiLogger.warn("socket oauth2 requires OAuth2Authentication, got:{}, falling back to signature",
        clientConfig.authentication.getClass().getName());
    return null;
  }

  /**
   * Prints no credential value: neither the signature nor the token.
   *
   * <p>Both are usable credentials, and this object shows up in logs.
   */
  @Override
  public String toString() {
    return "ApiAuthentication{tigerId=" + tigerId
        + ", version=" + version
        + ", oauth2=" + isOauth2()
        + ", hasSign=" + !StringUtils.isEmpty(sign) + "}";
  }
}
