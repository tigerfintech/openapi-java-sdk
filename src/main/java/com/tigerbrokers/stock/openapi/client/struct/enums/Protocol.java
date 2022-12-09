package com.tigerbrokers.stock.openapi.client.struct.enums;

import com.tigerbrokers.stock.openapi.client.util.StringUtils;

/**
 * Description:
 * Created by liutongping on 2022/03/08.
 */
public enum Protocol {
  HTTP("https://%s/gateway", ""),
  WEB_SOCKET("wss://%s:%s/stomp", "port"),
  SECURE_SOCKET("wss://%s:%s", "socket_port");

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

  public static boolean isWebSocketUrl(String url) {
    if (StringUtils.isEmpty(url)) {
      return false;
    }
    String webSocketUrlFormat = WEB_SOCKET.getUrlFormat();
    String suffix = webSocketUrlFormat.substring(webSocketUrlFormat.lastIndexOf('/'));
    return url.endsWith(suffix);
  }
}
