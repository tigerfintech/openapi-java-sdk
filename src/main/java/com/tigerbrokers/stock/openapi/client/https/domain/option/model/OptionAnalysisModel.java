package com.tigerbrokers.stock.openapi.client.https.domain.option.model;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.OptionAnalysisPeriod;

/**
 * Description: Option analysis request model
 */
public class OptionAnalysisModel extends ApiModel {

  private String symbol;
  private String period;
  private Boolean requireVolatilityList;

  public OptionAnalysisModel() {
  }

  public OptionAnalysisModel(String symbol, String period) {
    this.symbol = symbol;
    this.period = period;
  }

  public OptionAnalysisModel(String symbol, OptionAnalysisPeriod period) {
    this.symbol = symbol;
    this.period = period.getValue();
  }

  public OptionAnalysisModel(String symbol, String period, Boolean requireVolatilityList) {
    this.symbol = symbol;
    this.period = period;
    this.requireVolatilityList = requireVolatilityList;
  }

  public OptionAnalysisModel(String symbol, OptionAnalysisPeriod period, Boolean requireVolatilityList) {
    this.symbol = symbol;
    this.period = period.getValue();
    this.requireVolatilityList = requireVolatilityList;
  }
  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getPeriod() {
    return period;
  }

  public void setPeriod(String period) {
    this.period = period;
  }

  public void setPeriod(OptionAnalysisPeriod period) {
    this.period = period.getValue();
  }

  public Boolean getRequireVolatilityList() {
    return requireVolatilityList;
  }

  public void setRequireVolatilityList(Boolean requireVolatilityList) {
    this.requireVolatilityList = requireVolatilityList;
  }

  @Override
  public String toString() {
    return "OptionAnalysisModel{" +
        "symbol='" + symbol + '\'' +
        ", period='" + period + '\'' +
        ", requireVolatilityList=" + requireVolatilityList +
        '}';
  }
}
