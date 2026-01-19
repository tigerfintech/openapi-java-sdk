package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.PositionTransferDetailItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

public class PositionTransferDetailResponse extends TigerResponse {

  @JSONField(name = "data")
  private PositionTransferDetailItem item;

  public PositionTransferDetailItem getItem() {
    return item;
  }

  public void setItem(PositionTransferDetailItem item) {
    this.item = item;
  }
}
