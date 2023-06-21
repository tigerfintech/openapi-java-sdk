package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Right;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import java.io.Serializable;

public class ContractLeg implements Serializable {
  private static final long serialVersionUID = 1L;

  private String symbol;
  @JSONField(name = "sec_type")
  private String secType;
  private String expiry;
  private String strike;
  private String right;
  private String action;
  /** must be greater than 0 */
  private Integer ratio;

  public ContractLeg() {}

  public ContractLeg(SecType secType, String symbol, ActionType actionType, Integer ratio) {
    this.secType = secType.name();
    this.symbol = symbol;
    this.action = actionType.name();
    this.ratio = ratio;
  }

  public ContractLeg(SecType secType, String symbol,
      String strike, String expiry, Right right,
      ActionType actionType, Integer ratio) {
    this.secType = secType.name();
    this.symbol = symbol;
    this.strike = strike;
    this.expiry = expiry;
    this.right = right.name();
    this.action = actionType.name();
    this.ratio = ratio;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getSecType() {
    return secType;
  }

  public void setSecType(String secType) {
    this.secType = secType;
  }

  public String getExpiry() {
    return expiry;
  }

  public void setExpiry(String expiry) {
    this.expiry = expiry;
  }

  public String getStrike() {
    return strike;
  }

  public void setStrike(String strike) {
    this.strike = strike;
  }

  public String getRight() {
    return right;
  }

  public void setRight(String right) {
    this.right = right;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public Integer getRatio() {
    return ratio;
  }

  public void setRatio(Integer ratio) {
    this.ratio = ratio;
  }
}
