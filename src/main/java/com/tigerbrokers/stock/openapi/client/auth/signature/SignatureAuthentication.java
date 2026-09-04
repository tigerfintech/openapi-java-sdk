package com.tigerbrokers.stock.openapi.client.auth.signature;

import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.ACCESS_TOKEN;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.ACCOUNT_TYPE;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.DEVICE_ID;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.SIGN;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.SIGN_TYPE;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.TIGER_ID;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.TRADE_TOKEN;

import com.tigerbrokers.stock.openapi.client.auth.Authentication;
import com.tigerbrokers.stock.openapi.client.auth.AuthenticationAttempt;
import com.tigerbrokers.stock.openapi.client.auth.AuthenticationType;
import com.tigerbrokers.stock.openapi.client.auth.RequestAuthContext;
import com.tigerbrokers.stock.openapi.client.auth.RetryDecision;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.util.HttpResult;
import com.tigerbrokers.stock.openapi.client.util.TigerSignature;
import java.util.Map;

/**
 * The legacy authentication method: {@code tiger_id} plus an RSA signature in the request body.
 *
 * <p>This is a <b>verbatim lift</b> of the signing logic in {@code TigerHttpClient#buildParams}:
 * field order, how the signed content is assembled, and which fields participate in the
 * signature are all unchanged -- the outgoing request has to stay byte-identical to what it
 * was before the refactor. When an existing user passes no {@code Authentication}, the SDK
 * creates this implementation automatically, so any behaviour change here hits every existing
 * user.
 *
 * <p>Three different "tokens" each have their own place here, with different locations and
 * formats. Mixing them up means either a failed signature or a clobbered auth header:
 * <ul>
 *   <li>{@code licenseToken} (HK market-data license) -- the HTTP {@code Authorization}
 *       header, <b>a bare value with no {@code Bearer } prefix</b>, not signed
 *   <li>{@code accessToken} (the legacy one) -- a top-level body field, signed
 *   <li>{@code tradeToken} (obtained with the trading password) -- a top-level body field, signed
 * </ul>
 */
public class SignatureAuthentication implements Authentication {

  private final String tigerId;
  private final String privateKey;
  private final String signType;
  private final String charset;
  private final String deviceId;

  private volatile String licenseToken;
  private volatile String accessToken;
  private volatile String tradeToken;
  private volatile String accountType;

  public SignatureAuthentication(String tigerId, String privateKey, String deviceId) {
    this(tigerId, privateKey, deviceId, TigerApiConstants.SIGN_TYPE_RSA, TigerApiConstants.UTF_8);
  }

  public SignatureAuthentication(String tigerId, String privateKey, String deviceId,
      String signType, String charset) {
    this.tigerId = tigerId;
    this.privateKey = privateKey;
    this.deviceId = deviceId;
    this.signType = signType;
    this.charset = charset;
  }

  @Override
  public AuthenticationType type() {
    return AuthenticationType.SIGNATURE;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Field write order is copied from the original implementation. {@code sign} must be
   * added last -- it signs every field before it.
   */
  @Override
  public AuthenticationAttempt apply(RequestAuthContext context) {
    Map<String, Object> params = context.getParams();
    params.put(TIGER_ID, this.tigerId);
    params.put(SIGN_TYPE, this.signType);
    if (this.accessToken != null) {
      params.put(ACCESS_TOKEN, this.accessToken);
    }
    if (this.tradeToken != null) {
      params.put(TRADE_TOKEN, this.tradeToken);
    }
    if (this.accountType != null) {
      params.put(ACCOUNT_TYPE, this.accountType);
    }
    if (this.deviceId != null) {
      params.put(DEVICE_ID, this.deviceId);
    }
    if (this.tigerId != null) {
      String content = TigerSignature.getSignContent(params);
      params.put(SIGN, TigerSignature.rsaSign(content, privateKey, charset));
    }
    // The HK license token is a bare value; no Bearer prefix
    context.setAuthorizationHeader(this.licenseToken);
    return new AuthenticationAttempt(AuthenticationType.SIGNATURE, this.tigerId);
  }

  /**
   * {@inheritDoc}
   *
   * <p>There is no "retry with a different credential" for signature auth: a private-key
   * signature does not start passing just because you sign again. Renewal of the HK license
   * token is handled separately by {@code TokenManager} and is unrelated to this.
   */
  @Override
  public RetryDecision onUnauthorized(AuthenticationAttempt attempt, HttpResult response) {
    return RetryDecision.noRetry();
  }

  @Override
  public void close() {
    // no-op: the scheduled refresh task is owned by TokenManager
  }

  public String getTigerId() {
    return tigerId;
  }

  /** The HK market-data license token, written as the bare {@code Authorization} value. */
  public void setLicenseToken(String licenseToken) {
    this.licenseToken = licenseToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public void setTradeToken(String tradeToken) {
    this.tradeToken = tradeToken;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }
}
