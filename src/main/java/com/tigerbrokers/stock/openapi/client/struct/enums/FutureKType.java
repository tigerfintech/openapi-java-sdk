package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public enum FutureKType {
  min1("min"), min3("3min"), min5("5min"), min10("10min"),
  min15("15min"), min30("30min"), min45("45min"), min60("60min"),
  hour2("2hour"), hour3("3hour"), hour4("4hour"), hour6("6hour"),
  day("day"), week("week"), month("month");

  private String desc;

  FutureKType(String desc) {
    this.desc = desc;
  }
}
