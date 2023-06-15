package com.tigerbrokers.stock.openapi.client.struct.enums;

import com.tigerbrokers.stock.openapi.client.struct.Indicator;

/**
 * Description:
 * Created by bean on 2023/06/08.
 */
public enum OptionRankingIndicator implements Indicator {

  BigOrder("bigOrder"),
  Volume("volume"),
  Amount("amount"),
  OpenInt("openInt"),
  ;

  private final String value;

  OptionRankingIndicator(String value) {
    this.value = value;
  }

  @Override
  public String getValue() {
    return value;
  }

  public static OptionRankingIndicator getIndicatorByValue(String value) {
    for (OptionRankingIndicator indicator : OptionRankingIndicator.values()) {
      if (indicator.getValue().equals(value)) {
        return indicator;
      }
    }
    return null;
  }
}
