package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * 作者：ltc
 * 时间：2019/08/13
 */
public class AskBidItem extends ApiModel {

  /**
   * 股票代码
   */
  private String symbol;

  /**
   * 十档买卖盘数据
   */
  private AskBidOrderPair askBidDepth;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public AskBidOrderPair getAskBidDepth() {
    return askBidDepth;
  }

  public void setAskBidDepth(AskBidOrderPair askBidDepth) {
    this.askBidDepth = askBidDepth;
  }

  @Override
  public String toString() {
    return "AskBidItem{" +
        "symbol='" + symbol + '\'' +
        ", askBidDepth=" + askBidDepth +
        '}';
  }
}
