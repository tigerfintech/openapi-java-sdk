package com.tigerbrokers.stock.openapi.client.https.domain.contract.model;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2019/06/26.
 */
public class ContractsModel extends BaseContractModel {

  private List<String> symbols;

  public ContractsModel() {

  }

  /**
   * use ClientConfig.DEFAULT_CONFIG.defaultAccount
   */
  public ContractsModel(List<String> symbols) {
    this.symbols = symbols;
    setAccount(ClientConfig.DEFAULT_CONFIG.defaultAccount);
    setSecType(SecType.STK.name());
  }

  public ContractsModel(List<String> symbols, String secType) {
    this(symbols);
    setSecType(secType);
  }

  public ContractsModel(List<String> symbols, String secType, String expiry, Double strike,
      String right) {
    this(symbols, secType);
    setStrike(strike);
    setExpiry(expiry);
    setRight(right);
  }

  public List<String> getSymbols() {
    return symbols;
  }

  public void setSymbols(List<String> symbols) {
    this.symbols = symbols;
  }
}
