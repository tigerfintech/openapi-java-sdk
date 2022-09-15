package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by bean on 2022/09/06.
 */
public enum MethodName {
  /**
   * trade
   */
  ORDER_NO("order_no", 1),
  PLACE_ORDER("place_order", 1),
  BATCH_PLACE_ORDER("batch_place_order", 1),
  CANCEL_ORDER("cancel_order", 1),
  MODIFY_ORDER("modify_order", 1),
  PREVIEW_ORDER("preview_order", 1),

  /**
   * account/asset
   */
  ACCOUNTS("accounts", 1),
  ASSETS("assets", 1),
  PRIME_ASSETS("prime_assets", 1),
  ANALYTICS_ASSET("analytics_asset", 1),
  POSITIONS("positions", 1),
  ORDERS("orders", 1),
  ACTIVE_ORDERS("active_orders", 1),
  INACTIVE_ORDERS("inactive_orders", 1),
  FILLED_ORDERS("filled_orders", 1),
  ORDER_TRANSACTIONS("order_transactions", 1),

  /**
   * contract
   */
  CONTRACT("contract", 1),
  CONTRACTS("contracts", 1),

  /**
   * quote
   */
  MARKET_STATE("market_state", 2),
  ALL_SYMBOLS("all_symbols", 2),
  ALL_SYMBOL_NAMES("all_symbol_names", 2),
  BRIEF("brief", 2),
  STOCK_DETAIL("stock_detail", 2),
  HOUR_TRADING_TIMELINE("hour_trading_timeline", 2),

  TIMELINE("timeline", 2),
  HISTORY_TIMELINE("history_timeline", 2),
  KLINE("kline", 2),
  TRADE_TICK("trade_tick", 2),
  QUOTE_CONTRACT("quote_contract", 2),
  QUOTE_REAL_TIME("quote_real_time", 2),
  QUOTE_SHORTABLE_STOCKS("quote_shortable_stocks", 2),
  QUOTE_STOCK_TRADE("quote_stock_trade", 2),
  QUOTE_DEPTH("quote_depth", 2),
  QUOTE_DELAY("quote_delay", 2),
  /** trading calendar */
  TRADING_CALENDAR("trading_calendar", 2),

  /**
   * option quote
   */
  OPTION_EXPIRATION("option_expiration", 2),
  OPTION_CHAIN("option_chain", 2),
  OPTION_BRIEF("option_brief", 2),
  OPTION_KLINE("option_kline", 2),
  OPTION_TRADE_TICK("option_trade_tick", 2),

  /**
   * future quote
   */
  FUTURE_EXCHANGE("future_exchange", 2),
  FUTURE_CONTRACT_BY_CONTRACT_CODE("future_contract_by_contract_code", 2),
  FUTURE_CONTRACT_BY_EXCHANGE_CODE("future_contract_by_exchange_code", 2),
  FUTURE_CONTINUOUS_CONTRACTS("future_continuous_contracts", 2),
  FUTURE_CURRENT_CONTRACT("future_current_contract", 2),
  FUTURE_CONTRACTS("future_contracts", 2),
  FUTURE_KLINE("future_kline", 2),
  FUTURE_REAL_TIME_QUOTE("future_real_time_quote", 2),
  FUTURE_TICK("future_tick", 2),
  FUTURE_TRADING_DATE("future_trading_date", 2),

  /**
   * fundmental data
   */
  FINANCIAL_DAILY("financial_daily", 2),
  FINANCIAL_REPORT("financial_report", 2),
  CORPORATE_ACTION("corporate_action", 2),
  INDUSTRY_LIST("industry_list", 2),
  INDUSTRY_STOCKS("industry_stocks", 2),
  STOCK_INDUSTRY("stock_industry", 2),

  /**
   * grab quote
   */
  GRAB_QUOTE_PERMISSION("grab_quote_permission", 2),
  GET_QUOTE_PERMISSION("get_quote_permission", 2),

  /**
   * user
   */
  USER_LOGIN("user_login", 0),
  USER_TRADE_TOKEN("user_trade_token", 0),
  USER_TRADE_PASSWORD_VERIFY("user_trade_password_verify", 0),
  USER_TRADE_PASSWORD_RESET("user_trade_password_reset", 0),
  ;

  private String value;
  /** method type，0:login; 1：trade; 2:quote */
  private int type;

  MethodName(String value, int type) {
    this.value = value;
    this.type = type;
  }

  public String getValue() {
    return value;
  }

  /**
   * get method type. 0:login; 1：trade; 2:quote
   */
  public int getType() {
    return type;
  }

  public static MethodName getMethodNameByValue(String value) {
    for (MethodName methodName : MethodName.values()) {
      if (methodName.getValue().equals(value)) {
        return methodName;
      }
    }
    throw new RuntimeException("api method not supported: " + value);
  }
}
