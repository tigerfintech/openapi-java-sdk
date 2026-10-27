package com.tigerbrokers.stock.openapi.client.testsupport;

/** wire 信封格式，与服务端一致。行情与资讯的逻辑测试共用。 */
public class WireResponse {

  public int code;
  public String message;
  public Object data;
  public long timestamp = 1700000000;

  public WireResponse(int code, String message, Object data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }
}
