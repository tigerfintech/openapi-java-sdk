package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by lijiawen on 2018/07/16.
 */
public enum QuoteCode implements CodeEnumType {
  SUCCESS(0, "成功"),

  UNKOWN_ERRO(10000, "未知错误"),

  PARAM_ERROR(10001, "输入参数错误");

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
