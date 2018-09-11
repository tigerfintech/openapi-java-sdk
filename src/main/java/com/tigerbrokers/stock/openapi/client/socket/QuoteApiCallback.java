package com.tigerbrokers.stock.openapi.client.socket;

import com.alibaba.fastjson.JSONObject;

/**
 * Description:
 * Created by lijiawen on 2018/06/14.
 */
public interface QuoteApiCallback {

  void getMarketStateEnd(JSONObject jsonObject);

  void getAllSymbolsEnd(JSONObject jsonObject);

  void getAllSymbolNamesEnd(JSONObject jsonObject);

  void getBriefInfoEnd(JSONObject jsonObject);

  void getStockDetailEnd(JSONObject jsonObject);

  void getTimelineEnd(JSONObject jsonObject);

  void getHourTradingTimelineEnd(JSONObject jsonObject);

  void getKlineEnd(JSONObject jsonObject);

  void getTradeTickEnd(JSONObject jsonObject);
}
