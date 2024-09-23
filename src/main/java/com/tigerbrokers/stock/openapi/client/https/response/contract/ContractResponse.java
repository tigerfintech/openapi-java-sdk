package com.tigerbrokers.stock.openapi.client.https.response.contract;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * The return value of the contract API
 * author：ltc
 * date：2019/05/29
 */
public class ContractResponse extends TigerResponse {

  @JSONField(name = "data")
  private ContractItem item;

  public ContractItem getItem() {
    return item;
  }

  public void setItem(ContractItem item) {
    this.item = item;
  }
}
