package com.tigerbrokers.stock.openapi.client.https.response.future;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureHistoryMainContractItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by bean on 2023/08/29.
 */
public class FutureHistoryMainContractResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<FutureHistoryMainContractItem> historyMainContractItems;

  public List<FutureHistoryMainContractItem> getHistoryMainContractItems() {
    return historyMainContractItems;
  }

  public void setHistoryMainContractItems(
      List<FutureHistoryMainContractItem> historyMainContractItems) {
    this.historyMainContractItems = historyMainContractItems;
  }
}
