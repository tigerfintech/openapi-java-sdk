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

  public ContractModel(String account, String symbol, String secType) {
    this(account, symbol);
    setSecType(secType);
  }

  public ContractModel(String account, String symbol, String secType, String expiry, Double strike, String right) {
    this(account, symbol, secType);
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
