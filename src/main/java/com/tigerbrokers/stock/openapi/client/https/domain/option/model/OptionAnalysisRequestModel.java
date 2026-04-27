package com.tigerbrokers.stock.openapi.client.https.domain.option.model;

import java.util.List;

/**
 * Description: Option analysis request wrapper model
 */
public class OptionAnalysisRequestModel extends OptionModel {

  private List<OptionAnalysisModel> symbols;

  public OptionAnalysisRequestModel() {
  }

  public OptionAnalysisRequestModel(List<OptionAnalysisModel> symbols) {
    this.symbols = symbols;
  }

  public List<OptionAnalysisModel> getSymbols() {
    return symbols;
  }

  public void setSymbols(List<OptionAnalysisModel> symbols) {
    this.symbols = symbols;
  }

  @Override
  public String toString() {
    return "OptionAnalysisRequestModel{" +
        "symbols=" + symbols +
        '}';
  }
}
