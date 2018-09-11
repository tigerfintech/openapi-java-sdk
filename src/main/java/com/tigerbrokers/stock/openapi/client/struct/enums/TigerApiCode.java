package com.tigerbrokers.stock.openapi.client.struct.enums;

public enum TigerApiCode implements CodeEnumType {
  SUCCESS(0, "success"),
  SERVER_ERROR(1, "server error"),
  READ_TIME_OUT(2, "network read time out"),
  CLIENT_API_ERROR(3, "client api error"),
  JSON_FORMAT_ERROR(40000, "json format error"),
  POST_PARAM_EMPTY(40001, "post param is empty"),
  TIGER_ID_ERROR(40002, "tiger id error"),
  TIGER_ID_NOT_REGISTER(40003, "tiger id not register"),
  METHOD_NAME_ERROR(40004, "method name error"),
  METHOD_NOT_SUPPORTED(40005, "method is not supported"),
  TIMESTAMP_ERROR(40006, "timestamp error"),
  CHARSET_ERROR(40007, "charset error"),
  VERSION_NOT_SUPPORTED(40008, "api version is not supported"),
  SIGN_TYPE_NOT_SUPPORTED(40009, "sign type is not supported"),
  BIZ_PARAM_ERROR(40010, "biz param error"),
  ACCOUNT_EMPTY(40011, "account is empty"),
  ACCOUNT_NON_AUTHORIZED(40012, "account is not authorized to the api user"),
  SIGN_CHECK_FAILED(40013, "invalid signature"),
  IP_NOT_AUTHORIZED(40014, "ip not authorized"),
  ACCESS_FORBIDDEN(40015, "access forbidden"),
  RATE_LIMIT_ERROR(40016, "beyond the app call frequency limit"),
  TOO_MANY_SUB_ACCOUNTS(40017, "too many sub accounts"),
  SUB_ACCOUNT_NON_AUTHORIZED(40018, "sub account is not authorized to the master account"),
  ACCOUNT_TYPE_NOT_SUPPORT(40019, "account master type not support"),
  SUBSCRIBE_ID_ERROR(40020, "subscribe tiger id error"),
  SUBSCRIBE_SYMBOL_ERROR(40021, "subscribe symbol error"),
  SUBSCRIBE_SYMBOL_LIMIT_ERROR(40022, "subscribe symbol limit error"),
  SUBSCRIBE_SUBJECT_ERROR(40023, "subscribe subject error"),
  UNSUBSCRIBE_ERROR(40024, "unsubscribe error");

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
