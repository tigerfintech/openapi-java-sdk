package com.tigerbrokers.stock.openapi.client.https.response;

import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;

/**
 * Created by cheneg on 2015/9/10.
 */
public class TigerHttpResponse extends TigerResponse {

  private String data;
  private String sign;

  public TigerHttpResponse() {
  }

  public TigerHttpResponse(String data) {
    this.data = data;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public static TigerHttpResponse errorMsg(int errCode, String errMsg) {
    TigerHttpResponse response = new TigerHttpResponse();
    response.setCode(errCode);
    response.setMessage(errMsg);
    response.setTimestamp(System.currentTimeMillis());
    response.setData("");
    return response;
  }

  public static TigerHttpResponse errorMsg(TigerApiCode apiCode) {
    return errorMsg(apiCode.getCode(), apiCode.getMessage());
  }

  public static TigerHttpResponse succeedMsg(String data) {
    TigerHttpResponse response = new TigerHttpResponse();
    response.setCode(TigerApiCode.SUCCESS.getCode());
    response.setMessage(TigerApiCode.SUCCESS.getMessage());
    response.setTimestamp(System.currentTimeMillis());
    response.setData(data);
    return response;
  }

  @Override
  public String toString() {
    return "TigerHttpResponse{" +
        "code='" + getCode() + '\'' +
        ", message='" + getMessage() + '\'' +
        ", data='" + data + '\'' +
        ", timestamp='" + getTimestamp() + '\'' +
        ", sign='" + sign + '\'' +
        '}';
  }
}
