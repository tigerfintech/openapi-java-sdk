package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * 冰山单价格类型
 */
public enum PriceType {
  LIMIT_PRICE("LIMIT_PRICE", "限价"),
  ASK_PRICE("ASK_PRICE", "卖一价"),
  BID_PRICE("BID_PRICE", "买一价"),
  LATEST_PRICE("LATEST_PRICE", "最新价"),
  ;

  private final String value;
  private final String desc;

  PriceType(String value, String desc) {
    this.value = value;
    this.desc = desc;
  }

  public String getValue() {
    return value;
  }

  public String getDesc() {
    return desc;
  }
}
