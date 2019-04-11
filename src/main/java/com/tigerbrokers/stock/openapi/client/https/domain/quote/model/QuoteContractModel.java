package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteContractModel extends QuoteSymbolModel {

  @JSONField(name = "sec_type")
  private SecType secType;

  private String right;

  private String strike;

  private Long expiry;

  public Long getExpiry() {
    return expiry;
  }

  public void setExpiry(Long expiry) {
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

  public QuoteContractModel(List<String> symbols) {
    super(symbols);
  }

  public QuoteContractModel(List<String> symbols, SecType secType) {
    super(symbols);
    this.secType = secType;
  }

  public QuoteContractModel(List<String> symbols, SecType secType, Language lang) {
    super(symbols, lang);
    this.secType = secType;
  }

  public QuoteContractModel(List<String> symbols, SecType secType, Long expiry) {
    this(symbols,secType);
    this.expiry = expiry;
  }

  public QuoteContractModel(List<String> symbols, SecType secType, Long expiry, Language lang) {
    this(symbols,secType,expiry);
    this.setLang(lang);
  }

  public QuoteContractModel(List<String> symbols, SecType secType, Long expiry, String right, String strike) {
    this(symbols,secType,expiry);
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
