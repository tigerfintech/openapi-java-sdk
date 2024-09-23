package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created on 2023/02/02.
 */
public enum HaltedStatus {

  Normal(0),
  Suspension(3),
  Delisted(4);

  private Integer value;

  HaltedStatus(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }

  public static HaltedStatus getHaltedStatusByValue(Integer value) {
    for (HaltedStatus status : HaltedStatus.values()) {
      if (status.value.equals(value)) {
        return status;
      }
    }
    return null;
  }
}
