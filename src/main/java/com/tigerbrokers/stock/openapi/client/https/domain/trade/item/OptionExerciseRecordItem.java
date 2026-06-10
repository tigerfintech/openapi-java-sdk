package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

public class OptionExerciseRecordItem {

  private Long id;
  private Long accountId;
  private Long contractId;
  private String symbol;
  private String stkSymbol;
  private String expireDate;
  private String strike;
  private String callPut;
  private String type;
  private Double requestQuantity;
  private Double quantity;
  private String status;
  private String executingDate;
  private Integer itmRate;
  private Boolean isForce;
  private String reason;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public Long getContractId() {
    return contractId;
  }

  public void setContractId(Long contractId) {
    this.contractId = contractId;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getStkSymbol() {
    return stkSymbol;
  }

  public void setStkSymbol(String stkSymbol) {
    this.stkSymbol = stkSymbol;
  }

  public String getExpireDate() {
    return expireDate;
  }

  public void setExpireDate(String expireDate) {
    this.expireDate = expireDate;
  }

  public String getStrike() {
    return strike;
  }

  public void setStrike(String strike) {
    this.strike = strike;
  }

  public String getCallPut() {
    return callPut;
  }

  public void setCallPut(String callPut) {
    this.callPut = callPut;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Double getRequestQuantity() {
    return requestQuantity;
  }

  public void setRequestQuantity(Double requestQuantity) {
    this.requestQuantity = requestQuantity;
  }

  public Double getQuantity() {
    return quantity;
  }

  public void setQuantity(Double quantity) {
    this.quantity = quantity;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getExecutingDate() {
    return executingDate;
  }

  public void setExecutingDate(String executingDate) {
    this.executingDate = executingDate;
  }

  public Integer getItmRate() {
    return itmRate;
  }

  public void setItmRate(Integer itmRate) {
    this.itmRate = itmRate;
  }

  public Boolean getIsForce() {
    return isForce;
  }

  public void setIsForce(Boolean isForce) {
    this.isForce = isForce;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  @Override
  public String toString() {
    return "OptionExerciseRecordItem{" +
        "id=" + id +
        ", accountId=" + accountId +
        ", contractId=" + contractId +
        ", symbol='" + symbol + '\'' +
        ", stkSymbol='" + stkSymbol + '\'' +
        ", expireDate='" + expireDate + '\'' +
        ", strike='" + strike + '\'' +
        ", callPut='" + callPut + '\'' +
        ", type='" + type + '\'' +
        ", requestQuantity=" + requestQuantity +
        ", quantity=" + quantity +
        ", status='" + status + '\'' +
        ", executingDate='" + executingDate + '\'' +
        ", itmRate=" + itmRate +
        ", isForce=" + isForce +
        ", reason='" + reason + '\'' +
        '}';
  }
}
