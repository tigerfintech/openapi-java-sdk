package com.tigerbrokers.stock.openapi.client.auth.oauth2;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * OAuth2 device authorization response defined by RFC 8628.
 *
 * <p>The user code and verification URI are intended for presentation. The device code is a
 * credential used only for token polling and must not be displayed or logged.</p>
 */
public class OAuth2DeviceAuthorization {

  /** Credential used to poll the token endpoint; excluded from logs and display. */
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
   * Suggested polling interval in seconds, as defined by RFC 8628.
   *
   * <p>A missing value uses
   * {@link OAuth2SessionManager#DEFAULT_DEVICE_POLL_INTERVAL_SECONDS}.</p>
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

  /** Returns the complete verification URI when available, otherwise the base URI. */
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
