package com.tigerbrokers.stock.openapi.client.https.domain.option.model;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.OptionAnalysisPeriod;

/**
 * Description: Option analysis request model
 */
public class OptionAnalysisModel extends ApiModel {

  private String symbol;
  private String period;

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

  @Override
  public String toString() {
    return "OptionAnalysisModel{" +
        "symbol='" + symbol + '\'' +
        ", period='" + period + '\'' +
        '}';
  }
}
