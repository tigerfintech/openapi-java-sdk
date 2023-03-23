package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

import java.io.Serializable;

/**
 * @author bean
 * @date 2023/3/10 2:03 PM
 */
public class SegmentFundAvailableItem implements Serializable {
  private static final long serialVersionUID = 1L;

  private String fromSegment;
  private String toSegment;
  private String currency;
  private Double amount;

  public String getFromSegment() {
    return fromSegment;
  }

  public void setFromSegment(String fromSegment) {
    this.fromSegment = fromSegment;
  }

  public String getToSegment() {
    return toSegment;
  }

  public void setToSegment(String toSegment) {
    this.toSegment = toSegment;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }
}
