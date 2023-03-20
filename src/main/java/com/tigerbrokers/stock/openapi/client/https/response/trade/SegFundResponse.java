package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.SegFundItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

public class SegFundResponse extends TigerResponse {

  @JSONField(name = "data")
  private SegFundItem segFundItem;

  public SegFundItem getSegFundItem() {
    return segFundItem;
  }

  public void setSegFundItem(SegFundItem segFundItem) {
    this.segFundItem = segFundItem;
  }

  @Override
  public String toString() {
    return "SegFundResponse{" +
        "item='" + JSON.toJSONString(segFundItem, SerializerFeature.WriteEnumUsingToString) + '\'' +
        '}';
  }
}
