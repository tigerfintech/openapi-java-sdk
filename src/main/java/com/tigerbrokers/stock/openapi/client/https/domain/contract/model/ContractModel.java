package com.tigerbrokers.stock.openapi.client.https.domain.contract.model;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
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

  /**
   * use ClientConfig.DEFAULT_CONFIG.defaultAccount
   * @param symbol
   */
  public ContractModel(String symbol) {
    this.symbol = symbol;
    setAccount(ClientConfig.DEFAULT_CONFIG.defaultAccount);
    setSecType(SecType.STK.name());
  }

  /**
   * use ClientConfig.DEFAULT_CONFIG.defaultAccount
   * @param symbol
   * @param secType
   */
  public ContractModel(String symbol, String secType) {
    this(symbol);
    setSecType(secType);
  }

  /**
   * use ClientConfig.DEFAULT_CONFIG.defaultAccount
   */
  public ContractModel(String symbol, String secType, String currency) {
    this(symbol, secType);
    setCurrency(currency);
  }

  /**
   * use ClientConfig.DEFAULT_CONFIG.defaultAccount
   */
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
}
