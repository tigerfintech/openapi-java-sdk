package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.CapitalFlowItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * Description:
 * Created by bean on 2022/11/25.
 */
public class QuoteCapitalFlowResponse extends TigerResponse {
  @JSONField(name = "data")
  private CapitalFlowItem capitalFlowItem;

  public CapitalFlowItem getCapitalFlowItem() {
    return capitalFlowItem;
  }

  public void setCapitalFlowItem(CapitalFlowItem capitalFlowItem) {
    this.capitalFlowItem = capitalFlowItem;
  }
}
