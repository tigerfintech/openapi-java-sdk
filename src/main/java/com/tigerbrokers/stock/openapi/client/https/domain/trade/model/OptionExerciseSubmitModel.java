package com.tigerbrokers.stock.openapi.client.https.domain.trade.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

public class OptionExerciseSubmitModel extends ApiModel {

  private String account;
  @JSONField(name = "contract_id")
  private Long contractId;
  private String type;
  private Double quantity;
  @JSONField(name = "executing_date")
  private String executingDate;
  @JSONField(name = "is_force")
  private Boolean isForce;
  @JSONField(name = "itm_rate")
  private Integer itmRate;
  @JSONField(name = "secret_key")
  private String secretKey;

  @Override
  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public Long getContractId() {
    return contractId;
  }

  public void setContractId(Long contractId) {
    this.contractId = contractId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Double getQuantity() {
    return quantity;
  }

  public void setQuantity(Double quantity) {
    this.quantity = quantity;
  }

  public String getExecutingDate() {
    return executingDate;
  }

  public void setExecutingDate(String executingDate) {
    this.executingDate = executingDate;
  }

  public Boolean getIsForce() {
    return isForce;
  }

  public void setIsForce(Boolean isForce) {
    this.isForce = isForce;
  }

  public Integer getItmRate() {
    return itmRate;
  }

  public void setItmRate(Integer itmRate) {
    this.itmRate = itmRate;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }
}
