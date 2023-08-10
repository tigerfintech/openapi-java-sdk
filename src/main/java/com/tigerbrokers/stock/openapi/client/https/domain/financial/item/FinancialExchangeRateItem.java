package com.tigerbrokers.stock.openapi.client.https.domain.financial.item;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * Created by bean on 2023/08/10.
 */
public class FinancialExchangeRateItem extends ApiModel {

  private String symbol;
  private List<DailyValue> dailyValueList;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public List<DailyValue> getDailyValueList() {
    return dailyValueList;
  }

  public void setDailyValueList(
      List<DailyValue> dailyValueList) {
    this.dailyValueList = dailyValueList;
  }

  @Override
  public String toString() {
    return "FinancialDailyItem{" +
        "symbol='" + symbol + '\'' +
        ", dailyValueList=" + JSONObject.toJSONString(dailyValueList) +
        '}';
  }

  private class DailyValue {
    private Date date;
    private BigDecimal value;

    public Date getDate() {
      return date;
    }

    public void setDate(Date date) {
      this.date = date;
    }

    public BigDecimal getValue() {
      return value;
    }

    public void setValue(BigDecimal value) {
      this.value = value;
    }
  }
}
