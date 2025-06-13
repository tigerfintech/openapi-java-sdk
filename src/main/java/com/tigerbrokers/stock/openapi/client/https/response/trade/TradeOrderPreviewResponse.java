package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.TradeOrderPreviewItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

public class TradeOrderPreviewResponse extends TigerResponse {

  @JSONField(name = "data")
  private TradeOrderPreviewItem item;

  public TradeOrderPreviewItem getItem() {
    return item;
  }

  public void setItem(TradeOrderPreviewItem item) {
    this.item = item;
  }

  @Override
  public String toString() {
    return "TradeOrderPreviewResponse{" +
        "item='" + JSON.toJSONString(item, SerializerFeature.WriteEnumUsingToString) + '\'' +
        '}';
  }
}
