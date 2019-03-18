package com.tigerbrokers.stock.openapi.client.https.domain.financial.item;

import java.time.LocalDate;

/**
 * Description:
 * Created by lijiawen on 2019/02/28.
 */
public class CorporateDividendItem extends CorporateActionItem {

  private LocalDate recordDate;

  private LocalDate announcedDate;

  private LocalDate payDate;

  private Double amount;

  private String currency;

  private String type;

  public LocalDate getRecordDate() {
    return recordDate;
  }

  public void setRecordDate(LocalDate recordDate) {
    this.recordDate = recordDate;
  }

  public LocalDate getAnnouncedDate() {
    return announcedDate;
  }

  public void setAnnouncedDate(LocalDate announcedDate) {
    this.announcedDate = announcedDate;
  }

  public LocalDate getPayDate() {
    return payDate;
  }

  public void setPayDate(LocalDate payDate) {
    this.payDate = payDate;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
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

  @Override
  public String toString() {
    return "CorporateDividendItem{" +
        "recordDate=" + recordDate +
        ", announcedDate=" + announcedDate +
        ", payDate=" + payDate +
        ", amount=" + amount +
        ", currency='" + currency + '\'' +
        ", type='" + type + '\'' +
        '}';
  }
}
