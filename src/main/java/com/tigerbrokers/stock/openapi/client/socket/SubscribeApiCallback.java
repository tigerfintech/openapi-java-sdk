package com.tigerbrokers.stock.openapi.client.socket;

import com.tigerbrokers.stock.openapi.client.socket.data.TradeTick;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.AssetData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.BarData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.OptionTopData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.OrderStatusData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.OrderTransactionData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.PositionData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteBBOData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteBasicData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteDepthData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.StockTopData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.TickData;
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

  void tradeTickChange(TradeTick data);
  void fullTickChange(TickData data);

  void quoteChange(QuoteBasicData data);
  void quoteAskBidChange(QuoteBBOData data);

  void optionChange(QuoteBasicData data);
  void optionAskBidChange(QuoteBBOData data);

  void futureChange(QuoteBasicData data);
  void futureAskBidChange(QuoteBBOData data);

  void depthQuoteChange(QuoteDepthData data);

  void klineChange(BarData data);

  void stockTopPush(StockTopData data);

  void optionTopPush(OptionTopData data);

  void subscribeEnd(int id, String subject, String result);

  void cancelSubscribeEnd(int id, String subject, String result);

  void getSubscribedSymbolEnd(SubscribedSymbol subscribedSymbol);
}
