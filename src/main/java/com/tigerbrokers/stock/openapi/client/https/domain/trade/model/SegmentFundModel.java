package com.tigerbrokers.stock.openapi.client.https.domain.trade.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

public class SegmentFundModel extends ApiModel {

  /**
   * segment fund transfer ID(for canceling request)
   */
  private Long id;

  /**
   * account id
   */
  private String account;

  @JSONField(name = "secret_key")
  private String secretKey;

  @JSONField(name = "from_segment")
  private String fromSegment;
  @JSONField(name = "to_segment")
  private String toSegment;
  /**
   * currency
   */
  private String currency;
  private Double amount;
  private Integer limit;

  public SegmentFundModel() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String getFromSegment() {
    return fromSegment;
  }

  public void setFromSegment(String fromSegment) {
    this.fromSegment = fromSegment;
  }

  public String getToSegment() {
    return toSegment;
  }

  public void setToSegment(String toSegment) {
    this.toSegment = toSegment;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  @Override
  public String toString() {
    return "SegmentFundModel{" +
        "id=" + id +
        ", account='" + account + '\'' +
        ", secretKey='" + secretKey + '\'' +
        ", fromSegment='" + fromSegment + '\'' +
        ", toSegment='" + toSegment + '\'' +
        ", currency=" + currency +
        ", amount=" + amount +
        ", limit=" + limit +
        '}';
  }
}
