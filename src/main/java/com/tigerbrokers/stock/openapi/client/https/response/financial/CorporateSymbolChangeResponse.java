package com.tigerbrokers.stock.openapi.client.https.response.financial;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.item.CorporateSymbolChangeItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;
import java.util.Map;

public class CorporateSymbolChangeResponse extends TigerResponse {

  @JSONField(name = "data")
  private Map<String, List<CorporateSymbolChangeItem>> items;

  public Map<String, List<CorporateSymbolChangeItem>> getItems() {
    return items;
  }

  public void setItems(Map<String, List<CorporateSymbolChangeItem>> items) {
    this.items = items;
  }
}
