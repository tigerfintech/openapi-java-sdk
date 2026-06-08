package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.OptionExerciseRecordPageItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

public class OptionExerciseRecordResponse extends TigerResponse {

  @JSONField(name = "data")
  private OptionExerciseRecordPageItem item;

  public OptionExerciseRecordPageItem getItem() {
    return item;
  }

  public void setItem(OptionExerciseRecordPageItem item) {
    this.item = item;
  }
}
