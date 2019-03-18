package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteContractResponse extends TigerResponse {

  @JSONField(name = "data")
  private ContractItem contractItem;

  public ContractItem getContractItem() {
    return contractItem;
  }

  public void setContractItem(ContractItem contractItem) {
    this.contractItem = contractItem;
  }
}
