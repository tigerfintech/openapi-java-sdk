package com.tigerbrokers.stock.openapi.client.https.response.financial;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.item.CorporateSplitItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by lijiawen on 2019/02/28.
 */
public class CorporateSplitResponse extends TigerResponse {

  @JSONField(name = "data")
  private Map<String, List<CorporateSplitItem>> items;

  public Map<String, List<CorporateSplitItem>> getItems() {
    return items;
  }

  public void setItems(Map<String, List<CorporateSplitItem>> items) {
    this.items = items;
  }
}
