package com.tigerbrokers.stock.openapi.client.auth.oauth2;

/**
 * A failure during OAuth2 authorization.
 *
 * <p>It carries a {@link Category} rather than an error-code string because the only
 * distinctions a caller actually acts on are "retry", "re-authorize" and "fix your config".
 * The raw error returned by the AS goes in the message for diagnosis.</p>
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
     * Device flow: the user did not approve within the user_code's lifetime, or explicitly denied.
     *
     * <p>Kept separate from {@link #REAUTHORIZATION_REQUIRED} because the action differs: this
     * one only needs <b>a fresh device authorization</b> (a new user_code). The local token was
     * not touched, and this is not "authorization became invalid".</p>
     */
    DEVICE_AUTHORIZATION_FAILED,

    /**
     * The user has to go through authorization again.
     *
     * <p>The refresh_token expired, authorization was revoked, or it never happened at all.
     * Retrying is pointless.</p>
     */
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

  /** Whether this means "re-authorization is the only option" -- callers use it to decide whether to prompt. */
  public boolean isReauthorizationRequired() {
    return category == Category.REAUTHORIZATION_REQUIRED;
  }

  @Override
  public String toString() {
    return "OAuth2Exception{" + category + ": " + getMessage() + "}";
  }
}
