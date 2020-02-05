package com.tigerbrokers.stock.openapi.client.https.domain.contract.model;

import lombok.Data;

/**
 * 作者：ltc
 * 时间：2019/05/29
 */
@Data
public class ContractModel extends BaseContractModel {

  private String symbol;

  public ContractModel() {

  }

  public ContractModel(String account, String symbol) {
    this.symbol = symbol;
    setAccount(account);
  }

  public ContractModel(String account, String symbol, String secType, String currency) {
    this(account, symbol);
    setSecType(secType);
    setCurrency(currency);
  }

  public ContractModel(String account, String symbol, String secType, String currency, String expiry, Double strike,
      String right) {
    this(account, symbol, secType, currency);
    setStrike(strike);
    setExpiry(expiry);
    setRight(right);
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }
}
