package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.PositionsItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

public class PositionsResponse extends TigerResponse {

  @JSONField(name = "data")
  private PositionsItem item;

  public PositionsItem getItem() {
    return item;
  }

  public void setItem(PositionsItem item) {
    this.item = item;
  }

  @Override
  public String toString() {
    return "PositionsResponse{" +
        "item='" + JSON.toJSONString(item, SerializerFeature.WriteEnumUsingToString) + '\'' +
        '}';
  }
}
