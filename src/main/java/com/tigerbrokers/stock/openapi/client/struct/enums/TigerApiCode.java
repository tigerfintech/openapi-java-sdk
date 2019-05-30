package com.tigerbrokers.stock.openapi.client.struct.enums;

public enum TigerApiCode implements CodeEnumType {
  SUCCESS(0, "success"),
  SERVER_ERROR(1, "server error"),
  READ_TIME_OUT(2, "network read time out"),
  CLIENT_API_ERROR(3,"client error"),
  ACCESS_FORBIDDEN(4, "access forbidden"),
  RATE_LIMIT_ERROR(5, "rate limit error"),

  COMMON_PARAM_ERROR(1000, "common param error"),
  BIZ_PARAM_ERROR(1010, "biz param error"),

  TRADE_RESPONSE_ERROR(1100, "global account response error"),
  STANDARD_RESPONSE_ERROR(1200, "standard account response error"),
  PAPER_RESPONSE_ERROR(1300, "paper account response error"),

  FINANCIAL_RESPONSE_ERROR(2000, "financial response error"),
  STOCK_RESPONSE_ERROR(2100, "stock response error"),
  OPTION_RESPONSE_ERROR(2200, "option response error"),
  FUTURES_RESPONSE_ERROR(2300, "futures response error"),
  USER_RESPONSE_ERROR(2400, "user token error"),

  SUBSCRIBE_ERROR(3000, "subscribe error"),
  SUBSCRIBE_SYMBOL_ERROR(3001, "subscribe symbol error"),
  SUBSCRIBE_SYMBOL_LIMIT_ERROR(3002, "subscribe symbol limit error"),
  SUBSCRIBE_SUBJECT_ERROR(3003, "subscribe subject error"),
  SUBSCRIBE_ID_ERROR(3004, "subscribe tiger id error"),
  SUBSCRIBE_OTHER_SUBJECT_ERROR(3005, "register subject (except quote) error"),
  UNSUBSCRIBE_ERROR(3006, "unsubscribe error"),
  UNKNOWN_STOMP_COMMAND(3007, "unknown stomp command"),

  SIGN_CHECK_FAILED(40013, "check sign and data fail");

  int code;
  String message;

  TigerApiCode(int code, String message) {
    this.code = code;
    this.message = message;
  }

  @Override
  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
