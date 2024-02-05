package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created on 2023/02/02.
 */
public enum WarrantType {

  All(0),
  Call(1),
  Put(2),
  Bull(3),
  Bear(4);

  private Integer value;

  WarrantType(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }
}
