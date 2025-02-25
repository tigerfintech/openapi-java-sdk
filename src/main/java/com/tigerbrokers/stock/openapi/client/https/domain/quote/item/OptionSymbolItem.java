package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by liutongping on 2024/05/24.
 */
public class OptionSymbolItem extends ApiModel {

  private String symbol;
  private String name;
  private String underlyingSymbol;

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

  public String getUnderlyingSymbol() {
    return underlyingSymbol;
  }

  public void setUnderlyingSymbol(String underlyingSymbol) {
    this.underlyingSymbol = underlyingSymbol;
  }

  @Override
  public String toString() {
    return "SymbolNameItem{" +
        "symbol='" + symbol + '\'' +
        ", name='" + name + '\'' +
        ", underlyingSymbol='" + underlyingSymbol + '\'' +
        '}';
  }
}
