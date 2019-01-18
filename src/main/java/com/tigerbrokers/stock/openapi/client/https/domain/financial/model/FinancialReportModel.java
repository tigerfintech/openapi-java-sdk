package com.tigerbrokers.stock.openapi.client.https.domain.financial.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.FinancialPeriodType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2019/01/18.
 */
public class FinancialReportModel extends ApiModel {

  private List<String> symbols;
  private Market market;
  private List<String> fields;
  @JSONField(name = "period_type")
  private FinancialPeriodType periodType;

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

  public FinancialPeriodType getPeriodType() {
    return periodType;
  }

  public void setPeriodType(FinancialPeriodType periodType) {
    this.periodType = periodType;
  }

  @Override
  public String toString() {
    return "FinancialReportModel{" +
        "symbols=" + symbols +
        ", market=" + market +
        ", fields=" + fields +
        ", periodType=" + periodType +
        '}';
  }
}
