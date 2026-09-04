package com.tigerbrokers.stock.openapi.client.auth;

import com.tigerbrokers.stock.openapi.client.util.HttpResult;

/**
 * How a request authenticates. The only data-plane abstraction, and it does just three
 * things: attach the credential before sending, decide whether to swap credentials and
 * retry after a 401, and release whatever resources it holds.
 *
 * <p>It <b>deliberately has no</b> login, dynamic-registration or open-browser methods --
 * those belong to the control plane ({@code OAuth2SessionManager}). That way
 * {@link com.tigerbrokers.stock.openapi.client.auth.signature.SignatureAuthentication}
 * does not have to write {@code throw UnsupportedOperationException} for a pile of
 * authorization methods that mean nothing to it, and the business client never needs to
 * know what an OAuth flow is.
 *
 * <p>Implementations must be thread-safe: {@code TigerHttpClient} is a JVM singleton, so
 * one instance is shared across threads.
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
   * What to do after a 401.
   *
   * <p>Rules implementations must follow (consistent across the language SDKs): only
   * refresh for a 401 that means "credential is invalid" -- never for insufficient
   * permission (403), a server error (5xx), or a timeout; retry at most once per request
   * for authentication reasons; and only retry once a genuinely new credential is in hand.
   *
   * @param attempt the snapshot {@link #apply} returned at the time
   * @param response the response received, including its HTTP status
   */
  RetryDecision onUnauthorized(AuthenticationAttempt attempt, HttpResult response);

  /**
   * Releases resources (background refresh tasks and the like).
   *
   * <p>Must be called when switching authentication method -- otherwise the old background
   * task keeps refreshing a token nobody uses any more.
   */
  void close();
}
