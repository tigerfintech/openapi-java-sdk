package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by lijiawen on 2018/07/16.
 */
public enum QuoteCode implements CodeEnumType {
  SUCCESS(0, "success"),

  UNKOWN_ERRO(10000, "unknown error"),

  PARAM_ERROR(10001, "input parameter error");

  private int code;

  private String desc;

  QuoteCode(int code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  @Override
  public int getCode() {
    return code;
  }
}
