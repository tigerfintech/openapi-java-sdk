package com.tigerbrokers.stock.openapi.client.auth.oauth2;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * The complete result of one authorization, and also the on-disk format.
 *
 * <p>{@code issuer} and {@code clientId} have to be recorded: the filename only carries the
 * clientId, so after switching environments (production to staging) a missing issuer check
 * would send a staging token to production, showing up as an inexplicable 401.</p>
 */
public class OAuth2Token {

  /** Storage format version. An unrecognized value must raise an error rather than be guessed at. */
  @JSONField(name = "schema_version")
  private int schemaVersion = 1;

  @JSONField(name = "issuer")
  private String issuer;

  @JSONField(name = "client_id")
  private String clientId;

  @JSONField(name = "access_token")
  private String accessToken;

  @JSONField(name = "refresh_token")
  private String refreshToken;

  @JSONField(name = "token_type")
  private String tokenType = "Bearer";

  @JSONField(name = "scope")
  private String scope;

  /** Access token expiry as an absolute epoch-millis timestamp -- not expires_in. */
  @JSONField(name = "expires_at")
  private long expiresAt;

  public int getSchemaVersion() {
    return schemaVersion;
  }

  public void setSchemaVersion(int schemaVersion) {
    this.schemaVersion = schemaVersion;
  }

  public String getIssuer() {
    return issuer;
  }

  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public long getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(long expiresAt) {
    this.expiresAt = expiresAt;
  }

  /** How long until expiry; negative once already expired. */
  public long remainingMillis() {
    return expiresAt - System.currentTimeMillis();
  }

  /**
   * Whether it is time to refresh.
   *
   * @param aheadMillis how early to refresh. The window exists because time passes between
   *                    sending a request and the server validating it, so using a token right
   *                    up to its expiry produces intermittent 401s
   */
  public boolean needsRefresh(long aheadMillis) {
    return remainingMillis() <= aheadMillis;
  }

  public boolean hasRefreshToken() {
    return refreshToken != null && !refreshToken.isEmpty();
  }

  /**
   * Prints no token values.
   *
   * <p>This object shows up in logs and exceptions, and an access token is equivalent to a
   * password.</p>
   */
  @Override
  public String toString() {
    return "OAuth2Token{clientId=" + clientId
        + ", issuer=" + issuer
        + ", scope=" + scope
        + ", expiresAt=" + expiresAt
        + ", hasRefreshToken=" + hasRefreshToken() + "}";
  }
}
