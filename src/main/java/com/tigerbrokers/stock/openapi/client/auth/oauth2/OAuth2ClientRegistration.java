package com.tigerbrokers.stock.openapi.client.auth.oauth2;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Persisted OAuth2 dynamic client registration defined by RFC 7591.
 *
 * <p>Registrations are stored per issuer and retained when authorization tokens are removed.</p>
 */
public class OAuth2ClientRegistration {

  @JSONField(name = "schema_version")
  private int schemaVersion = 1;

  @JSONField(name = "issuer")
  private String issuer;

  @JSONField(name = "client_id")
  private String clientId;

  @JSONField(name = "client_name")
  private String clientName;

  /**
   * Scope granted to the client by the authorization server during registration.
   *
   * <p>A registration that no longer covers the requested scopes must be replaced to avoid an
   * {@code invalid_scope} response.</p>
   */
  @JSONField(name = "registered_scopes")
  private String registeredScopes;

  @JSONField(name = "client_id_issued_at")
  private long clientIdIssuedAt;

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

  public String getClientName() {
    return clientName;
  }

  public void setClientName(String clientName) {
    this.clientName = clientName;
  }

  public String getRegisteredScopes() {
    return registeredScopes;
  }

  public void setRegisteredScopes(String registeredScopes) {
    this.registeredScopes = registeredScopes;
  }

  public long getClientIdIssuedAt() {
    return clientIdIssuedAt;
  }

  public void setClientIdIssuedAt(long clientIdIssuedAt) {
    this.clientIdIssuedAt = clientIdIssuedAt;
  }

  @Override
  public String toString() {
    return "OAuth2ClientRegistration{clientId=" + clientId
        + ", issuer=" + issuer
        + ", registeredScopes=" + registeredScopes + "}";
  }
}
