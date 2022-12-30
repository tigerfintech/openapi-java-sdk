package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteContractModel extends ApiModel {

  private String symbol;

  @JSONField(name = "sec_type")
  private SecType secType;

  private String right;

  private String strike;

  private String expiry;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getExpiry() {
    return expiry;
  }

  public void setExpiry(String expiry) {
    this.expiry = expiry;
  }

  public String getRight() {
    return right;
  }

  public void setRight(String right) {
    this.right = right;
  }

  public String getStrike() {
    return strike;
  }

  public void setStrike(String strike) {
    this.strike = strike;
  }

  public QuoteContractModel() {
  }

  public QuoteContractModel(String symbol) {
    this.symbol = symbol;
  }

  public QuoteContractModel(String symbol, SecType secType) {
    this.symbol = symbol;
    this.secType = secType;
  }

  public QuoteContractModel(String symbol, SecType secType, Language lang) {
    this.symbol = symbol;
    this.secType = secType;
  }

  public QuoteContractModel(String symbol, SecType secType, String expiry) {
    this(symbol, secType);
    this.expiry = expiry;
  }

  public QuoteContractModel(String symbol, SecType secType, String expiry, Language lang) {
    this(symbol, secType, expiry);
    this.setLang(lang);
  }

  public QuoteContractModel(String symbol, SecType secType, String expiry, String right, String strike) {
    this(symbol, secType, expiry);
    this.setRight(right);
    this.setStrike(strike);
  }

  public SecType getSecType() {
    return secType;
  }

  public void setSecType(SecType secType) {
    this.secType = secType;
  }
}
