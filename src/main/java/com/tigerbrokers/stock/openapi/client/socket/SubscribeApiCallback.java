package com.tigerbrokers.stock.openapi.client.socket;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.socket.data.TradeTick;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.AssetData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.OrderStatusData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.OrderTransactionData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.PositionData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteBBOData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteBasicData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteDepthData;
import com.tigerbrokers.stock.openapi.client.struct.SubscribedSymbol;

/**
 * Description:
 * Created by lijiawen on 2018/08/29.
 */
public interface SubscribeApiCallback {

  void orderStatusChange(int id, OrderStatusData data);

  void orderTransactionChange(int id, OrderTransactionData data);

  void positionChange(int id, PositionData data);

  void assetChange(int id, AssetData data);

  void tradeTickChange(int id, TradeTick data);

  void quoteChange(int id, QuoteBasicData data);
  void quoteAskBidChange(int id, QuoteBBOData data);

  void optionChange(int id, QuoteBasicData data);
  void optionAskBidChange(int id, QuoteBBOData data);

  void futureChange(int id, QuoteBasicData data);
  void futureAskBidChange(int id, QuoteBBOData data);

  void depthQuoteChange(int id, QuoteDepthData data);

  void subscribeEnd(int id, String subject, String result);

  void cancelSubscribeEnd(int id, String subject, String result);

  void getSubscribedSymbolEnd(SubscribedSymbol subscribedSymbol);
}
