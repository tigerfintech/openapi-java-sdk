package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * author：ltc
 * date：2019/08/13
 */
public class QuoteDepthItem extends ApiModel {

  /**
   * 股票代码
   */
  private String symbol;

  /**
   * 卖盘
   */
  private List<DepthEntry> asks;

  /**
   * 买盘
   */
  private List<DepthEntry> bids;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public List<DepthEntry> getAsks() {
    return asks;
  }

  public void setAsks(List<DepthEntry> asks) {
    this.asks = asks;
  }

  public List<DepthEntry> getBids() {
    return bids;
  }

  public void setBids(List<DepthEntry> bids) {
    this.bids = bids;
  }
}
