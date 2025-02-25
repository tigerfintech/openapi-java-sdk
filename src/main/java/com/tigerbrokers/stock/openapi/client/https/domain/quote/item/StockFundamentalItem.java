package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * author：bean
 * date：2024/12/19
 */
public class StockFundamentalItem extends ApiModel {

  /**
   * stock symbol
   */
  private String symbol;

  /**
   * rate of return
   */
  private Double roe;

  /**
   * price-to-book ratio
   */
  private Double pbRate;

  /**
   * divide rate
   */
  private Double divideRate;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Double getRoe() {
    return roe;
  }

  public void setRoe(Double roe) {
    this.roe = roe;
  }

  public Double getPbRate() {
    return pbRate;
  }

  public void setPbRate(Double pbRate) {
    this.pbRate = pbRate;
  }

  public Double getDivideRate() {
    return divideRate;
  }

  public void setDivideRate(Double divideRate) {
    this.divideRate = divideRate;
  }
}
