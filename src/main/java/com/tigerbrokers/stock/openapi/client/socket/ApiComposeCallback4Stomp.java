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

/**
 * Description:
 * Created by lijiawen on 2018/05/16.
 */
public abstract class ApiComposeCallback4Stomp implements ApiComposeCallback {

  public abstract void orderStatusChange(JSONObject jsonObject);

  public abstract void orderTransactionChange(JSONObject jsonObject);

  public abstract void positionChange(JSONObject jsonObject);

  public abstract void assetChange(JSONObject jsonObject);

  public abstract void quoteChange(JSONObject jsonObject);

  public abstract void tradeTickChange(JSONObject jsonObject);

  public abstract void optionChange(JSONObject jsonObject);

  public abstract void futureChange(JSONObject jsonObject);

  public abstract void depthQuoteChange(JSONObject jsonObject);

  @Override
  public void orderStatusChange(int id, OrderStatusData data) {
    // do nothing for protobuf data
  }

  @Override
  public void orderTransactionChange(int id, OrderTransactionData data) {
    // do nothing for protobuf data
  }

  @Override
  public void positionChange(int id, PositionData data) {
    // do nothing for protobuf data
  }

  @Override
  public void assetChange(int id, AssetData data) {
    // do nothing for protobuf data
  }

  @Override
  public void tradeTickChange(int id, TradeTick data) {
    // do nothing for protobuf data
  }

  @Override
  public void quoteChange(int id, QuoteBasicData data) {
    // do nothing for protobuf data
  }
  @Override
  public void quoteAskBidChange(int id, QuoteBBOData data) {
    // do nothing for protobuf data
  }

  @Override
  public void optionChange(int id, QuoteBasicData data) {
    // do nothing for protobuf data
  }
  @Override
  public void optionAskBidChange(int id, QuoteBBOData data) {
    // do nothing for protobuf data
  }

  @Override
  public void futureChange(int id, QuoteBasicData data) {
    // do nothing for protobuf data
  }
  @Override
  public void futureAskBidChange(int id, QuoteBBOData data) {
    // do nothing for protobuf data
  }

  @Override
  public void depthQuoteChange(int id, QuoteDepthData data) {
    // do nothing for protobuf data
  }
}
