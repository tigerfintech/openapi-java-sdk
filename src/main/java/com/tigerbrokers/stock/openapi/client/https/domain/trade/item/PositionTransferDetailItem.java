package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

import java.util.List;

public class PositionTransferDetailItem {

  private Long id;
  private Long accountId;
  private Long counterpartyAccountId;
  private String method;
  private String direction;
  private String status;
  private String memo;
  private Long userId;
  private String userName;
  private Long finishedAt;
  private Long updatedAt;
  private Long createdAt;
  private List<TransferDetail> detail;

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

  public Long getCounterpartyAccountId() {
    return counterpartyAccountId;
  }

  public void setCounterpartyAccountId(Long counterpartyAccountId) {
    this.counterpartyAccountId = counterpartyAccountId;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
    this.direction = direction;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Long getFinishedAt() {
    return finishedAt;
  }

  public void setFinishedAt(Long finishedAt) {
    this.finishedAt = finishedAt;
  }

  public Long getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Long updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Long createdAt) {
    this.createdAt = createdAt;
  }

  public List<TransferDetail> getDetail() {
    return detail;
  }

  public void setDetail(List<TransferDetail> detail) {
    this.detail = detail;
  }

  public static class TransferDetail {

    private Long id;
    private Long transferId;
    private String direction;
    private Long contractId;
    private String symbol;
    private String formattedSymbol;
    private String market;
    private Double quantity;
    private String status;
    private String message;
    private Long updatedAt;
    private Long createdAt;

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public Long getTransferId() {
      return transferId;
    }

    public void setTransferId(Long transferId) {
      this.transferId = transferId;
    }

    public String getDirection() {
      return direction;
    }

    public void setDirection(String direction) {
      this.direction = direction;
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

    public String getFormattedSymbol() {
      return formattedSymbol;
    }

    public void setFormattedSymbol(String formattedSymbol) {
      this.formattedSymbol = formattedSymbol;
    }

    public String getMarket() {
      return market;
    }

    public void setMarket(String market) {
      this.market = market;
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

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    public Long getUpdatedAt() {
      return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
      this.updatedAt = updatedAt;
    }

    public Long getCreatedAt() {
      return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
      this.createdAt = createdAt;
    }
  }
}