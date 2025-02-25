package com.tigerbrokers.stock.openapi.client.struct.enums;

import com.tigerbrokers.stock.openapi.client.struct.Indicator;

/**
 * Description:
 * Created by bean on 2023/06/08.
 */
public enum StockRankingIndicator implements Indicator {

  ChangeRate("changeRate"),
  ChangeRate5Min("changeRate5Min"),
  TurnoverRate("turnoverRate"),
  Amount("amount"),// trade amount
  Volume("volume"),// trade volume
  Amplitude("amplitude"),
  ;

  private final String value;

  StockRankingIndicator(String value) {
    this.value = value;
  }

  @Override
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
