package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description: Option analysis period enum
 */
public enum OptionAnalysisPeriod {

  /**
   * 3 year period
   */
  THREE_YEAR("3year"),

  /**
   * 52 week (1 year) period
   */
  FIFTY_TWO_WEEK("52week"),

  /**
   * 26 week (6 month) period
   */
  TWENTY_SIX_WEEK("26week"),

  /**
   * 13 week (3 month) period
   */
  THIRTEEN_WEEK("13week");

  private String value;

  OptionAnalysisPeriod(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
