package com.tigerbrokers.stock.openapi.client.https.response.future;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureTickBatchItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureTickResponse extends TigerResponse {

  @JSONField(name = "data")
  private FutureTickBatchItem futureTickItems;

  public FutureTickBatchItem getFutureTickItems() {
    return futureTickItems;
  }

  public void setFutureTickItems(FutureTickBatchItem futureTickItems) {
    this.futureTickItems = futureTickItems;
  }
}
