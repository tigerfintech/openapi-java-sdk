package com.tigerbrokers.stock.openapi.client.constant;

/**
 * Description: please use enume 'MethodName'
 * Created by lijiawen on 2018/05/31.
 */
@Deprecated
public interface ApiServiceType {

  /**
   * trade
   */
  String ORDER_NO = "order_no";
  String PREVIEW_ORDER = "preview_order";
  String PLACE_ORDER = "place_order";
  String BATCH_PLACE_ORDER = "batch_place_order";
  String CANCEL_ORDER = "cancel_order";
  String MODIFY_ORDER = "modify_order";

  /**
   * quote
   */
  String MARKET_STATE = "market_state";
  String ALL_SYMBOLS = "all_symbols";
  String ALL_SYMBOL_NAMES = "all_symbol_names";
  String BRIEF = "brief";
  String STOCK_DETAIL = "stock_detail";
  String HOUR_TRADING_TIMELINE = "hour_trading_timeline";

  String TIMELINE = "timeline";
  String HISTORY_TIMELINE = "history_timeline";
  String KLINE = "kline";
  String TRADE_TICK = "trade_tick";
  String QUOTE_CONTRACT = "quote_contract";
  String QUOTE_REAL_TIME = "quote_real_time";
  String QUOTE_SHORTABLE_STOCKS = "quote_shortable_stocks";
  String QUOTE_STOCK_TRADE = "quote_stock_trade";
  String QUOTE_DEPTH = "quote_depth";
  String QUOTE_DELAY = "quote_delay";
  /** trading calendar */
  String TRADING_CALENDAR = "trading_calendar";

  /**
   * option quote
   */
  String OPTION_EXPIRATION = "option_expiration";
  String OPTION_CHAIN = "option_chain";
  String OPTION_BRIEF = "option_brief";
  String OPTION_KLINE = "option_kline";
  String OPTION_TRADE_TICK = "option_trade_tick";

  /**
   * future quote
   */
  String FUTURE_EXCHANGE = "future_exchange";
  String FUTURE_CONTRACT_BY_CONTRACT_CODE = "future_contract_by_contract_code";
  String FUTURE_CONTRACT_BY_EXCHANGE_CODE = "future_contract_by_exchange_code";
  String FUTURE_CONTINUOUS_CONTRACTS = "future_continuous_contracts";
  String FUTURE_CURRENT_CONTRACT = "future_current_contract";
  String FUTURE_CONTRACTS = "future_contracts";
  String FUTURE_KLINE = "future_kline";
  String FUTURE_REAL_TIME_QUOTE = "future_real_time_quote";
  String FUTURE_TICK = "future_tick";
  String FUTURE_TRADING_DATE = "future_trading_date";

  /**
   * account/asset
   */
  String ACCOUNTS = "accounts";
  String ASSETS = "assets";
  String PRIME_ASSETS = "prime_assets";
  String ANALYTICS_ASSET = "analytics_asset";
  String POSITIONS = "positions";
  String ORDERS = "orders";
  String ACTIVE_ORDERS = "active_orders";
  String INACTIVE_ORDERS = "inactive_orders";
  String FILLED_ORDERS = "filled_orders";
  String ORDER_TRANSACTIONS = "order_transactions";

  /**
   * contract
   */
  String CONTRACT = "contract";
  String CONTRACTS = "contracts";

  /**
   * fundmental data
   */
  String FINANCIAL_DAILY = "financial_daily";
  String FINANCIAL_REPORT = "financial_report";
  String CORPORATE_ACTION = "corporate_action";
  String INDUSTRY_LIST = "industry_list";
  String INDUSTRY_STOCKS = "industry_stocks";
  String STOCK_INDUSTRY = "stock_industry";

  /**
   * user
   */
  String USER_LOGIN = "user_login";
  String USER_TRADE_TOKEN = "user_trade_token";
  String USER_TRADE_PASSWORD_VERIFY = "user_trade_password_verify";
  String USER_TRADE_PASSWORD_RESET = "user_trade_password_reset";

  /**
   * grab quote
   */
  String GRAB_QUOTE_PERMISSION = "grab_quote_permission";
  String GET_QUOTE_PERMISSION = "get_quote_permission";
}
