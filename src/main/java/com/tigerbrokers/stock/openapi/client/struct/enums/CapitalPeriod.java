package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by bean on 2022/11/25.
 */
public enum CapitalPeriod {

  intraday("intraday"), day("day"), week("week"), month("month"),
  year("year"), quarter("quarter"), halfayear("6month");

  private String value;

  CapitalPeriod(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
