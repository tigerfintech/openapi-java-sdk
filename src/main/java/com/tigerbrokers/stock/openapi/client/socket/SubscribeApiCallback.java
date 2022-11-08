package com.tigerbrokers.stock.openapi.client.socket;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteDepthData;
import com.tigerbrokers.stock.openapi.client.struct.SubscribedSymbol;

/**
 * Description:
 * Created by lijiawen on 2018/08/29.
 */
public interface SubscribeApiCallback {

  void orderStatusChange(JSONObject jsonObject);

  void positionChange(JSONObject jsonObject);

  void assetChange(JSONObject jsonObject);

  void quoteChange(QuoteData data);

  void tradeTickChange(JSONObject jsonObject);

  void optionChange(JSONObject jsonObject);

  void futureChange(JSONObject jsonObject);

  void depthQuoteChange(QuoteDepthData data);

  void subscribeEnd(String id, String subject, JSONObject jsonObject);

  void cancelSubscribeEnd(String id, String subject, JSONObject jsonObject);

  void getSubscribedSymbolEnd(SubscribedSymbol subscribedSymbol);
}
