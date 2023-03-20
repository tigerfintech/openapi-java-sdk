package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.SegFundAvailableItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

public class SegFundAvailableResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<SegFundAvailableItem> segFundAvailableItems;

  public List<SegFundAvailableItem> getSegFundAvailableItems() {
    return segFundAvailableItems;
  }

  public void setSegFundAvailableItems(
      List<SegFundAvailableItem> segFundAvailableItems) {
    this.segFundAvailableItems = segFundAvailableItems;
  }

  @Override
  public String toString() {
    return "SegFundAvailableResponse{" +
        "items='" + JSON.toJSONString(segFundAvailableItems, SerializerFeature.WriteEnumUsingToString) + '\'' +
        '}';
  }
}
