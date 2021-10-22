package com.tigerbrokers.stock.openapi.client.socket;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.struct.SubscribedSymbol;

/**
 * Description:
 * Created by lijiawen on 2018/08/29.
 */
public interface SubscribeApiCallback {

  void orderStatusChange(JSONObject jsonObject);

  void positionChange(JSONObject jsonObject);

  void assetChange(JSONObject jsonObject);

  void quoteChange(JSONObject jsonObject);

  void optionChange(JSONObject jsonObject);

  void futureChange(JSONObject jsonObject);

  void depthQuoteChange(JSONObject jsonObject);

  void subscribeEnd(String id, String subject, JSONObject jsonObject);

  void cancelSubscribeEnd(String id, String subject, JSONObject jsonObject);

  void getSubscribedSymbolEnd(SubscribedSymbol subscribedSymbol);
}
