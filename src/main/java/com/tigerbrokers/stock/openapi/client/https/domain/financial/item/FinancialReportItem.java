package com.tigerbrokers.stock.openapi.client.https.domain.financial.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by lijiawen on 2019/01/18.
 */
public class FinancialReportItem extends ApiModel {

  private String symbol;
  private String currency;
  private String field;
  private String value;
  private String filingDate;
  private String periodEndDate;

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

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getFilingDate() {
    return filingDate;
  }

  public void setFilingDate(String filingDate) {
    this.filingDate = filingDate;
  }

  public String getPeriodEndDate() {
    return periodEndDate;
  }

  public void setPeriodEndDate(String periodEndDate) {
    this.periodEndDate = periodEndDate;
  }

  @Override
  public String toString() {
    return "FinancialReportItem{" +
        "symbol='" + symbol + '\'' +
        ", currency='" + currency + '\'' +
        ", field='" + field + '\'' +
        ", value='" + value + '\'' +
        ", filingDate='" + filingDate + '\'' +
        ", periodEndDate='" + periodEndDate + '\'' +
        '}';
  }
}
