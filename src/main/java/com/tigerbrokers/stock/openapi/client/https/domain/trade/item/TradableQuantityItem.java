package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

import java.io.Serializable;

/**
 * @author bean
 * @date 2023/4/11 4:03 PM
 */
public class TradableQuantityItem implements Serializable {
  private static final long serialVersionUID = 1L;

  /** tradable quantity for cash */
  private Double tradableQuantity;
  /** tradable quantity for margin */
  private Double financingQuantity;
  /** position quantity */
  private Double positionQuantity;
  /** tradable quantity in the position */
  private Double tradablePositionQuantity;

  public Double getTradableQuantity() {
    return tradableQuantity;
  }

  public void setTradableQuantity(Double tradableQuantity) {
    this.tradableQuantity = tradableQuantity;
  }

  public Double getFinancingQuantity() {
    return financingQuantity;
  }

  public void setFinancingQuantity(Double financingQuantity) {
    this.financingQuantity = financingQuantity;
  }

  public Double getPositionQuantity() {
    return positionQuantity;
  }

  public void setPositionQuantity(Double positionQuantity) {
    this.positionQuantity = positionQuantity;
  }

  public Double getTradablePositionQuantity() {
    return tradablePositionQuantity;
  }

  public void setTradablePositionQuantity(Double tradablePositionQuantity) {
    this.tradablePositionQuantity = tradablePositionQuantity;
  }
}
