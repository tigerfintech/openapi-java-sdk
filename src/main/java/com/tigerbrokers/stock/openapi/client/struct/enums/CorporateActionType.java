package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by lijiawen on 2019/02/28.
 */
public enum CorporateActionType {
  SPLIT("split"),
  DIVIDEND("dividend"),
  EARNING("earning");

  private String value;

  CorporateActionType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
