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
 * Authentication credentials for a socket connection.
 *
 * <p>Signature credentials and OAuth2 access tokens are mutually exclusive.</p>
 */
public class ApiAuthentication {

  private ClientConfig clientConfig;
  private String tigerId;
  private String sign;
  private String version = HeaderBuilder.DEFAULT_VERSION;

  /** OAuth2 session used to resolve a token for each connection attempt. */
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
   * Returns a current access token, or {@code null} in signature mode.
   *
   * <p>The token is resolved for each connection attempt and refreshed synchronously using the
   * session manager's single-flight refresh.</p>
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
   * Builds socket authentication from the configured authentication implementation.
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

  /** Returns the session manager from a configured {@link OAuth2Authentication}. */
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

  /** Returns a representation that excludes signature and access-token values. */
  @Override
  public String toString() {
    return "ApiAuthentication{tigerId=" + tigerId
        + ", version=" + version
        + ", oauth2=" + isOauth2()
        + ", hasSign=" + !StringUtils.isEmpty(sign) + "}";
  }
}
