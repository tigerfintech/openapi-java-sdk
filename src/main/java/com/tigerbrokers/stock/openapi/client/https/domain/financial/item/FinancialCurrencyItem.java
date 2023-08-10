package com.tigerbrokers.stock.openapi.client.https.domain.financial.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by bean on 2023/08/10.
 */
public class FinancialCurrencyItem extends ApiModel {

  private String symbol;
  private String currency;
  private String companyCurrency;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getCompanyCurrency() {
    return companyCurrency;
  }

  public void setCompanyCurrency(String companyCurrency) {
    this.companyCurrency = companyCurrency;
  }

  @Override
  public String toString() {
    return "FinancialDailyItem{" +
        "symbol='" + symbol + '\'' +
        ", currency=" + currency +
        ", companyCurrency=" + companyCurrency +
        '}';
  }
}
