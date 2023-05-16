package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.TradeOrder;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

public class SingleOrderResponse extends TigerResponse {

  @JSONField(name = "data")
  private TradeOrder item;

  public TradeOrder getItem() {
    return item;
  }

  public void setItem(TradeOrder item) {
    this.item = item;
  }

  @Override
  public String toString() {
    return "SingleOrderResponse{" +
        "item='" + JSON.toJSONString(item, SerializerFeature.WriteEnumUsingToString) + '\'' +
        '}';
  }
}
