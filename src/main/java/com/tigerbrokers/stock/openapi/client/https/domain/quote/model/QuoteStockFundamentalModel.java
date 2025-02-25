package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

import java.util.List;

/**
 * author：bean
 * date：2024/12/19
 */
public class QuoteStockFundamentalModel extends ApiModel {

  private List<String> symbols;
  private String market;

  public QuoteStockFundamentalModel(List<String> symbols, String market) {
    this.symbols = symbols;
    this.market = market;
  }

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public List<String> getSymbols() {
    return symbols;
  }

  public void setSymbols(List<String> symbols) {
    this.symbols = symbols;
  }
}
