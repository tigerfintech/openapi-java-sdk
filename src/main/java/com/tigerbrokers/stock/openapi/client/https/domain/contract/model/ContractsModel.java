package com.tigerbrokers.stock.openapi.client.https.domain.contract.model;

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

  public ContractsModel(String account, List<String> symbols) {
    this.symbols = symbols;
    setAccount(account);
    setSecType(SecType.STK.name());
  }

  public ContractsModel(String account, List<String> symbols, String secType) {
    this(account, symbols);
    setSecType(secType);
  }

  public ContractsModel(String account, List<String> symbols, String secType, String expiry, Double strike,
      String right) {
    this(account, symbols, secType);
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
