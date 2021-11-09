package com.tigerbrokers.stock.openapi.client.https.response.contract;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用于接受账户api合约接口的返回值
 * 作者：ltc
 * 时间：2019/05/29
 */
public class ContractsResponse extends TigerResponse {

  @JSONField(name = "data")
  private Map<String, List<ContractItem>> items;

  public List<ContractItem> getItems() {
    if (items != null) {
      return items.get("items");
    }
    return new ArrayList<>();
  }

  public void setItems(Map<String, List<ContractItem>> items) {
    this.items = items;
  }
}
