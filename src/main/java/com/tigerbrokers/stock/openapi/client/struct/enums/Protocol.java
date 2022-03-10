package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by liutongping on 2022/03/08.
 */
public enum Protocol {
  HTTP("https://%s/gateway", ""),
  STOMP("wss://%s:%s/stomp", "port"),
  SOCKET("wss://%s:%s", "socket_port");

  private String urlFormat;
  private String portFieldName;

  Protocol(String urlFormat, String portFieldName) {
    this.urlFormat = urlFormat;
    this.portFieldName = portFieldName;
  }

  public String getUrlFormat() {
    return urlFormat;
  }

  public String getPortFieldName() {
    return portFieldName;
  }
}
