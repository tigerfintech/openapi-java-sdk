package com.tigerbrokers.stock.openapi.client.https.response.future;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureRealTimeItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureRealTimeQuoteResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<FutureRealTimeItem> futureRealTimeItems;

  public List<FutureRealTimeItem> getFutureRealTimeItems() {
    return futureRealTimeItems;
  }

  public void setFutureRealTimeItems(List<FutureRealTimeItem> futureRealTimeItems) {
    this.futureRealTimeItems = futureRealTimeItems;
  }
}
