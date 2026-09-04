package com.tigerbrokers.stock.openapi.client.auth.oauth2;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;

/**
 * The parts of the AS metadata ({@code /.well-known/oauth-authorization-server}) this SDK uses.
 *
 * <p>Endpoint addresses always come from here and are never hardcoded in the SDK -- with a
 * split frontend the issuer may point at the frontend, and only the server knows which origin
 * the browser should actually open.</p>
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
   * Every scope the AS supports (RFC 8414 {@code scopes_supported}).
   *
   * <p>When the application specifies no scope we request all of these. The list does change
   * -- when the server adds a new scope the SDK keeps up without a code change, whereas a
   * hardcoded copy would drift.</p>
   *
   * <p>The spec marks this field RECOMMENDED rather than REQUIRED, so it may be null.</p>
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
