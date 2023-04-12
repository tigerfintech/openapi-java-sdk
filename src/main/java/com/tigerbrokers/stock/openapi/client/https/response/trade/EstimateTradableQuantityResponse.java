package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.TradableQuantityItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

public class EstimateTradableQuantityResponse extends TigerResponse {

  @JSONField(name = "data")
  private TradableQuantityItem tradableQuantityItem;

  public TradableQuantityItem getTradableQuantityItem() {
    return tradableQuantityItem;
  }

  public void setTradableQuantityItem(
      TradableQuantityItem tradableQuantityItem) {
    this.tradableQuantityItem = tradableQuantityItem;
  }

  @Override
  public String toString() {
    return "EstimateTradableQuantityResponse{" +
        "item='" + JSON.toJSONString(tradableQuantityItem, SerializerFeature.WriteEnumUsingToString) + '\'' +
        '}';
  }
}
