package com.tigerbrokers.stock.openapi.client.socket;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.TigerSignature;
import com.tigerbrokers.stock.openapi.client.util.builder.HeaderBuilder;

/**
 * Description:
 * Created by lijiawen on 2018/06/06.
 */
public class ApiAuthentication {

  private ClientConfig clientConfig;
  private String tigerId;
  private String sign;
  private String version = HeaderBuilder.DEFAULT_VERSION;

  public ApiAuthentication(ClientConfig clientConfig) {
    this.clientConfig = clientConfig;
    this.tigerId = clientConfig.tigerId;
  }

  public ClientConfig getClientConfig() {
    return clientConfig;
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

  public static ApiAuthentication build(ClientConfig clientConfig) {
    return build(clientConfig, clientConfig.privateKey, HeaderBuilder.DEFAULT_VERSION);
  }

  public static ApiAuthentication build(ClientConfig clientConfig, String privateKey) {
    return build(clientConfig, privateKey, HeaderBuilder.DEFAULT_VERSION);
  }

  public static ApiAuthentication build(ClientConfig clientConfig, String privateKey, String version) {
    ApiAuthentication authentication = new ApiAuthentication(clientConfig);
    try {
      String sign = TigerSignature.rsaSign(clientConfig.tigerId, privateKey, TigerApiConstants.UTF_8);
      authentication.setSign(sign);
      authentication.setVersion(version);
    } catch (Exception e) {
      ApiLogger.error("authentication build exception:{}", e.getMessage(), e);
      return null;
    }
    return authentication;
  }
}
