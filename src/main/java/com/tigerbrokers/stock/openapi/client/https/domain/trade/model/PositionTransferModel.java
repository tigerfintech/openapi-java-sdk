package com.tigerbrokers.stock.openapi.client.https.domain.trade.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

public class PositionTransferModel extends ApiModel {

  @JSONField(name = "from_account")
  private String fromAccount;
  @JSONField(name = "to_account")
  private String toAccount;
  private List<Transfer> transfers;
  private String market;
  private String comment;
  @JSONField(name = "secret_key")
  private String secretKey;

  public PositionTransferModel(String fromAccount, String toAccount, List<Transfer> transfers,
      String market) {
    this.fromAccount = fromAccount;
    this.toAccount = toAccount;
    this.transfers = transfers;
    this.market = market;
  }

  public String getFromAccount() {
    return fromAccount;
  }

  public void setFromAccount(String fromAccount) {
    this.fromAccount = fromAccount;
  }

  public String getToAccount() {
    return toAccount;
  }

  public void setToAccount(String toAccount) {
    this.toAccount = toAccount;
  }

  public List<Transfer> getTransfers() {
    return transfers;
  }

  public void setTransfers(
      List<Transfer> transfers) {
    this.transfers = transfers;
  }

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public static class Transfer {

    private String symbol;
    private String expiry;
    private String strike;
    private String right;
    private Long quantity;
    @JSONField(name = "sec_type")
    private String secType;

    public Transfer() {
    }

    public Transfer(String symbol, Long quantity) {
      this.symbol = symbol;
      this.quantity = quantity;
    }

    public String getSymbol() {
      return symbol;
    }

    public void setSymbol(String symbol) {
      this.symbol = symbol;
    }

    public String getExpiry() {
      return expiry;
    }

    public void setExpiry(String expiry) {
      this.expiry = expiry;
    }

    public String getStrike() {
      return strike;
    }

    public void setStrike(String strike) {
      this.strike = strike;
    }

    public String getRight() {
      return right;
    }

    public void setRight(String right) {
      this.right = right;
    }

    public Long getQuantity() {
      return quantity;
    }

    public void setQuantity(Long quantity) {
      this.quantity = quantity;
    }

    public String getSecType() {
      return secType;
    }

    public void setSecType(String secType) {
      this.secType = secType;
    }
  }
}
