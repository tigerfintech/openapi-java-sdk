package com.tigerbrokers.stock.openapi.client.auth.oauth2;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;

/**
 * OAuth2 authorization server metadata used by the SDK.
 *
 * <p>Endpoint locations are obtained from
 * {@code /.well-known/oauth-authorization-server}.</p>
 */
public class OAuth2Metadata {

  @JSONField(name = "issuer")
  private String issuer;

  @JSONField(name = "authorization_endpoint")
  private String authorizationEndpoint;

  @JSONField(name = "token_endpoint")
  private String tokenEndpoint;

  @JSONField(name = "registration_endpoint")
  private String registrationEndpoint;

  /** The device-flow entry point. Null when the AS does not support the device flow. */
  @JSONField(name = "device_authorization_endpoint")
  private String deviceAuthorizationEndpoint;

  @JSONField(name = "revocation_endpoint")
  private String revocationEndpoint;

  /**
   * Scopes advertised in RFC 8414 {@code scopes_supported} metadata.
   *
   * <p>The field is recommended rather than required by RFC 8414 and may be absent.</p>
   */
  @JSONField(name = "scopes_supported")
  private List<String> scopesSupported;

  public String getIssuer() {
    return issuer;
  }

  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }

  public String getAuthorizationEndpoint() {
    return authorizationEndpoint;
  }

  public void setAuthorizationEndpoint(String authorizationEndpoint) {
    this.authorizationEndpoint = authorizationEndpoint;
  }

  public String getTokenEndpoint() {
    return tokenEndpoint;
  }

  public void setTokenEndpoint(String tokenEndpoint) {
    this.tokenEndpoint = tokenEndpoint;
  }

  public String getRegistrationEndpoint() {
    return registrationEndpoint;
  }

  public void setRegistrationEndpoint(String registrationEndpoint) {
    this.registrationEndpoint = registrationEndpoint;
  }

  public String getDeviceAuthorizationEndpoint() {
    return deviceAuthorizationEndpoint;
  }

  public void setDeviceAuthorizationEndpoint(String deviceAuthorizationEndpoint) {
    this.deviceAuthorizationEndpoint = deviceAuthorizationEndpoint;
  }

  public String getRevocationEndpoint() {
    return revocationEndpoint;
  }

  public void setRevocationEndpoint(String revocationEndpoint) {
    this.revocationEndpoint = revocationEndpoint;
  }

  public List<String> getScopesSupported() {
    return scopesSupported;
  }

  public void setScopesSupported(List<String> scopesSupported) {
    this.scopesSupported = scopesSupported;
  }
}
