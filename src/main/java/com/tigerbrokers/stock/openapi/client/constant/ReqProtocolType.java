package com.tigerbrokers.stock.openapi.client.constant;

/**
 * Description:
 * Created by lijiawen on 2018/05/22.
 */
public interface ReqProtocolType {

  String REQ_HEADER = "req-type";

  /**
   * trading
   */
  int ORDER_NO = 1;
  int PREVIEW_ORDER = 2;
  int PLACE_ORDER = 3;
  int CANCEL_ORDER = 4;
  int MODIFY_ORDER = 5;
  int REQ_OPEN_ORDERS = 6;
  int REQ_ASSETS = 7;
  int REQ_POSITIONS = 8;
  int REQ_ACCOUNT = 9;

  /**
   * quote
   */
  int REQ_MARKET_STATE = 101;
  int REQ_ALL_SYMBOLS = 102;
  int REQ_ALL_SYMBOL_NAMES = 103;
  int REQ_BRIEF_INFO = 104;
  int REQ_STOCK_DETAIL = 105;
  int REQ_TIME_LINE = 106;
  int REQ_HOUR_TRADING_TIME_LINE = 107;
  int REQ_KLINE = 108;
  int REQ_TRADE_TICK = 109;
  int REQ_SUB_SYMBOLS = 110;
  int REQ_SUB_OPTION = 110;
}
