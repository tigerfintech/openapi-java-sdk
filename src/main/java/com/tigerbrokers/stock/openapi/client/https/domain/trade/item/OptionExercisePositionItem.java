package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

public class OptionExercisePositionItem {

  private String market;
  private Long contractId;
  private String stkSymbol;
  private String symbol;
  private String expireDate;
  private String strike;
  private String callPut;
  private Long accountId;
  private Double position;
  private Double availableQuantity;

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public Long getContractId() {
    return contractId;
  }

  public void setContractId(Long contractId) {
    this.contractId = contractId;
  }

  public String getStkSymbol() {
    return stkSymbol;
  }

  public void setStkSymbol(String stkSymbol) {
    this.stkSymbol = stkSymbol;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
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

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public Double getPosition() {
    return position;
  }

  public void setPosition(Double position) {
    this.position = position;
  }

  public Double getAvailableQuantity() {
    return availableQuantity;
  }

  public void setAvailableQuantity(Double availableQuantity) {
    this.availableQuantity = availableQuantity;
  }

  @Override
  public String toString() {
    return "OptionExercisePositionItem{" +
        "market='" + market + '\'' +
        ", contractId=" + contractId +
        ", stkSymbol='" + stkSymbol + '\'' +
        ", symbol='" + symbol + '\'' +
        ", expireDate='" + expireDate + '\'' +
        ", strike='" + strike + '\'' +
        ", callPut='" + callPut + '\'' +
        ", accountId=" + accountId +
        ", position=" + position +
        ", availableQuantity=" + availableQuantity +
        '}';
  }
}
