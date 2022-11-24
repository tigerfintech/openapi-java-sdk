package com.tigerbrokers.stock.openapi.client.socket;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.socket.data.TradeTick;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.AssetData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.OrderStatusData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.OrderTransactionData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.PositionData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteDepthData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteFutureData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteOptionData;

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
  public void orderStatusChange(OrderStatusData data) {
    // do nothing for protobuf data
  }

  @Override
  public void orderTransactionChange(OrderTransactionData data) {
    // do nothing for protobuf data
  }

  @Override
  public void positionChange(PositionData data) {
    // do nothing for protobuf data
  }

  @Override
  public void assetChange(AssetData data) {
    // do nothing for protobuf data
  }

  @Override
  public void quoteChange(QuoteData data) {
    // do nothing for protobuf data
  }

  @Override
  public void tradeTickChange(TradeTick data) {
    // do nothing for protobuf data
  }

  @Override
  public void optionChange(QuoteOptionData data) {
    // do nothing for protobuf data
  }

  @Override
  public void futureChange(QuoteFutureData data) {
    // do nothing for protobuf data
  }

  @Override
  public void depthQuoteChange(QuoteDepthData data) {
    // do nothing for protobuf data
  }
}
