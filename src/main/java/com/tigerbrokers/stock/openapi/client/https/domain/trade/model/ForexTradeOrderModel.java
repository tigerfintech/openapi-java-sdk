package com.tigerbrokers.stock.openapi.client.https.domain.trade.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.SegmentType;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeInForce;

public class ForexTradeOrderModel extends ApiModel {

  /**
   * account id
   */
  private String account;

  @JSONField(name = "secret_key")
  private String secretKey;

  @JSONField(name = "source_currency")
  private Currency sourceCurrency;
  @JSONField(name = "source_amount")
  private Double sourceAmount;
  @JSONField(name = "target_currency")
  private Currency targetCurrency;
  @JSONField(name = "seg_type")
  private SegmentType segType;
  @JSONField(name = "external_id")
  private String externalId;

  /**
   * order validity time range, forex order only support 'DAY' and 'GTC', default is 'DAY'
   * DAY：valid for the day
   * GTC：valid until cancelled
   */
  @JSONField(name = "time_in_force")
  private TimeInForce timeInForce;

  public ForexTradeOrderModel() {
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

  public Currency getSourceCurrency() {
    return sourceCurrency;
  }

  public void setSourceCurrency(Currency sourceCurrency) {
    this.sourceCurrency = sourceCurrency;
  }

  public Double getSourceAmount() {
    return sourceAmount;
  }

  public void setSourceAmount(Double sourceAmount) {
    this.sourceAmount = sourceAmount;
  }

  public Currency getTargetCurrency() {
    return targetCurrency;
  }

  public void setTargetCurrency(Currency targetCurrency) {
    this.targetCurrency = targetCurrency;
  }

  public SegmentType getSegType() {
    return segType;
  }

  public void setSegType(SegmentType segType) {
    this.segType = segType;
  }

  public String getExternalId() {
    return externalId;
  }

  public void setExternalId(String externalId) {
    this.externalId = externalId;
  }

  public TimeInForce getTimeInForce() {
    return timeInForce;
  }

  public void setTimeInForce(TimeInForce timeInForce) {
    this.timeInForce = timeInForce;
  }

  @Override
  public String toString() {
    return "SegmentFundModel{" +
        ", account='" + account + '\'' +
        ", secretKey='" + secretKey + '\'' +
        ", source_currency='" + sourceCurrency + '\'' +
        ", source_amount='" + sourceAmount + '\'' +
        ", target_currency=" + targetCurrency +
        ", seg_type=" + segType +
        ", time_in_force=" + timeInForce +
        ", external_id=" + externalId +
        '}';
  }
}
