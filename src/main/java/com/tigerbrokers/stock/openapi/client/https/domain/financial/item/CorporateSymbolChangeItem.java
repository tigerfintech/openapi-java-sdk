package com.tigerbrokers.stock.openapi.client.https.domain.financial.item;

public class CorporateSymbolChangeItem extends CorporateActionItem {

  private String oldSymbol;
  private String newSymbol;

  public String getOldSymbol() {
    return oldSymbol;
  }

  public void setOldSymbol(String oldSymbol) {
    this.oldSymbol = oldSymbol;
  }

  public String getNewSymbol() {
    return newSymbol;
  }

  public void setNewSymbol(String newSymbol) {
    this.newSymbol = newSymbol;
  }

  @Override
  public String toString() {
    return "CorporateSymbolChangeItem{" +
        "oldSymbol='" + oldSymbol + '\'' +
        ", newSymbol='" + newSymbol + '\'' +
        ", symbol='" + getSymbol() + '\'' +
        ", market='" + getMarket() + '\'' +
        ", executeDate=" + getExecuteDate() +
        ", actionType=" + getActionType() +
        '}';
  }
}
