package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by bean on 2023/06/08.
 */
public enum StockTopTarget {

  ChangeRate("changeRate"),
  ChangeRate5Min("changeRate5Min"),
  TurnoverRate("turnoverRate"),
  TradeAmount("amount"),
  TradeVolume("volume"),
  Amplitude("amplitude"),
  ;

  private final String value;

  StockTopTarget(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static StockTopTarget getTargetByValue(String value) {
    for (StockTopTarget target : StockTopTarget.values()) {
      if (target.getValue().equals(value)) {
        return target;
      }
    }
    return null;
  }
}
