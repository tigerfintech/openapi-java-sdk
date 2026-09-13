package com.tigerbrokers.stock.openapi.client.auth.oauth2;

import com.tigerbrokers.stock.openapi.client.auth.Authentication;
import com.tigerbrokers.stock.openapi.client.auth.AuthenticationAttempt;
import com.tigerbrokers.stock.openapi.client.auth.AuthenticationType;
import com.tigerbrokers.stock.openapi.client.auth.RequestAuthContext;
import com.tigerbrokers.stock.openapi.client.auth.RetryDecision;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.HttpResult;

/**
 * Applies an OAuth2 access token as an {@code Authorization: Bearer} header.
 *
 * <p>Authorization, registration, token storage, and refresh are delegated to
 * {@link OAuth2SessionManager}. OAuth2 requests omit signature fields.</p>
 */
public class OAuth2Authentication implements Authentication {

  private static final String BEARER_PREFIX = "Bearer ";

  private final OAuth2SessionManager sessions;

  /**
   * @param sessions session manager used to obtain access tokens; construction does not start
   *     interactive authorization
   */
  public OAuth2Authentication(OAuth2SessionManager sessions) {
    if (sessions == null) {
      throw new IllegalArgumentException("OAuth2SessionManager is required");
    }
    this.sessions = sessions;
  }

  @Override
  public AuthenticationType type() {
    return AuthenticationType.OAUTH2;
  }

  /**
   * {@inheritDoc}
   *
   * <p>If the token is close to expiry this refreshes synchronously (single-flight, so
   * concurrent callers cause only one refresh). If authorization has never happened it
   * throws {@link OAuth2Exception} with category {@code REAUTHORIZATION_REQUIRED} -- it
   * never opens a browser implicitly.
   */
  @Override
  public AuthenticationAttempt apply(RequestAuthContext context) {
    String accessToken = sessions.getAccessToken();
    context.setAuthorizationHeader(BEARER_PREFIX + accessToken);
    return new AuthenticationAttempt(AuthenticationType.OAUTH2, accessToken);
  }

  /**
   * {@inheritDoc}
   *
   * <p>Refreshes only after an unauthorized response. Permission failures, server errors, and
   * timeouts do not trigger token refresh. Non-retryable requests are not resubmitted.</p>
   */
  @Override
  public RetryDecision onUnauthorized(AuthenticationAttempt attempt, HttpResult response) {
    if (response == null || !response.isUnauthorized()) {
      return RetryDecision.noRetry();
    }
    try {
      // Reuse a token already replaced by another thread to preserve single-flight refresh.
      OAuth2Token refreshed = sessions.refreshAfterUnauthorized(
          attempt == null ? null : attempt.getCredential());
      if (refreshed == null || refreshed.getAccessToken() == null) {
        return RetryDecision.noRetry();
      }
      // Do not retry with the rejected credential.
      if (attempt != null && refreshed.getAccessToken().equals(attempt.getCredential())) {
        ApiLogger.warn("oauth2 got 401 but access token unchanged after refresh, no retry");
        return RetryDecision.noRetry();
      }
      ApiLogger.info("oauth2 access token refreshed after 401, retrying once");
      return RetryDecision.retry(BEARER_PREFIX + refreshed.getAccessToken());
    } catch (OAuth2Exception e) {
      // Failed refresh requires explicit reauthorization.
      ApiLogger.warn("oauth2 refresh after 401 failed:{}, re-authorization required", e.getMessage());
      return RetryDecision.noRetry();
    } catch (RuntimeException e) {
      ApiLogger.warn("oauth2 refresh after 401 error:{}", e.getMessage());
      return RetryDecision.noRetry();
    }
  }

  /** Does not close the application-owned session manager. */
  @Override
  public void close() {
    // no-op
  }

  public OAuth2SessionManager getSessionManager() {
    return sessions;
  }
}
