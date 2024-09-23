package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public enum FutureKType {
  min1("min"), min2("2min"), min3("3min"), min5("5min"), min10("10min"),
  min15("15min"), min30("30min"), min45("45min"), min60("60min"),
  hour2("2hour"), hour3("3hour"), hour4("4hour"), hour6("6hour"),
  day("day"), week("week"), month("month");

  private String value;

  FutureKType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
