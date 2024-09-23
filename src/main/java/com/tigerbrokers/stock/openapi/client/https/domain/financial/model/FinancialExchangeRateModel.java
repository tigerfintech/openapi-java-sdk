package com.tigerbrokers.stock.openapi.client.https.domain.financial.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * Created by bean on 2023/08/10.
 */
public class FinancialExchangeRateModel extends ApiModel {

  @JSONField(name = "currency_list")
  private List<String> currencyList;
  @JSONField(name = "begin_date")
  private Date beginDate;
  @JSONField(name = "end_date")
  private Date endDate;

  public List<String> getCurrencyList() {
    return currencyList;
  }

  public void setCurrencyList(List<String> currencyList) {
    this.currencyList = currencyList;
  }

  public Date getBeginDate() {
    return beginDate;
  }

  public void setBeginDate(Date beginDate) {
    this.beginDate = beginDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  @Override
  public String toString() {
    return "FinancialExchangeRateModel{" +
        "currencyList=" + currencyList +
        ", beginDate=" + beginDate +
        ", endDate=" + endDate +
        '}';
  }
}
