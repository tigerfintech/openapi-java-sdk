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
  private final boolean placeOrder;
  private String authorizationHeader;

  /**
   * @param params the body parameters; an authentication implementation may add fields here
   *     (signature mode adds tiger_id, sign and so on)
   * @param placeOrder whether this is an order-placing request -- those must not be retried
   *     automatically, see {@link #isPlaceOrder()}
   */
  public RequestAuthContext(Map<String, Object> params, boolean placeOrder) {
    this.params = params;
    this.placeOrder = placeOrder;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  /**
   * Whether this is an order-placing request.
   *
   * <p>Such requests <b>must not</b> be retried automatically on authentication failure:
   * unless the server explicitly guarantees that the auth check happens before the business
   * logic runs, a 401 does not prove the order was not placed, and an automatic retry means
   * a duplicate order. The existing code already sets the fail-retry count to 0 for order
   * placement; that semantic has to be preserved.
   */
  public boolean isPlaceOrder() {
    return placeOrder;
  }

  /** The full {@code Authorization} header value; null means do not send the header. */
  public String getAuthorizationHeader() {
    return authorizationHeader;
  }

  public void setAuthorizationHeader(String authorizationHeader) {
    this.authorizationHeader = authorizationHeader;
  }
}
