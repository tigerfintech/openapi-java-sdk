package com.tigerbrokers.stock.openapi.client.https.response.financial;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.item.FinancialReportItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2019/01/18.
 */
public class FinancialReportResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<FinancialReportItem> financialReportItems;

  public List<FinancialReportItem> getFinancialReportItems() {
    return financialReportItems;
  }

  public void setFinancialReportItems(List<FinancialReportItem> financialReportItems) {
    this.financialReportItems = financialReportItems;
  }
}
