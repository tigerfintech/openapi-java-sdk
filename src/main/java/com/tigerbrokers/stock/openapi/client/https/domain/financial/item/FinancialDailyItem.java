package com.tigerbrokers.stock.openapi.client.https.domain.financial.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.Date;

/**
 * Description:
 * Created by lijiawen on 2019/01/18.
 */
public class FinancialDailyItem extends ApiModel {

  private String symbol;
  private Date date;
  private String field;
  private Double value;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public Double getValue() {
    return value;
  }

  public void setValue(Double value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "FinancialDailyItem{" +
        "symbol='" + symbol + '\'' +
        ", date=" + date +
        ", field='" + field + '\'' +
        ", value=" + value +
        '}';
  }
}
