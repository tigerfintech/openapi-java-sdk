package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by bean on 2023/06/08.
 */
public enum StockRankingIndicator {

  ChangeRate("changeRate"),
  ChangeRate5Min("changeRate5Min"),
  TurnoverRate("turnoverRate"),
  TradeAmount("amount"),
  TradeVolume("volume"),
  Amplitude("amplitude"),
  ;

  private final String value;

  StockRankingIndicator(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static StockRankingIndicator getIndicatorByValue(String value) {
    for (StockRankingIndicator indicator : StockRankingIndicator.values()) {
      if (indicator.getValue().equals(value)) {
        return indicator;
      }
    }
    return null;
  }
}
