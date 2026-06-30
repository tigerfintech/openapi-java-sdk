package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.OptionExerciseCheckItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

public class OptionExerciseCheckResponse extends TigerResponse {

  @JSONField(name = "data")
  private OptionExerciseCheckItem item;

  public OptionExerciseCheckItem getItem() {
    return item;
  }

  public void setItem(OptionExerciseCheckItem item) {
    this.item = item;
  }
}
