package com.tigerbrokers.stock.openapi.client.https.response.future;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureTradingDateItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureTradingDateResponse extends TigerResponse {

  @JSONField(name = "data")
  private FutureTradingDateItem futureTradingDateItem;

  public FutureTradingDateItem getFutureTradingDateItem() {
    return futureTradingDateItem;
  }

  public void setFutureTradingDateItem(FutureTradingDateItem futureTradingDateItem) {
    this.futureTradingDateItem = futureTradingDateItem;
  }
}
