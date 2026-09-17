package com.tigerbrokers.stock.openapi.client.auth;

import com.tigerbrokers.stock.openapi.client.util.HttpResult;

/**
 * Applies request credentials, handles authentication retries, and releases authentication
 * resources.
 *
 * <p>Authorization and client registration are managed separately by
 * {@code OAuth2SessionManager}. Implementations must be thread-safe because an instance may be
 * shared across request threads.</p>
 */
public interface Authentication {

  /**
   * Which kind of authentication. For logging and assertions; business logic should not
   * branch on it.
   */
  AuthenticationType type();

  /**
   * Writes the credential into the request before sending, and returns a snapshot of
   * "which credential this request used".
   *
   * <p>The OAuth2 implementation may trigger a refresh here (token about to expire), so it
   * is allowed to throw
   * {@link com.tigerbrokers.stock.openapi.client.auth.oauth2.OAuth2Exception}.
   *
   * @return the snapshot, handed back unchanged to {@link #onUnauthorized} on a 401; see
   *     {@link AuthenticationAttempt}
   */
  AuthenticationAttempt apply(RequestAuthContext context);

  /**
   * Decides whether an unauthorized response should be retried with a replacement credential.
   *
   * <p>Authentication retries are limited to one per request and do not apply to permission
   * failures, server errors, or timeouts.</p>
   *
   * @param attempt credential snapshot returned by {@link #apply}
   * @param response response containing the HTTP status
   */
  RetryDecision onUnauthorized(AuthenticationAttempt attempt, HttpResult response);

  /** Releases resources held by the active authentication implementation. */
  void close();
}
