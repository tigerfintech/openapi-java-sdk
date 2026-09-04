package com.tigerbrokers.stock.openapi.client.auth.oauth2;

import com.tigerbrokers.stock.openapi.client.auth.Authentication;
import com.tigerbrokers.stock.openapi.client.auth.AuthenticationAttempt;
import com.tigerbrokers.stock.openapi.client.auth.AuthenticationType;
import com.tigerbrokers.stock.openapi.client.auth.RequestAuthContext;
import com.tigerbrokers.stock.openapi.client.auth.RetryDecision;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.HttpResult;

/**
 * Authenticates requests with an OAuth2 access token: {@code Authorization: Bearer <jwt>}.
 *
 * <p>It is just an adapter -- it takes a currently valid token from
 * {@link OAuth2SessionManager} and writes it as a Bearer header. Authorization,
 * registration, storage and refresh all live in the SessionManager; no business logic here.
 *
 * <p><b>Sends no signature field at all.</b> The gateway branches on "does the body contain
 * {@code sign}" -- if it does, it takes the signature-verification branch, the Bearer header
 * is ignored and then misread as an HK license token, and the resulting error has nothing to
 * do with the auth method. So even if a private key is still stored locally, OAuth2 mode
 * never falls back to signing.
 *
 * <p>Usage:
 * <pre>{@code
 * OAuth2SessionManager sessions = OAuth2SessionManager.builder().clientId("...").build();
 * sessions.loginIfNeeded(url -> System.out.println("open: " + url));
 *
 * ClientConfig config = new ClientConfig();
 * config.authentication = new OAuth2Authentication(sessions);
 * TigerHttpClient client = TigerHttpClient.getInstance().clientConfig(config);
 * }</pre>
 */
public class OAuth2Authentication implements Authentication {

  private static final String BEARER_PREFIX = "Bearer ";

  private final OAuth2SessionManager sessions;

  /**
   * @param sessions an already-built session manager. Construction does <b>not</b> start
   *     authorization -- authorization is a user interaction and must be initiated
   *     explicitly by the application, e.g. via {@code loginIfNeeded()}.
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
   * <p>Only refreshes on a 401. A 403 means insufficient permission (scope too narrow,
   * account not granted, or the endpoint is closed to OAuth2 entirely) -- a refresh returns
   * the same scope, so the retry is guaranteed to be another 403 and is pure waste. 5xx and
   * timeouts have nothing to do with the token.
   *
   * <p>Order-placing requests are never retried: a 401 does not prove the order failed, so a
   * retry risks placing a duplicate.
   */
  @Override
  public RetryDecision onUnauthorized(AuthenticationAttempt attempt, HttpResult response) {
    if (response == null || !response.isUnauthorized()) {
      return RetryDecision.noRetry();
    }
    try {
      // Pass the token string we just used: if another thread already rotated it, this
      // returns the new one without hitting the token endpoint again -- that is what keeps
      // a 401 storm from diverging
      OAuth2Token refreshed = sessions.refreshAfterUnauthorized(
          attempt == null ? null : attempt.getCredential());
      if (refreshed == null || refreshed.getAccessToken() == null) {
        return RetryDecision.noRetry();
      }
      // Only retry once the credential genuinely changed; otherwise we would resend the
      // same rejected token
      if (attempt != null && refreshed.getAccessToken().equals(attempt.getCredential())) {
        ApiLogger.warn("oauth2 got 401 but access token unchanged after refresh, no retry");
        return RetryDecision.noRetry();
      }
      ApiLogger.info("oauth2 access token refreshed after 401, retrying once");
      return RetryDecision.retry(BEARER_PREFIX + refreshed.getAccessToken());
    } catch (OAuth2Exception e) {
      // Refresh failed (refresh_token also expired, or authorization was revoked): only
      // re-authorization helps, retrying is pointless
      ApiLogger.warn("oauth2 refresh after 401 failed:{}, re-authorization required", e.getMessage());
      return RetryDecision.noRetry();
    } catch (RuntimeException e) {
      ApiLogger.warn("oauth2 refresh after 401 error:{}", e.getMessage());
      return RetryDecision.noRetry();
    }
  }

  /** The session is owned by the application and may still be in use, so we do not close it. */
  @Override
  public void close() {
    // no-op
  }

  public OAuth2SessionManager getSessionManager() {
    return sessions;
  }
}
