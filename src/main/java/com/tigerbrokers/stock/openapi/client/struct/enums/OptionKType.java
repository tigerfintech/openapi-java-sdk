package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by lijiawen on 2023/03/21.
 */
public enum OptionKType {

  day("day"), min1("1min"), min5("5min"),
  min30("30min"), min60("60min");

  private String value;

  OptionKType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
