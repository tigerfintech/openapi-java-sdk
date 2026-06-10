package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.OptionExercisePositionPageItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

public class OptionExercisePositionResponse extends TigerResponse {

  @JSONField(name = "data")
  private OptionExercisePositionPageItem item;

  public OptionExercisePositionPageItem getItem() {
    return item;
  }

  public void setItem(OptionExercisePositionPageItem item) {
    this.item = item;
  }
}
