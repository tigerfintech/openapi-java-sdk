package com.tigerbrokers.stock.openapi.client.https.response;

import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;
import java.io.Serializable;

/**
 * API基础响应信息。
 */
public class TigerResponse implements Serializable {

  private static final long serialVersionUID = 5014379068811962022L;

  private int code;

  private String message;

  private long timestamp;

  private String sign;

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public boolean isSuccess() {
    return code == TigerApiCode.SUCCESS.getCode();
  }
}