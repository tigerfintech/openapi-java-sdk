package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by bean on 2022/06/23.
 */
public class TradeCalendar extends ApiModel {

  /**
   * trading day date，yyyy-MM-dd
   */
  private String date;

  /**
   * trading day type:NORMAL/EARLY_CLOSE
   */
  private String type;

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "TradeCalendar{" +
        "date='" + date + '\'' +
        ", type='" + type + '\'' +
        '}';
  }
}
