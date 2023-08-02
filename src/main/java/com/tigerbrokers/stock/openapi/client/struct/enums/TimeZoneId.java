package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by lijiawen on 2018/07/17.
 */
public enum TimeZoneId {

  HongKong("Asia/Hong_Kong"),
  Shanghai("Asia/Shanghai"),
  NewYork("America/New_York"),
  Chicago("America/Chicago"),
  Singapore("Asia/Singapore"),
  Sydney("Australia/Sydney"),
  Auckland("Pacific/Auckland"),
  London("Europe/London");

  private String zoneId;

  TimeZoneId(String zoneId) {
    this.zoneId = zoneId;
  }

  public String getZoneId() {
    return zoneId;
  }

  public static TimeZoneId getTimeZoneIdByMarket(Market market) {
    if (market == null) {
      return Shanghai;
    }
    switch (market) {
      case US:
        return NewYork;
      case AU:
        return Sydney;
      case NZ:
        return Auckland;
      case UK:
        return London;
      case SG:
        return Singapore;
      case CN:
      case HK:
        return Shanghai;
      default:
        return Shanghai;
    }
  }
}
