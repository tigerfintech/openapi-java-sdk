package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by liutongping on 2022/03/08.
 */
public enum Protocol {
  HTTP("https://%s/gateway", ""),
  STOMP_WEBSOCKET("wss://%s:%s/stomp", "port"),
  STOMP("wss://%s:%s", "socket_port");

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
