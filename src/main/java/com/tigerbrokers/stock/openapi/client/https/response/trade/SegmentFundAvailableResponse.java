package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.SegmentFundAvailableItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

public class SegmentFundAvailableResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<SegmentFundAvailableItem> segmentFundAvailableItems;

  public List<SegmentFundAvailableItem> getSegmentFundAvailableItems() {
    return segmentFundAvailableItems;
  }

  public void setSegmentFundAvailableItems(
      List<SegmentFundAvailableItem> segmentFundAvailableItems) {
    this.segmentFundAvailableItems = segmentFundAvailableItems;
  }

  @Override
  public String toString() {
    return "SegmentFundAvailableResponse{" +
        "items='" + JSON.toJSONString(segmentFundAvailableItems, SerializerFeature.WriteEnumUsingToString) + '\'' +
        '}';
  }
}
