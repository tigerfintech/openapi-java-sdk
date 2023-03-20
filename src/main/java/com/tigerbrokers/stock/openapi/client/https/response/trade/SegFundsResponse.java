package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.SegFundItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

public class SegFundsResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<SegFundItem> segFundItems;

  public List<SegFundItem> getSegFundItems() {
    return segFundItems;
  }

  public void setSegFundItems(
      List<SegFundItem> segFundItems) {
    this.segFundItems = segFundItems;
  }

  @Override
  public String toString() {
    return "SegFundsResponse{" +
        "items='" + JSON.toJSONString(segFundItems, SerializerFeature.WriteEnumUsingToString) + '\'' +
        '}';
  }
}
