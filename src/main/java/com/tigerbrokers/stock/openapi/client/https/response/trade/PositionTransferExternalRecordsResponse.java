package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.PositionTransferExternalRecordItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

public class PositionTransferExternalRecordsResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<PositionTransferExternalRecordItem> item;

  public List<PositionTransferExternalRecordItem> getItem() {
    return item;
  }

  public void setItem(List<PositionTransferExternalRecordItem> item) {
    this.item = item;
  }
}
