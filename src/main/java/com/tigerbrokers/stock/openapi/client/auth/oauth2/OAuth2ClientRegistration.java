package com.tigerbrokers.stock.openapi.client.auth.oauth2;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * The client_id obtained via dynamic registration (RFC 7591), one per issuer.
 *
 * <p>It is stored separately so that logout can clear only the token and keep the client_id
 * -- the next authorization then needs no re-registration. Otherwise junk clients pile up on
 * the AS and the user sees dozens of identically named applications on the authorization
 * management page.</p>
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
   * The scope the AS granted this client in the registration response.
   *
   * <p>Not what we asked for -- the registration request declares no scope, so what we get is
   * the AS's policy. Recording it is what lets us tell whether "the scope we need now is
   * still within range": if the server later adds a new scope this one is no longer enough
   * and we have to register a fresh client, otherwise authorization is rejected with
   * {@code invalid_scope}.</p>
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
