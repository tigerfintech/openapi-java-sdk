package com.tigerbrokers.stock.openapi.client.https.domain.trade.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

public class PositionTransferRecordsModel extends ApiModel {

  @JSONField(name = "account_id")
  private String accountId;
  @JSONField(name = "since_date")
  private String sinceDate;
  @JSONField(name = "to_date")
  private String toDate;
  private String status;
  private String market;
  private String symbol;
  @JSONField(name = "secret_key")
  private String secretKey;

  public PositionTransferRecordsModel(String accountId, String sinceDate, String toDate,
      String status, String market, String symbol) {
    this.accountId = accountId;
    this.sinceDate = sinceDate;
    this.toDate = toDate;
    this.status = status;
    this.market = market;
    this.symbol = symbol;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getSinceDate() {
    return sinceDate;
  }

  public void setSinceDate(String sinceDate) {
    this.sinceDate = sinceDate;
  }

  public String getToDate() {
    return toDate;
  }

  public void setToDate(String toDate) {
    this.toDate = toDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }
}
