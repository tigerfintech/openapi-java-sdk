package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.SegmentFundItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

public class SegmentFundResponse extends TigerResponse {

  @JSONField(name = "data")
  private SegmentFundItem segmentFundItem;

  public SegmentFundItem getSegmentFundItem() {
    return segmentFundItem;
  }

  public void setSegmentFundItem(SegmentFundItem segmentFundItem) {
    this.segmentFundItem = segmentFundItem;
  }

  @Override
  public String toString() {
    return "SegmentFundResponse{" +
        "item='" + JSON.toJSONString(segmentFundItem, SerializerFeature.WriteEnumUsingToString) + '\'' +
        '}';
  }
}
