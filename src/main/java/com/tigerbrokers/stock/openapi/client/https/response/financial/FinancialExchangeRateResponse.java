package com.tigerbrokers.stock.openapi.client.https.response.financial;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.item.FinancialExchangeRateItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by bean on 2023/08/10.
 */
public class FinancialExchangeRateResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<FinancialExchangeRateItem> financialExchangeRateItems;

  public List<FinancialExchangeRateItem> getFinancialExchangeRateItems() {
    return financialExchangeRateItems;
  }

  public void setFinancialExchangeRateItems(
      List<FinancialExchangeRateItem> financialExchangeRateItems) {
    this.financialExchangeRateItems = financialExchangeRateItems;
  }
}
