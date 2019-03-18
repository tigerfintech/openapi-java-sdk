package com.tigerbrokers.stock.openapi.client.https.response.financial;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.item.FinancialDailyItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2019/01/18.
 */
public class FinancialDailyResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<FinancialDailyItem> financialDailyItems;

  public List<FinancialDailyItem> getFinancialDailyItems() {
    return financialDailyItems;
  }

  public void setFinancialDailyItems(List<FinancialDailyItem> financialDailyItems) {
    this.financialDailyItems = financialDailyItems;
  }
}
