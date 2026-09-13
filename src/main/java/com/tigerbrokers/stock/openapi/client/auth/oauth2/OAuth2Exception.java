package com.tigerbrokers.stock.openapi.client.auth.oauth2;

/**
 * OAuth2 authorization or token-management failure.
 *
 * <p>The category identifies the required recovery action. Server error details are retained in
 * the exception message.</p>
 */
public class OAuth2Exception extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public enum Category {
    /** Cannot fetch the AS metadata: wrong address, service down, or no network. */
    METADATA_UNAVAILABLE,

    /** No usable client_id, and automatic registration is not possible. */
    CLIENT_REGISTRATION_REQUIRED,

    /** The callback stage failed: timeout, user denial, or a state mismatch. */
    CALLBACK_FAILED,

    /**
     * Device authorization did not complete before expiration or was denied.
     *
     * <p>A new device authorization is required; existing persisted tokens are not affected.</p>
     */
    DEVICE_AUTHORIZATION_FAILED,

    /** Authorization must be performed again before a token can be obtained. */
    REAUTHORIZATION_REQUIRED,

    /** The token endpoint call failed, and not in the terminal way above. */
    TOKEN_REQUEST_FAILED,

    /** Reading or writing the local token file failed. */
    STORAGE_FAILED
  }

  private final Category category;

  public OAuth2Exception(Category category, String message) {
    super(message);
    this.category = category;
  }

  public OAuth2Exception(Category category, String message, Throwable cause) {
    super(message, cause);
    this.category = category;
  }

  public Category getCategory() {
    return category;
  }

  /** Returns whether reauthorization is required. */
  public boolean isReauthorizationRequired() {
    return category == Category.REAUTHORIZATION_REQUIRED;
  }

  @Override
  public String toString() {
    return "OAuth2Exception{" + category + ": " + getMessage() + "}";
  }
}
