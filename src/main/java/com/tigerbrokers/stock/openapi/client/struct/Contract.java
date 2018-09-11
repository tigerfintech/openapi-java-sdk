package com.tigerbrokers.stock.openapi.client.struct;

import java.io.Serializable;

/**
 * Description:
 * Created by lijiawen on 2018/05/23.
 */
public class Contract implements Serializable {

  public String symbol;
  public String securityType;
  public String exchange;
  public String primaryExchange;
  public String currency;
  public String expiry;
  public double strike;
  public String right;
  public String localSymbol;
  public String contractId;
  public String multiplier;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getSecurityType() {
    return securityType;
  }

  public void setSecurityType(String securityType) {
    this.securityType = securityType;
  }

  public String getExchange() {
    return exchange;
  }

  public void setExchange(String exchange) {
    this.exchange = exchange;
  }

  public String getPrimaryExchange() {
    return primaryExchange;
  }

  public void setPrimaryExchange(String primaryExchange) {
    this.primaryExchange = primaryExchange;
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

  public double getStrike() {
    return strike;
  }

  public void setStrike(double strike) {
    this.strike = strike;
  }

  public String getRight() {
    return right;
  }

  public void setRight(String right) {
    this.right = right;
  }

  public String getLocalSymbol() {
    return localSymbol;
  }

  public void setLocalSymbol(String localSymbol) {
    this.localSymbol = localSymbol;
  }

  public String getContractId() {
    return contractId;
  }

  public void setContractId(String contractId) {
    this.contractId = contractId;
  }

  public String getMultiplier() {
    return multiplier;
  }

  public void setMultiplier(String multiplier) {
    this.multiplier = multiplier;
  }
}
