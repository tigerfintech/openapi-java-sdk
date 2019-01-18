package com.tigerbrokers.stock.openapi.client.https.domain.financial.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2019/01/18.
 */
public class FinancialDailyModel extends ApiModel {

  private List<String> symbols;
  private Market market;
  private List<String> fields;
  @JSONField(name = "begin_date")
  private Date beginDate;
  @JSONField(name = "end_date")
  private Date endDate;

  public List<String> getSymbols() {
    return symbols;
  }

  public void setSymbols(List<String> symbols) {
    this.symbols = symbols;
  }

  public Market getMarket() {
    return market;
  }

  public void setMarket(Market market) {
    this.market = market;
  }

  public List<String> getFields() {
    return fields;
  }

  public void setFields(List<String> fields) {
    this.fields = fields;
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
    return "FinancialDailyModel{" +
        "symbols=" + symbols +
        ", market=" + market +
        ", fields=" + fields +
        ", beginDate=" + beginDate +
        ", endDate=" + endDate +
        '}';
  }
}
