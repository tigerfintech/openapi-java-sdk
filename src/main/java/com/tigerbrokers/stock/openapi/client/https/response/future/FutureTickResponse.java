package com.tigerbrokers.stock.openapi.client.https.response.future;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureTickBatchItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureTickResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<FutureTickBatchItem> futureTickItems;

  public List<FutureTickBatchItem> getFutureTickItems() {
    return futureTickItems;
  }

  public void setFutureTickItems(List<FutureTickBatchItem> futureTickItems) {
    this.futureTickItems = futureTickItems;
  }
}
