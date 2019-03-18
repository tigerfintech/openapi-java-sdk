package com.tigerbrokers.stock.openapi.client.https.response;

/**
 * Created by cheneg on 2015/9/10.
 */
public class TigerHttpResponse extends TigerResponse {

  private String data;

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

  @Override
  public String toString() {
    return "TigerHttpResponse{" +
        "data='" + data + '\'' +
        '}';
  }
}
