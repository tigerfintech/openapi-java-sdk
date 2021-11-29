package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteContractResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<ContractItem> contractItems;

  public List<ContractItem> getContractItems() {
    return contractItems;
  }

  public void setContractItems(List<ContractItem> contractItems) {
    this.contractItems = contractItems;
  }
}
