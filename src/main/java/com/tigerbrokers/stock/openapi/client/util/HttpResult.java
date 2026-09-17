package com.tigerbrokers.stock.openapi.client.util;

public class HttpResult {

  private final int status;
  private final String body;

  public HttpResult(int status, String body) {
    this.status = status;
    this.body = body;
  }

  public int getStatus() {
    return status;
  }

  public String getBody() {
    return body;
  }

  public boolean isUnauthorized() {
    return status == 401;
  }

  public boolean isForbidden() {
    return status == 403;
  }

  public boolean isHttpSuccess() {
    return status >= 200 && status < 300;
  }

  @Override
  public String toString() {
    return "HttpResult{status=" + status
        + ", bodyLength=" + (body == null ? 0 : body.length()) + "}";
  }
}
