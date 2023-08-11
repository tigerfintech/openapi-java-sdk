package com.tigerbrokers.stock.openapi.client.https.response.financial;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.item.FinancialCurrencyItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by bean on 2023/08/10.
 */
public class FinancialCurrencyResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<FinancialCurrencyItem> financialCurrencyItems;

  public List<FinancialCurrencyItem> getFinancialCurrencyItems() {
    return financialCurrencyItems;
  }

  public void setFinancialCurrencyItems(
      List<FinancialCurrencyItem> financialCurrencyItems) {
    this.financialCurrencyItems = financialCurrencyItems;
  }
}
