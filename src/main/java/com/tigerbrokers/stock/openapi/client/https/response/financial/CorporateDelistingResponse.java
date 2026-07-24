package com.tigerbrokers.stock.openapi.client.https.response.financial;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.item.CorporateDelistingItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;
import java.util.Map;

public class CorporateDelistingResponse extends TigerResponse {

  @JSONField(name = "data")
  private Map<String, List<CorporateDelistingItem>> items;

  public Map<String, List<CorporateDelistingItem>> getItems() {
    return items;
  }

  public void setItems(Map<String, List<CorporateDelistingItem>> items) {
    this.items = items;
  }
}
