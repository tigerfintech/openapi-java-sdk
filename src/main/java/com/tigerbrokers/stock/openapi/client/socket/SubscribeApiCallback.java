package com.tigerbrokers.stock.openapi.client.socket;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.socket.data.TradeTick;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.AssetData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.OrderStatusData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.OrderTransactionData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.PositionData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteDepthData;
import com.tigerbrokers.stock.openapi.client.struct.SubscribedSymbol;

/**
 * Description:
 * Created by lijiawen on 2018/08/29.
 */
public interface SubscribeApiCallback {

  void orderStatusChange(OrderStatusData data);

  void orderTransactionChange(OrderTransactionData data);

  void positionChange(PositionData data);

  void assetChange(AssetData data);

  void quoteChange(QuoteData data);

  void tradeTickChange(TradeTick data);

  void optionChange(QuoteData data);

  void futureChange(QuoteData data);

  void depthQuoteChange(QuoteDepthData data);

  void subscribeEnd(String id, String subject, JSONObject jsonObject);

  void cancelSubscribeEnd(String id, String subject, JSONObject jsonObject);

  void getSubscribedSymbolEnd(SubscribedSymbol subscribedSymbol);
}
