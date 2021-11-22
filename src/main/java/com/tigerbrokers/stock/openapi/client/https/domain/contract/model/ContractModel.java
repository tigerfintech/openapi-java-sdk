package com.tigerbrokers.stock.openapi.client.https.domain.contract.model;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;

/**
 * 作者：ltc
 * 时间：2019/05/29
 */
public class ContractModel extends BaseContractModel {

  private String symbol;

  public ContractModel() {

  }

  /**
   * use ClientConfig.DEFAULT_CONFIG.defaultAccount
   * @param symbol symbol
   */
  public ContractModel(String symbol) {
    this.symbol = symbol;
    setAccount(ClientConfig.DEFAULT_CONFIG.defaultAccount);
    setSecType(SecType.STK.name());
  }

  /**
   * use ClientConfig.DEFAULT_CONFIG.defaultAccount
   * @param symbol symbol
   * @param secType secType
   */
  public ContractModel(String symbol, String secType) {
    this(symbol);
    setSecType(secType);
  }

  public ContractModel(String symbol, String secType, String currency) {
    this(symbol, secType);
    setCurrency(currency);
  }

  public ContractModel(String symbol, String secType, String currency, String expiry, Double strike,
      String right) {
    this(symbol, secType, currency);
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

  public static ContractModel getStockModel(String symbol) {
    return new ContractModel(symbol, SecType.STK.name());
  }

  public static ContractModel getOptionModel(String symbol, String expiry, Double strike, String right) {
    return new ContractModel(symbol, SecType.OPT.name(), null, expiry, strike, right);
  }

  public static ContractModel getWarrantModel(String symbol, String expiry, Double strike, String right) {
    return new ContractModel(symbol, SecType.WAR.name(), null, expiry, strike, right);
  }

  public static ContractModel getCbbcModel(String symbol, String expiry, Double strike, String right) {
    return new ContractModel(symbol, SecType.IOPT.name(), null, expiry, strike, right);
  }

  public static ContractModel getFutureModel(String symbol) {
    return new ContractModel(symbol, SecType.FUT.name());
  }
}
