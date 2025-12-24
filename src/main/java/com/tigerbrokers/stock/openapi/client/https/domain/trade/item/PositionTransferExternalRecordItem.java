package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

import java.util.List;

public class PositionTransferExternalRecordItem {

  private Long id;
  private String status;
  private Boolean allFinished;
  private Boolean counterpartyContacted;
  private Long accountId;
  private String transferMethod;
  private List<TransferPropertyInfo> transferPropertyInfos;
  private String institutionName;
  private String institutionType;
  private String remoteClearingBroker;
  private String dtcNumber;
  private String remoteUserName;
  private String remoteAccount;
  private String contactName;
  private String contactEmail;
  private String contactPhone;
  private Boolean cancelable;
  private Long createdAt;
  private Long updatedAt;
  private String side;
  private Long institutionId;
  private String market;
  private String userName;
  private String transferHin;
  private String fullPortfolio;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Boolean getAllFinished() {
    return allFinished;
  }

  public void setAllFinished(Boolean allFinished) {
    this.allFinished = allFinished;
  }

  public Boolean getCounterpartyContacted() {
    return counterpartyContacted;
  }

  public void setCounterpartyContacted(Boolean counterpartyContacted) {
    this.counterpartyContacted = counterpartyContacted;
  }

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public String getTransferMethod() {
    return transferMethod;
  }

  public void setTransferMethod(String transferMethod) {
    this.transferMethod = transferMethod;
  }

  public List<TransferPropertyInfo> getTransferPropertyInfos() {
    return transferPropertyInfos;
  }

  public void setTransferPropertyInfos(
      List<TransferPropertyInfo> transferPropertyInfos) {
    this.transferPropertyInfos = transferPropertyInfos;
  }

  public String getInstitutionName() {
    return institutionName;
  }

  public void setInstitutionName(String institutionName) {
    this.institutionName = institutionName;
  }

  public String getInstitutionType() {
    return institutionType;
  }

  public void setInstitutionType(String institutionType) {
    this.institutionType = institutionType;
  }

  public String getRemoteClearingBroker() {
    return remoteClearingBroker;
  }

  public void setRemoteClearingBroker(String remoteClearingBroker) {
    this.remoteClearingBroker = remoteClearingBroker;
  }

  public String getDtcNumber() {
    return dtcNumber;
  }

  public void setDtcNumber(String dtcNumber) {
    this.dtcNumber = dtcNumber;
  }

  public String getRemoteUserName() {
    return remoteUserName;
  }

  public void setRemoteUserName(String remoteUserName) {
    this.remoteUserName = remoteUserName;
  }

  public String getRemoteAccount() {
    return remoteAccount;
  }

  public void setRemoteAccount(String remoteAccount) {
    this.remoteAccount = remoteAccount;
  }

  public String getContactName() {
    return contactName;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  public String getContactEmail() {
    return contactEmail;
  }

  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }

  public String getContactPhone() {
    return contactPhone;
  }

  public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone;
  }

  public Boolean getCancelable() {
    return cancelable;
  }

  public void setCancelable(Boolean cancelable) {
    this.cancelable = cancelable;
  }

  public Long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Long createdAt) {
    this.createdAt = createdAt;
  }

  public Long getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Long updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getSide() {
    return side;
  }

  public void setSide(String side) {
    this.side = side;
  }

  public Long getInstitutionId() {
    return institutionId;
  }

  public void setInstitutionId(Long institutionId) {
    this.institutionId = institutionId;
  }

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getTransferHin() {
    return transferHin;
  }

  public void setTransferHin(String transferHin) {
    this.transferHin = transferHin;
  }

  public String getFullPortfolio() {
    return fullPortfolio;
  }

  public void setFullPortfolio(String fullPortfolio) {
    this.fullPortfolio = fullPortfolio;
  }

  public static class TransferPropertyInfo {

    private Long id;
    private String symbol;
    private String market;
    private String secType;
    private String stockName;
    private Double quantity;
    private Double averageCost;
    private String status;
    private Boolean cancelable;
    private Long updatedAt;

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public String getSymbol() {
      return symbol;
    }

    public void setSymbol(String symbol) {
      this.symbol = symbol;
    }

    public String getMarket() {
      return market;
    }

    public void setMarket(String market) {
      this.market = market;
    }

    public String getSecType() {
      return secType;
    }

    public void setSecType(String secType) {
      this.secType = secType;
    }

    public String getStockName() {
      return stockName;
    }

    public void setStockName(String stockName) {
      this.stockName = stockName;
    }

    public Double getQuantity() {
      return quantity;
    }

    public void setQuantity(Double quantity) {
      this.quantity = quantity;
    }

    public Double getAverageCost() {
      return averageCost;
    }

    public void setAverageCost(Double averageCost) {
      this.averageCost = averageCost;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public Boolean getCancelable() {
      return cancelable;
    }

    public void setCancelable(Boolean cancelable) {
      this.cancelable = cancelable;
    }

    public Long getUpdatedAt() {
      return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
      this.updatedAt = updatedAt;
    }
  }
}