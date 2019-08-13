package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * 作者：ltc
 * 时间：2019/08/13
 */
public class QuoteAskBidModel extends ApiModel {

  private List<String> symbols;

  public QuoteAskBidModel(List<String> symbols) {
    this.symbols = symbols;
  }

  public List<String> getSymbols() {
    return symbols;
  }

  public void setSymbols(List<String> symbols) {
    this.symbols = symbols;
  }
}
