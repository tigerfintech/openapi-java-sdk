package com.tigerbrokers.stock.openapi.client.https.domain.trade.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.SegmentType;

public class DepositWithdrawModel extends ApiModel {

  /**
   * account id
   */
  private String account;

  @JSONField(name = "secret_key")
  private String secretKey;

  @JSONField(name = "seg_type")
  private SegmentType segType;

  public DepositWithdrawModel() {
  }

  @Override
  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public SegmentType getSegType() {
    return segType;
  }

  public void setSegType(SegmentType segType) {
    this.segType = segType;
  }

  @Override
  public String toString() {
    return "DepositWithdrawModel{" +
        "account='" + account + '\'' +
        ", secretKey='" + secretKey + '\'' +
        ", segType=" + segType +
        '}';
  }
}
