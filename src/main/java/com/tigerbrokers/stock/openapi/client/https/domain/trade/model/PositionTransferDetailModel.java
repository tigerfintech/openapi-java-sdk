package com.tigerbrokers.stock.openapi.client.https.domain.trade.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

public class PositionTransferDetailModel extends ApiModel {

  private Long id;
  @JSONField(name = "account_id")
  private String accountId;
  @JSONField(name = "secret_key")
  private String secretKey;

  public PositionTransferDetailModel(Long id, String accountId) {
    this.id = id;
    this.accountId = accountId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }
}
