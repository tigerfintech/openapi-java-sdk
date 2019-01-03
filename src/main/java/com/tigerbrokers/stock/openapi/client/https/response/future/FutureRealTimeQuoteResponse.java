package com.tigerbrokers.stock.openapi.client.https.response.future;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureRealTimeItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureRealTimeQuoteResponse extends TigerResponse {

  @JSONField(name = "data")
  private FutureRealTimeItem futureRealTimeItem;

  public FutureRealTimeItem getFutureRealTimeItem() {
    return futureRealTimeItem;
  }

  public void setFutureRealTimeItem(FutureRealTimeItem futureRealTimeItem) {
    this.futureRealTimeItem = futureRealTimeItem;
  }
}
