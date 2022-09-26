package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by bean on 2022/09/06.
 */
public enum MethodName {
  /**
   * trade
   */
  ORDER_NO("order_no", MethodType.TRADE),
  PLACE_ORDER("place_order", MethodType.TRADE),
  BATCH_PLACE_ORDER("batch_place_order", MethodType.TRADE),
  CANCEL_ORDER("cancel_order", MethodType.TRADE),
  MODIFY_ORDER("modify_order", MethodType.TRADE),
  PREVIEW_ORDER("preview_order", MethodType.TRADE),

  /**
   * account/asset
   */
  ACCOUNTS("accounts", MethodType.TRADE),
  ASSETS("assets", MethodType.TRADE),
  PRIME_ASSETS("prime_assets", MethodType.TRADE),
  ANALYTICS_ASSET("analytics_asset", MethodType.TRADE),
  POSITIONS("positions", MethodType.TRADE),
  ORDERS("orders", MethodType.TRADE),
  ACTIVE_ORDERS("active_orders", MethodType.TRADE),
  INACTIVE_ORDERS("inactive_orders", MethodType.TRADE),
  FILLED_ORDERS("filled_orders", MethodType.TRADE),
  ORDER_TRANSACTIONS("order_transactions", MethodType.TRADE),

  /**
   * contract
   */
  CONTRACT("contract", MethodType.TRADE),
  CONTRACTS("contracts", MethodType.TRADE),

  /**
   * quote
   */
  MARKET_STATE("market_state", MethodType.QUOTE),
  ALL_SYMBOLS("all_symbols", MethodType.QUOTE),
  ALL_SYMBOL_NAMES("all_symbol_names", MethodType.QUOTE),
  BRIEF("brief", MethodType.QUOTE),
  STOCK_DETAIL("stock_detail", MethodType.QUOTE),
  HOUR_TRADING_TIMELINE("hour_trading_timeline", MethodType.QUOTE),

  TIMELINE("timeline", MethodType.QUOTE),
  HISTORY_TIMELINE("history_timeline", MethodType.QUOTE),
  KLINE("kline", MethodType.QUOTE),
  TRADE_TICK("trade_tick", MethodType.QUOTE),
  QUOTE_CONTRACT("quote_contract", MethodType.QUOTE),
  QUOTE_REAL_TIME("quote_real_time", MethodType.QUOTE),
  QUOTE_SHORTABLE_STOCKS("quote_shortable_stocks", MethodType.QUOTE),
  QUOTE_STOCK_TRADE("quote_stock_trade", MethodType.QUOTE),
  QUOTE_DEPTH("quote_depth", MethodType.QUOTE),
  QUOTE_DELAY("quote_delay", MethodType.QUOTE),
  /** trading calendar */
  TRADING_CALENDAR("trading_calendar", MethodType.QUOTE),

  /**
   * option quote
   */
  OPTION_EXPIRATION("option_expiration", MethodType.QUOTE),
  OPTION_CHAIN("option_chain", MethodType.QUOTE),
  OPTION_BRIEF("option_brief", MethodType.QUOTE),
  OPTION_KLINE("option_kline", MethodType.QUOTE),
  OPTION_TRADE_TICK("option_trade_tick", MethodType.QUOTE),

  /**
   * future quote
   */
  FUTURE_EXCHANGE("future_exchange", MethodType.QUOTE),
  FUTURE_CONTRACT_BY_CONTRACT_CODE("future_contract_by_contract_code", MethodType.QUOTE),
  FUTURE_CONTRACT_BY_EXCHANGE_CODE("future_contract_by_exchange_code", MethodType.QUOTE),
  FUTURE_CONTINUOUS_CONTRACTS("future_continuous_contracts", MethodType.QUOTE),
  FUTURE_CURRENT_CONTRACT("future_current_contract", MethodType.QUOTE),
  FUTURE_CONTRACTS("future_contracts", MethodType.QUOTE),
  FUTURE_KLINE("future_kline", MethodType.QUOTE),
  FUTURE_REAL_TIME_QUOTE("future_real_time_quote", MethodType.QUOTE),
  FUTURE_TICK("future_tick", MethodType.QUOTE),
  FUTURE_TRADING_DATE("future_trading_date", MethodType.QUOTE),

  /**
   * fundmental data
   */
  FINANCIAL_DAILY("financial_daily", MethodType.QUOTE),
  FINANCIAL_REPORT("financial_report", MethodType.QUOTE),
  CORPORATE_ACTION("corporate_action", MethodType.QUOTE),
  INDUSTRY_LIST("industry_list", MethodType.QUOTE),
  INDUSTRY_STOCKS("industry_stocks", MethodType.QUOTE),
  STOCK_INDUSTRY("stock_industry", MethodType.QUOTE),

  /**
   * grab quote
   */
  GRAB_QUOTE_PERMISSION("grab_quote_permission", MethodType.QUOTE),
  GET_QUOTE_PERMISSION("get_quote_permission", MethodType.QUOTE),

  /**
   * user
   */
  USER_LOGIN("user_login", MethodType.OTHER),
  USER_TRADE_TOKEN("user_trade_token", MethodType.OTHER),
  USER_TRADE_PASSWORD_VERIFY("user_trade_password_verify", MethodType.OTHER),
  USER_TRADE_PASSWORD_RESET("user_trade_password_reset", MethodType.OTHER),
  USER_LICENSE("user_license", MethodType.OTHER),
  ;

  private String value;
  /** method type，TRADE, QUOTE, OTHER */
  private MethodType type;

  MethodName(String value, MethodType type) {
    this.value = value;
    this.type = type;
  }

  public String getValue() {
    return value;
  }

  /**
   * get method type. TRADE, QUOTE, OTHER
   */
  public MethodType getType() {
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
