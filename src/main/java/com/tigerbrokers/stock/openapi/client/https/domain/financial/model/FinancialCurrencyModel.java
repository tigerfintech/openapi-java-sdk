package com.tigerbrokers.stock.openapi.client.https.domain.financial.model;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import java.util.List;

/**
 * Description:
 * Created by bean on 2023/08/10.
 */
public class FinancialCurrencyModel extends ApiModel {

  private List<String> symbols;
  private Market market;

  public List<String> getSymbols() {
    return symbols;
  }

  public void setSymbols(List<String> symbols) {
    this.symbols = symbols;
  }

  public Market getMarket() {
    return market;
  }

  public void setMarket(Market market) {
    this.market = market;
  }

  @Override
  public String toString() {
    return "FinancialCurrencyModel{" +
        "symbols=" + symbols +
        ", market=" + market +
        '}';
  }
}
