package com.tigerbrokers.stock.openapi.client.https.response.future;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureExchangeItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/21.
 */
public class FutureExchangeResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<FutureExchangeItem> futureExchangeItems;

  public List<FutureExchangeItem> getFutureExchangeItems() {
    return futureExchangeItems;
  }

  public void setFutureExchangeItems(List<FutureExchangeItem> futureExchangeItems) {
    this.futureExchangeItems = futureExchangeItems;
  }
}
