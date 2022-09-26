package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by liutongping on 2022/03/08.
 */
public enum Env {
  PROD("openapi"),
  SANDBOX("openapi-sandbox"),
  TEST("openapi-test");

  private String configFieldName;
  Env(String configFieldName) {
    this.configFieldName = configFieldName;
  }

  public String getConfigFieldName() {
    return configFieldName;
  }
}
