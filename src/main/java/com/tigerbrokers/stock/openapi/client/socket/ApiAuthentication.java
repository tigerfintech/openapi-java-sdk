package com.tigerbrokers.stock.openapi.client.socket;

import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.TigerSignature;
import com.tigerbrokers.stock.openapi.client.util.builder.StompHeaderBuilder;

/**
 * Description:
 * Created by lijiawen on 2018/06/06.
 */
public class ApiAuthentication {

  private String tigerId;
  private String sign;
  private String version = StompHeaderBuilder.VERSION;

  public ApiAuthentication(String tigerId) {
    this.tigerId = tigerId;
  }

  public String getTigerId() {
    return this.tigerId;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public static ApiAuthentication build(String tigerId, String privateKey) {
    return build(tigerId, privateKey, StompHeaderBuilder.VERSION);
  }

  public static ApiAuthentication build(String tigerId, String privateKey, String version) {
    ApiAuthentication authentication = new ApiAuthentication(tigerId);
    try {
      String sign = TigerSignature.rsaSign(tigerId, privateKey, TigerApiConstants.CHARSET_UTF8);
      authentication.setSign(sign);
      authentication.setVersion(version);
    } catch (Exception e) {
      ApiLogger.error("authentication build exception:{}", e.getMessage(), e);
      return null;
    }
    return authentication;
  }
}
