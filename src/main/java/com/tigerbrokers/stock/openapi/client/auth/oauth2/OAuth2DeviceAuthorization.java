package com.tigerbrokers.stock.openapi.client.auth.oauth2;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * The device authorization response (RFC 8628 §3.2) -- the values meant to be shown to the user.
 *
 * <p>Once the application has it, it is responsible for displaying {@link #getUserCode()} and
 * {@link #getVerificationUri()} (or turning {@link #getVerificationUriComplete()} straight into
 * a QR code). The SDK then starts polling for the user's approval.</p>
 *
 * <p>{@code deviceCode} is the credential the SDK polls with, not something to show a person --
 * it is equivalent to a one-time password, so do not display it and do not log it.
 * {@link #toString()} therefore omits it.</p>
 */
public class OAuth2DeviceAuthorization {

  /** The credential used to poll the token endpoint. Equivalent to a password; do not leak it. */
  @JSONField(name = "device_code")
  private String deviceCode;

  /** The short code shown to the user to type into the verification page, e.g. {@code SXPS-VLZK}. */
  @JSONField(name = "user_code")
  private String userCode;

  /** The verification page the user should open. */
  @JSONField(name = "verification_uri")
  private String verificationUri;

  /**
   * The complete verification link with the user_code already included (OPTIONAL in RFC 8628).
   *
   * <p>When present it saves the user from typing, which suits a QR code or a clickable link.
   * When null, fall back to {@link #getVerificationUri()} plus typing
   * {@link #getUserCode()}.</p>
   */
  @JSONField(name = "verification_uri_complete")
  private String verificationUriComplete;

  /** How long the user_code stays valid, in seconds. Once expired, start device authorization again. */
  @JSONField(name = "expires_in")
  private long expiresIn;

  /**
   * The suggested polling interval in seconds. OPTIONAL in RFC 8628.
   *
   * <p>Tiger's AS <b>does not return this field</b>, in which case the spec's 5-second default
   * applies. See {@link OAuth2SessionManager#DEFAULT_DEVICE_POLL_INTERVAL_SECONDS}.</p>
   */
  @JSONField(name = "interval")
  private Long interval;

  public String getDeviceCode() {
    return deviceCode;
  }

  public void setDeviceCode(String deviceCode) {
    this.deviceCode = deviceCode;
  }

  public String getUserCode() {
    return userCode;
  }

  public void setUserCode(String userCode) {
    this.userCode = userCode;
  }

  public String getVerificationUri() {
    return verificationUri;
  }

  public void setVerificationUri(String verificationUri) {
    this.verificationUri = verificationUri;
  }

  public String getVerificationUriComplete() {
    return verificationUriComplete;
  }

  public void setVerificationUriComplete(String verificationUriComplete) {
    this.verificationUriComplete = verificationUriComplete;
  }

  public long getExpiresIn() {
    return expiresIn;
  }

  public void setExpiresIn(long expiresIn) {
    this.expiresIn = expiresIn;
  }

  public Long getInterval() {
    return interval;
  }

  public void setInterval(Long interval) {
    this.interval = interval;
  }

  /**
   * Prefers the complete link, falling back to the verification page address.
   *
   * <p>Lets the application display it in one line without null-checking itself.</p>
   */
  public String getBestVerificationUri() {
    return verificationUriComplete != null && !verificationUriComplete.isEmpty()
        ? verificationUriComplete : verificationUri;
  }

  /** Omits deviceCode -- that is a credential. */
  @Override
  public String toString() {
    return "OAuth2DeviceAuthorization{userCode=" + userCode
        + ", verificationUri=" + verificationUri
        + ", expiresIn=" + expiresIn + "s}";
  }
}
