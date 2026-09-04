package com.tigerbrokers.stock.openapi.client.auth;

/**
 * A snapshot of "which credential did this request use", returned by
 * {@link Authentication#apply} and handed back unchanged to
 * {@link Authentication#onUnauthorized} when a 401 arrives.
 *
 * <p><b>Why it is needed:</b> without it there is no way to stop a 401 storm. Say 20
 * concurrent requests all go out with token A and A expires right then, so 20 401s come
 * back. If we do not record which credential was used, the 1st 401 refreshes to B, the 2nd
 * comes back and refreshes B to C, the 3rd to D, and so on. After every refresh there is
 * still a batch of in-flight requests holding an older token, so it never converges.
 *
 * <p>Recording it makes this simple: 401s 2 through 20 compare "I used A, current is
 * already B" and retry with B directly. One refresh handles all of them.
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
   * The credential value actually used for this request (the raw access token in OAuth2 mode).
   *
   * <p>Only for equality comparison against the current credential. Do not print it, do not
   * expose it.
   */
  public String getCredential() {
    return credential;
  }

  /**
   * Never prints the credential value -- it is equivalent to a password.
   */
  @Override
  public String toString() {
    return "AuthenticationAttempt{type=" + type
        + ", credential=" + (credential == null ? "<none>" : "***") + "}";
  }
}
