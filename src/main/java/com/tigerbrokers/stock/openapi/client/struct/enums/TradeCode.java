package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by lijiawen on 2018/07/16.
 */
public enum TradeCode implements CodeEnumType {

  SUCCESS(0, "trade success"),

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

  TRADE_CHECK_ERROR(40000, "trade check error");

  private int code;
  private String desc;

  TradeCode(int code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  @Override
  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
