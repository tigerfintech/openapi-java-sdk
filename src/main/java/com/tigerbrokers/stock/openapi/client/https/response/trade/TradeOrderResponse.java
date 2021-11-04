package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.TradeOrderItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import lombok.ToString;

@ToString
public class TradeOrderResponse extends TigerResponse {

  @JSONField(name = "data")
  private String data;

  private TradeOrderItem item;

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public TradeOrderItem getItem() {
    if (item == null && data != null) {
      item = JSON.parseObject(data, TradeOrderItem.class);
    }
    return item;
  }

  public void setItem(TradeOrderItem item) {
    this.item = item;
  }
}
