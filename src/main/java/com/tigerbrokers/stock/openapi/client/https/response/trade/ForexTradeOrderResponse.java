package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.TradeOrder;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

public class ForexTradeOrderResponse extends TigerResponse {

  @JSONField(name = "data")
  private TradeOrder tradeOrder;

  public TradeOrder getTradeOrder() {
    return tradeOrder;
  }

  public void setTradeOrder(TradeOrder tradeOrder) {
    this.tradeOrder = tradeOrder;
  }

  @Override
  public String toString() {
    return "ForexTradeOrderResponse{" +
        "tradeOrder='" + JSON.toJSONString(tradeOrder, SerializerFeature.WriteEnumUsingToString) + '\'' +
        '}';
  }
}
