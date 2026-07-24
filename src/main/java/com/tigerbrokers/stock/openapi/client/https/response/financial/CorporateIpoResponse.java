package com.tigerbrokers.stock.openapi.client.https.response.financial;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.item.CorporateIpoItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;
import java.util.Map;

public class CorporateIpoResponse extends TigerResponse {

  @JSONField(name = "data")
  private Map<String, List<CorporateIpoItem>> items;

  public Map<String, List<CorporateIpoItem>> getItems() {
    return items;
  }

  public void setItems(Map<String, List<CorporateIpoItem>> items) {
    this.items = items;
  }
}
