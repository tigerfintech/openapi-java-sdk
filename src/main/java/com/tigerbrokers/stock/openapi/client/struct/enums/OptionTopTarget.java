package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by bean on 2023/06/08.
 */
public enum OptionTopTarget {

  BigOrder("bigOrder"),
  Volumn("volume"),
  Amount("amount"),
  OpenInt("openInt"),
  ;

  private final String value;

  OptionTopTarget(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static OptionTopTarget getTargetByValue(String value) {
    for (OptionTopTarget target : OptionTopTarget.values()) {
      if (target.getValue().equals(value)) {
        return target;
      }
    }
    return null;
  }
}
