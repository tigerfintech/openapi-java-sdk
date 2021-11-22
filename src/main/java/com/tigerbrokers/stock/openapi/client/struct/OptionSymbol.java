package com.tigerbrokers.stock.openapi.client.struct;

/**
 * 作者：ltc
 * 时间：2019/09/04
 */
public class OptionSymbol {

  private String symbol;
  private String expiry;
  private String strike;
  private String right;

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
}
