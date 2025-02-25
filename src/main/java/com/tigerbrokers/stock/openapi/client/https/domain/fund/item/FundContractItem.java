package com.tigerbrokers.stock.openapi.client.https.domain.fund.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FundContractItem extends ApiModel {

  private String symbol;
  private String name;
  private String companyName;
  private String market;
  private String secType;
  private String currency;
  private Boolean tradeable;

  private String subType;
  private String dividendType;
  private Boolean tigerVault;

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

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
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

  public Boolean getTradeable() {
    return tradeable;
  }

  public void setTradeable(Boolean tradeable) {
    this.tradeable = tradeable;
  }

  public String getSubType() {
    return subType;
  }

  public void setSubType(String subType) {
    this.subType = subType;
  }

  public String getDividendType() {
    return dividendType;
  }

  public void setDividendType(String dividendType) {
    this.dividendType = dividendType;
  }

  public Boolean getTigerVault() {
    return tigerVault;
  }

  public void setTigerVault(Boolean tigerVault) {
    this.tigerVault = tigerVault;
  }

  @Override
  public String toString() {
    return "FundContractItem{" +
        "symbol='" + symbol + '\'' +
        ", name='" + name + '\'' +
        ", companyName='" + companyName + '\'' +
        ", market='" + market + '\'' +
        ", secType='" + secType + '\'' +
        ", currency='" + currency + '\'' +
        ", subType=" + subType +
        ", dividendType=" + dividendType +
        ", tradeable=" + tradeable +
        ", tigerVault=" + tigerVault +
        '}';
  }
}
