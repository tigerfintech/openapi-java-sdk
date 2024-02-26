package com.tigerbrokers.stock.openapi.client.https.domain.fund.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FundQuoteItem extends ApiModel {

  /**
   * fund code
   */
  private String symbol;

  private Float close;

  private Long timestamp;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Float getClose() {
    return close;
  }

  public void setClose(Float close) {
    this.close = close;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return "KlineItem{" +
        "symbol='" + symbol + '\'' +
        ", close=" + close +
        ", timestamp=" + timestamp +
        '}';
  }
}
