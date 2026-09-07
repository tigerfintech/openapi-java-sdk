package com.tigerbrokers.stock.openapi.client.auth;

import java.util.Map;

/**
 * The parts of a request that authentication is allowed to rewrite: the body parameters and
 * the {@code Authorization} header.
 *
 * <p>Only holds what authentication needs to touch. Business parameters like {@code method},
 * {@code version}, {@code biz_content} and {@code timestamp} are filled in by the client
 * itself, have nothing to do with authentication, and must not be modified by an
 * authentication implementation.
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
   * Whether this request may be retried with a new credential after a 401.
   *
   * <p>Named after the constraint rather than the business operation on purpose: an
   * authentication implementation has no business knowing what an order is, it only needs to
   * know whether it is allowed to send the request a second time.
   *
   * <p>{@code false} for order placement, modification and cancellation. Unless the server
   * explicitly guarantees that the auth check happens before the business logic runs, a 401
   * does not prove the request was not executed, and an automatic retry means a duplicate
   * order. The caller decides which requests fall into this category.
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
