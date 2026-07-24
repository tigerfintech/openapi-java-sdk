package com.tigerbrokers.stock.openapi.client.https.domain.financial.item;

import java.time.LocalDate;

public class CorporateDelistingItem extends CorporateActionItem {

  private LocalDate announcedDate;
  private String reason;

  public LocalDate getAnnouncedDate() {
    return announcedDate;
  }

  public void setAnnouncedDate(LocalDate announcedDate) {
    this.announcedDate = announcedDate;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  @Override
  public String toString() {
    return "CorporateDelistingItem{" +
        "announcedDate=" + announcedDate +
        ", reason='" + reason + '\'' +
        ", symbol='" + getSymbol() + '\'' +
        ", market='" + getMarket() + '\'' +
        ", executeDate=" + getExecuteDate() +
        ", actionType=" + getActionType() +
        '}';
  }
}
