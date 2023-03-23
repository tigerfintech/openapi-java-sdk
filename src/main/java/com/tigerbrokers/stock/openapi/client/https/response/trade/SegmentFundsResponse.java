package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.SegmentFundItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

public class SegmentFundsResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<SegmentFundItem> segmentFundItems;

  public List<SegmentFundItem> getSegmentFundItems() {
    return segmentFundItems;
  }

  public void setSegmentFundItems(List<SegmentFundItem> segmentFundItems) {
    this.segmentFundItems = segmentFundItems;
  }

  @Override
  public String toString() {
    return "SegmentFundsResponse{" +
        "items='" + JSON.toJSONString(segmentFundItems, SerializerFeature.WriteEnumUsingToString) + '\'' +
        '}';
  }
}
