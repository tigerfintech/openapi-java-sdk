package com.tigerbrokers.stock.openapi.client.https.response.future;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureContractItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureContractResponse extends TigerResponse {

  @JSONField(name = "data")
  private FutureContractItem futureContractItem;

  public FutureContractItem getFutureContractItem() {
    return futureContractItem;
  }

  public void setFutureContractItem(FutureContractItem futureContractItem) {
    this.futureContractItem = futureContractItem;
  }
}
