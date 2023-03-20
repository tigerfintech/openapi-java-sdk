package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

import java.io.Serializable;

/**
 * @author bean
 * @date 2023/3/20 2:03 PM
 */
public class SegFundItem implements Serializable {
  private static final long serialVersionUID = 1L;

  private long id;
  private String fromSegment;
  private String toSegment;
  private String currency;
  private Double amount;

  private String status;
  private String statusDesc;
  private String message;
  private Long settledAt;
  private Long updatedAt;
  private Long createdAt;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getStatusDesc() {
    return statusDesc;
  }

  public void setStatusDesc(String statusDesc) {
    this.statusDesc = statusDesc;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Long getSettledAt() {
    return settledAt;
  }

  public void setSettledAt(Long settledAt) {
    this.settledAt = settledAt;
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
