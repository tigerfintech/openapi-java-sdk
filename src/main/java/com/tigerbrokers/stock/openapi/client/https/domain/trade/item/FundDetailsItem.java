package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

import java.io.Serializable;

/**
 * Created on 2025/4/24
 *
 * @author: sukai
 */
public class FundDetailsItem implements Serializable {
  private String id;
  private String currency;
  private String type;
  private String desc;
  private String contractName;
  private String segType;
  private Double amount;
  private String businessDate;
  private Long updatedAt;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getContractName() {
    return contractName;
  }

  public void setContractName(String contractName) {
    this.contractName = contractName;
  }

  public String getSegType() {
    return segType;
  }

  public void setSegType(String segType) {
    this.segType = segType;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public String getBusinessDate() {
    return businessDate;
  }

  public void setBusinessDate(String businessDate) {
    this.businessDate = businessDate;
  }

  public Long getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Long updatedAt) {
    this.updatedAt = updatedAt;
  }
}
