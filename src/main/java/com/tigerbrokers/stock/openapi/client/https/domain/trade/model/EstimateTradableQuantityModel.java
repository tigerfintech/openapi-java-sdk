package com.tigerbrokers.stock.openapi.client.https.domain.trade.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderType;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.struct.enums.SegmentType;

public class EstimateTradableQuantityModel extends ApiModel {

  /**
   * account id
   */
  private String account;

  @JSONField(name = "secret_key")
  private String secretKey;

  private String symbol;
  private String expiry;
  private String strike;
  private String right;

  @JSONField(name = "seg_type")
  private SegmentType segType;
  @JSONField(name = "sec_type")
  private SecType secType;

  private ActionType action;
  @JSONField(name = "order_type")
  private OrderType orderType;

  @JSONField(name = "limit_price")
  private Double limitPrice;
  @JSONField(name = "stop_price")
  private Double stopPrice;

  public EstimateTradableQuantityModel() {
  }

  @Override
  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getExpiry() {
    return expiry;
  }

  public void setExpiry(String expiry) {
    this.expiry = expiry;
  }

  public String getStrike() {
    return strike;
  }

  public void setStrike(String strike) {
    this.strike = strike;
  }

  public String getRight() {
    return right;
  }

  public void setRight(String right) {
    this.right = right;
  }

  public SegmentType getSegType() {
    return segType;
  }

  public void setSegType(SegmentType segType) {
    this.segType = segType;
  }

  public SecType getSecType() {
    return secType;
  }

  public void setSecType(SecType secType) {
    this.secType = secType;
  }

  public ActionType getAction() {
    return action;
  }

  public void setAction(ActionType action) {
    this.action = action;
  }

  public OrderType getOrderType() {
    return orderType;
  }

  public void setOrderType(OrderType orderType) {
    this.orderType = orderType;
  }

  public Double getLimitPrice() {
    return limitPrice;
  }

  public void setLimitPrice(Double limitPrice) {
    this.limitPrice = limitPrice;
  }

  public Double getStopPrice() {
    return stopPrice;
  }

  public void setStopPrice(Double stopPrice) {
    this.stopPrice = stopPrice;
  }

  @Override
  public String toString() {
    return "EstimateTradableQuantityModel{" +
        ", account='" + account + '\'' +
        ", secretKey='" + secretKey + '\'' +
        ", symbol='" + symbol + '\'' +
        ", expiry='" + expiry + '\'' +
        ", strike='" + strike + '\'' +
        ", right='" + right + '\'' +
        ", secType=" + secType +
        ", action=" + action +
        ", orderType=" + orderType +
        ", limitPrice=" + limitPrice +
        ", stopPrice=" + stopPrice +
        '}';
  }
}
