package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by lijiawen on 2018/07/17.
 */
public enum TimeZoneId {

  Shanghai("Asia/Shanghai"),
  NewYork("America/New_York"),
  Singapore("Asia/Singapore");

  private String zoneId;

  TimeZoneId(String zoneId) {
    this.zoneId = zoneId;
  }

  public String getZoneId() {
    return zoneId;
  }
}
