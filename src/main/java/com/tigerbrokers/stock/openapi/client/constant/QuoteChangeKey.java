package com.tigerbrokers.stock.openapi.client.constant;

/**
 * Description:
 * Created by lijiawen on 2018/08/23.
 */
public interface QuoteChangeKey {

  String type = "type";
  String symbol = "symbol";
  String timestamp = "timestamp";

  String latestPrice = "latestPrice";
  String latestTime = "latestTime";
  String preClose = "preClose";
  String volume = "volume";

  String open = "open";
  String high = "high";
  String low = "low";
  String close = "close";
  String marketStatus = "marketStatus";

  String hourTradingTag = "hourTradingTag";
  String hourTradingLatestPrice = "hourTradingLatestPrice";
  String hourTradingLatestTime = "hourTradingLatestTime";
  String hourTradingPreClose = "hourTradingPreClose";

  String askPrice = "askPrice";
  String askSize = "askSize";
  String bidPrice = "bidPrice";
  String bidSize = "bidSize";

  String minute = "mi";
  String mPrice = "p";
  String mAvgPrice = "a";
  String mTimestamp = "t";
  String mVolume = "v";
}
