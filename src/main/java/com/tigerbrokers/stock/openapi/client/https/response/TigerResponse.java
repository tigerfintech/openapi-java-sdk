package com.tigerbrokers.stock.openapi.client.https.response;

import java.io.Serializable;

/**
 * API基础响应信息。
 */
public abstract class TigerResponse implements Serializable {

  private static final long serialVersionUID = 5014379068811962022L;

  private int code;

  private String message;

  private long timestamp;

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
}