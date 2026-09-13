package com.tigerbrokers.stock.openapi.client.auth;

import java.util.Map;

/**
 * Mutable request fields available to an authentication implementation.
 *
 * <p>Authentication may update body credential fields and the {@code Authorization} header but
 * must not modify business request parameters.</p>
 */
public class RequestAuthContext {

  private final Map<String, Object> params;
  private final boolean retryable;
  private String authorizationHeader;

  /**
   * @param params the body parameters; an authentication implementation may add fields here
   *     (signature mode adds tiger_id, sign and so on)
   * @param retryable whether this request may be retried with a new credential after a 401;
   *     see {@link #isRetryable()}
   */
  public RequestAuthContext(Map<String, Object> params, boolean retryable) {
    this.params = params;
    this.retryable = retryable;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  /**
   * Indicates whether authentication may retry the request with a replacement credential.
   *
   * <p>Requests with non-idempotent effects must not be retried unless the server guarantees
   * authentication is completed before request processing.</p>
   */
  public boolean isRetryable() {
    return retryable;
  }

  /** The full {@code Authorization} header value; null means do not send the header. */
  public String getAuthorizationHeader() {
    return authorizationHeader;
  }

  public void setAuthorizationHeader(String authorizationHeader) {
    this.authorizationHeader = authorizationHeader;
  }
}
