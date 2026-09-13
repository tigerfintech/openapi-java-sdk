package com.tigerbrokers.stock.openapi.client.auth;

/**
 * Immutable snapshot of the credential applied to a request.
 *
 * <p>The snapshot allows concurrent unauthorized responses to share a single credential refresh
 * and prevents repeated rotation of an already replaced credential.</p>
 */
public class AuthenticationAttempt {

  private final AuthenticationType type;
  private final String credential;

  public AuthenticationAttempt(AuthenticationType type, String credential) {
    this.type = type;
    this.credential = credential;
  }

  public AuthenticationType getType() {
    return type;
  }

  /**
   * Returns the credential applied to the request.
   *
   * <p>The value is confidential and is intended only for comparison with the current
   * credential.</p>
   */
  public String getCredential() {
    return credential;
  }

  /** Returns a representation that excludes the credential value. */
  @Override
  public String toString() {
    return "AuthenticationAttempt{type=" + type
        + ", credential=" + (credential == null ? "<none>" : "***") + "}";
  }
}
