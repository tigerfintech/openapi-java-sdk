package com.tigerbrokers.stock.openapi.client.auth;

/**
 * Authentication retry decision and replacement credential.
 *
 * <p>A retry must use a credential different from the rejected credential.</p>
 */
public class RetryDecision {

  private static final RetryDecision NO_RETRY = new RetryDecision(false, null);

  private final boolean shouldRetry;
  private final String authorizationHeader;

  private RetryDecision(boolean shouldRetry, String authorizationHeader) {
    this.shouldRetry = shouldRetry;
    this.authorizationHeader = authorizationHeader;
  }

  /** Do not retry: the error is returned to the caller as-is. */
  public static RetryDecision noRetry() {
    return NO_RETRY;
  }

  /**
   * Retry once with a new credential.
   *
   * @param authorizationHeader the full new {@code Authorization} header value, including
   *     the {@code Bearer } prefix
   */
  public static RetryDecision retry(String authorizationHeader) {
    return new RetryDecision(true, authorizationHeader);
  }

  public boolean shouldRetry() {
    return shouldRetry;
  }

  public String getAuthorizationHeader() {
    return authorizationHeader;
  }

  @Override
  public String toString() {
    return "RetryDecision{shouldRetry=" + shouldRetry + "}";
  }
}
