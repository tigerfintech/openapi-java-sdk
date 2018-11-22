package com.tigerbrokers.stock.openapi.client.struct.enums;

public enum TigerApiCode implements CodeEnumType {
  SUCCESS(0, "success"),
  SERVER_ERROR(1, "server error"),
  READ_TIME_OUT(2, "network read time out"),
  CLIENT_API_ERROR(3, "client api error"),
  COMMON_ERROR(10000, "common error"),
  TRADE_ERROR(20001, "interface logic error"),
  TRADE_ACCOUNT_NOT_EXIST(20002, "account not exist in ibkr"),
  TRADE_ORDER_NOT_EXIST(20003, "order is empty or account/orderId cannot find order"),
  TRADE_ORDER_NOT_MATCH(20004, "order not match"),
  TRADE_MARGIN_ERROR(20005, "validate order error，server no response"),
  TRADE_ACCOUNT_NOT_SAME(20006, "account inconsistency"),
  TRADE_ACCOUNT_NOT_LOGIN(20007, "account not login"),
  TRADE_DUPLICATE_ORDER_ID(20008, "duplicate order id"),
  TRADE_NO_TRADER(20009, "no trader"),
  TRADE_ORDER_MARGIN_ERROR(20010, "order margin error"),
  TRADE_ORDER_INACTIVE(20011, "order inactive"),
  TRADE_DAY_TRADE_REMAINING(20012, "validate commission error"),
  TRADE_SYSTEM_NOT_AVAILABLE(20013, "connect trade server error"),
  TRADE_ACCOUNT_NOT_OPENED(20014, "account not open"),
  TRADE_ACCESS_DENIED(20015, "access denied"),
  TRADE_CONTRACT_UNACCEPTABLE(21001, "contract unacceptable"),
  TRADE_SYMBOL_EMPTY(21002, "symbol empty"),
  TRADE_QUANTITY_NON_POSITIVE(21003, "quantity non positive"),
  TRADE_PRICE_INCORRECT(21004, "price incorrect"),
  TRADE_SYMBOL_INCORRECT(21005, "symbol incorrect"),
  TRADE_CONTRACT_INCORRECT(21006, "contract incorrect"),
  TRADE_ACTION_INCORRECT(21007, "action incorrect"),
  TRADE_ORDER_TYPE_INCORRECT(21008, "order type incorrect"),
  JSON_FORMAT_ERROR(30000, "json format error"),
  TRADE_ORDER_INVALID(40000, "trade order invalid"),
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
  SIGN_CHECK_FAILED(40013, "check sign and data fail"),
  IP_NOT_AUTHORIZED(40014, "ip not authorized"),
  ACCESS_FORBIDDEN(40015, "access forbidden"),
  RATE_LIMIT_ERROR(40016, "rate limit error"),
  TOO_MANY_SUB_ACCOUNTS(40017, "too many sub accounts"),
  SUB_ACCOUNT_NON_AUTHORIZED(40018, "sub account is not authorized to the master account"),
  ACCOUNT_TYPE_NOT_SUPPORT(40019, "account master type not support"),
  SUBSCRIBE_ID_ERROR(40020, "subscribe tiger id error"),
  SUBSCRIBE_SYMBOL_ERROR(40021, "subscribe symbol error"),
  SUBSCRIBE_SYMBOL_LIMIT_ERROR(40022, "subscribe symbol limit error"),
  SUBSCRIBE_SUBJECT_ERROR(40023, "subscribe subject error"),
  UNSUBSCRIBE_ERROR(40024, "unsubscribe error"),
  STANDARD_RESPONSE_ERROR(40025, "standard account response error");

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
