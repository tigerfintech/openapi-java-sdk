package com.tigerbrokers.stock.openapi.client.https.response.contract;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * 用于接受账户api合约接口的返回值
 * 作者：ltc
 * 时间：2019/05/29
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
