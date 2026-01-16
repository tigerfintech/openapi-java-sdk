package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.PositionTransferItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

public class PositionTransferResponse extends TigerResponse {

  @JSONField(name = "data")
  private PositionTransferItem item;

  public PositionTransferItem getItem() {
    return item;
  }

  public void setItem(PositionTransferItem item) {
    this.item = item;
  }
}
