package com.tigerbrokers.stock.openapi.client.constant;

/**
 * Description:
 * Created by lijiawen on 2018/05/23.
 */
public interface RspProtocolType {

  String RET_HEADER = "ret-type";

  int END_CONN = -1;

  /**
   * 交易
   */
  int GET_ORDER_NO_END = 1;
  int PREVIEW_ORDER_END = 2;
  int PLACE_ORDER_END = 3;
  int CANCEL_ORDER_END = 4;
  int MODIFY_ORDER_END = 5;
  int GET_ASSET_END = 6;
  int GET_POSITION_END = 7;
  int GET_ACCOUNT_END = 8;
  int SUBSCRIBE_ORDER_STATUS = 9;
  int SUBSCRIBE_POSITION = 10;
  int SUBSCRIBE_ASSET = 11;

  /**
   * 行情
   */
  int GET_MARKET_STATE_END = 101;
  int GET_ALL_SYMBOLS_END = 102;
  int GET_ALL_SYMBOL_NAMES_END = 103;
  int GET_BRIEF_INFO_END = 104;
  int GET_STOCK_DETAIL_END = 105;
  int GET_TIME_LINE_END = 106;
  int GET_HOUR_TRADING_TIME_LINE_END = 107;
  int GET_KLINE_END = 108;
  int GET_TRADING_TICK_END = 109;
  int GET_QUOTE_CHANGE_END = 110;
  int GET_SUB_SYMBOLS_END = 111;
  int GET_SUBSCRIBE_END = 112;
  int GET_CANCEL_SUBSCRIBE_END = 113;
  int GET_OPTION_CHANGE_END = 114;
}
