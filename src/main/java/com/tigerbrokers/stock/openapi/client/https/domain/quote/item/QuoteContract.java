package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import java.io.Serializable;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteContract implements Serializable {

  /**
   * symbol
   */
  protected String symbol;

  /**
   * name
   */
  protected String name;

  /**
   * exchange
   */
  protected String exchange;

  /**
   * market
   */
  protected String market;

  /**
   * security type
   */
  protected String secType;

  /**
   * currency
   */
  protected String currency;

  /**
   * expiry(Options、WAR、CBBC、Futures)，format：yyyy-MM-dd
   */
  protected String expiry;

  /**
   * strike price
   */
  protected String strike;

  /**
   * multiplier
   */
  protected Double multiplier;

  /**
   * direction（PUT/CALL)
   */
  private String right;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getExchange() {
    return exchange;
  }

  public void setExchange(String exchange) {
    this.exchange = exchange;
  }

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public String getSecType() {
    return secType;
  }

  public void setSecType(String secType) {
    this.secType = secType;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
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

  public Double getMultiplier() {
    return multiplier;
  }

  public void setMultiplier(Double multiplier) {
    this.multiplier = multiplier;
  }

  public String getRight() {
    return right;
  }

  public void setRight(String right) {
    this.right = right;
  }

  @Override
  public String toString() {
    return "QuoteContract{" +
        "symbol='" + symbol + '\'' +
        ", name='" + name + '\'' +
        ", exchange='" + exchange + '\'' +
        ", market='" + market + '\'' +
        ", secType='" + secType + '\'' +
        ", currency='" + currency + '\'' +
        ", expiry='" + expiry + '\'' +
        ", strike='" + strike + '\'' +
        ", multiplier=" + multiplier +
        ", right='" + right + '\'' +
        '}';
  }
}
