package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created on 2023/02/02.
 */
public enum OptionPrice {

  ITM(1),// in the money(include at the money)
  OTM(-1);// out of the money

  private Integer value;

  OptionPrice(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }
}
