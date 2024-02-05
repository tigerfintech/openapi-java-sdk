package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created on 2023/02/02.
 */
public enum WarrantState {

  All(0),
  Normal(1),
  TerminateTrades(2),
  WaitingToBeListed(3);

  private Integer value;

  WarrantState(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }
}
