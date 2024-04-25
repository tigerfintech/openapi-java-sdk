package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by lijiawen on 2018/06/06.
 */
public enum KType {

  day("day"), week("week"), month("month"), year("year"), min1("1min"), min3("3min"),
  min5("5min"), min15("15min"), min30("30min"), min60("60min");

  private String value;

  KType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
