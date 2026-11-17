package com.tigerbrokers.stock.openapi.client.https.domain.contract.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/** A supported combination of order type, time in force, and trading session. */
public class OrderCapabilityItem extends ApiModel {

  private String orderType;
  private String timeInForce;
  private String tradingSessionType;

  public String getOrderType() {
    return orderType;
  }

  public void setOrderType(String orderType) {
    this.orderType = orderType;
  }

  public String getTimeInForce() {
    return timeInForce;
  }

  public void setTimeInForce(String timeInForce) {
    this.timeInForce = timeInForce;
  }

  public String getTradingSessionType() {
    return tradingSessionType;
  }

  public void setTradingSessionType(String tradingSessionType) {
    this.tradingSessionType = tradingSessionType;
  }

  @Override
  public String toString() {
    return "OrderCapabilityItem{" +
        "orderType='" + orderType + '\'' +
        ", timeInForce='" + timeInForce + '\'' +
        ", tradingSessionType='" + tradingSessionType + '\'' +
        '}';
  }
}
