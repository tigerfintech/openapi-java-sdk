package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

public class OrderLeg extends ContractLeg {

  private String market;
  private String currency;
  private Integer multiplier;
  private Double totalQuantity;
  private Double filledQuantity;
  private Double avgFilledPrice;
  private Long createdAt;
  private Long updatedAt;

  public OrderLeg () {}

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public Integer getMultiplier() {
    return multiplier;
  }

  public void setMultiplier(Integer multiplier) {
    this.multiplier = multiplier;
  }

  public Double getTotalQuantity() {
    return totalQuantity;
  }

  public void setTotalQuantity(Double totalQuantity) {
    this.totalQuantity = totalQuantity;
  }

  public Double getFilledQuantity() {
    return filledQuantity;
  }

  public void setFilledQuantity(Double filledQuantity) {
    this.filledQuantity = filledQuantity;
  }

  public Double getAvgFilledPrice() {
    return avgFilledPrice;
  }

  public void setAvgFilledPrice(Double avgFilledPrice) {
    this.avgFilledPrice = avgFilledPrice;
  }

  public Long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Long createdAt) {
    this.createdAt = createdAt;
  }

  public Long getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Long updatedAt) {
    this.updatedAt = updatedAt;
  }
}
