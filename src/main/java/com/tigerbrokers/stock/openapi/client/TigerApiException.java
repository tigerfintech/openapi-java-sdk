package com.tigerbrokers.stock.openapi.client;

import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;

/**
 * Created by gaoan on 8/26/15.
 */
public class TigerApiException extends Exception {

  private static final long serialVersionUID = -238091758285157331L;

  private int errCode;
  private String errMsg;
  private TigerApiCode tigerApiCode;

  public TigerApiException() {
    super();
  }

  public TigerApiException(String message, Throwable cause) {
    super(message, cause);
  }

  public TigerApiException(String message) {
    super(message);
  }

  public TigerApiException(Throwable cause) {
    super(cause);
  }

  public TigerApiException(int errCode, String errMsg) {
    super(errCode + ":" + errMsg);
    this.errCode = errCode;
    this.errMsg = errMsg;
  }

  public TigerApiException(TigerApiCode error) {
    this(error.getCode(), error.getMessage());
    this.tigerApiCode = error;
  }

  public int getErrCode() {
    return this.errCode;
  }

  public String getErrMsg() {
    return this.errMsg;
  }

  public TigerApiCode getApiError() {
    return tigerApiCode;
  }
}
