package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.BatchOrderItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

public class BatchOrderResponse extends TigerResponse {

  @JSONField(name = "data")
  private BatchOrderItem item;

  public BatchOrderItem getItem() {
    return item;
  }

  public void setItem(BatchOrderItem item) {
    this.item = item;
  }

  @Override
  public String toString() {
    return "BatchOrderResponse{" +
        "item='" + JSON.toJSONString(item, SerializerFeature.WriteEnumUsingToString) + '\'' +
        '}';
  }
}
