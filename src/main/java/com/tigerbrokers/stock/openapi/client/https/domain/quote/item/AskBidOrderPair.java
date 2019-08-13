package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：ltc
 * 时间：2019/08/13
 */
public class AskBidOrderPair implements Serializable {

  /**
   * 卖盘
   */
  private List<OrderPoint> asks;

  /**
   * 买盘
   */
  private List<OrderPoint> bids;

  public List<OrderPoint> getAsks() {
    return asks;
  }

  public void setAsks(List<OrderPoint> asks) {
    this.asks = asks;
  }

  public List<OrderPoint> getBids() {
    return bids;
  }

  public void setBids(List<OrderPoint> bids) {
    this.bids = bids;
  }

  @Override
  public String toString() {
    return "AskBidOrderPair{" +
        "asks=" + asks +
        ", bids=" + bids +
        '}';
  }
}
