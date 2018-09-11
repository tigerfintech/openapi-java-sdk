package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by lijiawen on 2018/05/31.
 */
public enum ActionType {

  BUY("BUY"), SELL("SELL");

  private String action;

  ActionType(String action) {
    this.action = action;
  }
}
