package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.TradeOrderItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

public class TradeOrderResponse extends TigerResponse {

  @JSONField(name = "data")
  private TradeOrderItem item;

  @Deprecated
  public String getData() {
    return item == null ? null : JSON.toJSONString(item);
  }

  public TradeOrderItem getItem() {
    return item;
  }

  public void setItem(TradeOrderItem item) {
    this.item = item;
  }

  @Override
  public String toString() {
    return "TradeOrderResponse{" +
        "item='" + JSON.toJSONString(item) + '\'' +
        '}';
  }
}
