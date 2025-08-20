package com.tigerbrokers.stock.openapi.client.https.response.future;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureDepthItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

public class FutureDepthResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<FutureDepthItem> futureDepthItems;

  public List<FutureDepthItem> getFutureDepthItems() {
    return futureDepthItems;
  }

  public void setFutureDepthItems(List<FutureDepthItem> futureDepthItems) {
    this.futureDepthItems = futureDepthItems;
  }
}
