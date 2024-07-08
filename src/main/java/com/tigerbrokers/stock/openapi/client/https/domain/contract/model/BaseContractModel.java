package com.tigerbrokers.stock.openapi.client.https.domain.contract.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by lijiawen on 2019/06/26.
 */
public class BaseContractModel extends ApiModel {

  private String account;
  @JSONField(name = "sec_type")
  private String secType;
  private String currency;
  private String expiry;
  private Double strike;
  private String right;
  private String exchange;
  @JSONField(name = "secret_key")
  private String secretKey;

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getSecType() {
    return secType;
  }

  public void setSecType(String secType) {
    this.secType = secType;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getExpiry() {
    return expiry;
  }

  public void setExpiry(String expiry) {
    this.expiry = expiry;
  }

  public Double getStrike() {
    return strike;
  }

  public void setStrike(Double strike) {
    this.strike = strike;
  }

  public String getRight() {
    return right;
  }

  public void setRight(String right) {
    this.right = right;
  }

  public String getExchange() {
    return exchange;
  }

  public void setExchange(String exchange) {
    this.exchange = exchange;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }
}
