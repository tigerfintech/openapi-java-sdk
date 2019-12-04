package com.tigerbrokers.stock.openapi.client.https.response.financial;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.item.CorporateEarningItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by lijiawen on 2019/11/27
 */
public class CorporateEarningResponse extends TigerResponse {

  @JSONField(name = "data")
  private Map<String, List<CorporateEarningItem>> items;

  public Map<String, List<CorporateEarningItem>> getItems() {
    return items;
  }

  public void setItems(Map<String, List<CorporateEarningItem>> items) {
    this.items = items;
  }
}
