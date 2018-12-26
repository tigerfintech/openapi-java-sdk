package com.tigerbrokers.stock.openapi.client.https.response.future;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureKlineItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureKlineResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<FutureKlineItem> futureKlineItems;

  public List<FutureKlineItem> getFutureKlineItems() {
    return futureKlineItems;
  }

  public void setFutureKlineItems(List<FutureKlineItem> futureKlineItems) {
    this.futureKlineItems = futureKlineItems;
  }
}