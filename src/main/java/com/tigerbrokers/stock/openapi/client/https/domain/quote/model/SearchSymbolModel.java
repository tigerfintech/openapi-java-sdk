package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

public class SearchSymbolModel extends ApiModel {

  private String keyword;
  private Integer size;
  private String market;

  public SearchSymbolModel() {
  }

  public SearchSymbolModel(String keyword) {
    this.keyword = keyword;
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }
}
