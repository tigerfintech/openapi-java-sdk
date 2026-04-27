package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.PositionTransferRecordItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

public class PositionTransferRecordsResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<PositionTransferRecordItem> item;

  public List<PositionTransferRecordItem> getItem() {
    return item;
  }

  public void setItem(
      List<PositionTransferRecordItem> item) {
    this.item = item;
  }
}
