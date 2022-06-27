package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;

/**
 * Description:
 * Created by bean on 2022/06/23.
 */
public class TradeCalendarModel extends ApiModel {

  private Market market;
  @JSONField(name = "begin_date")
  private String beginDate;
  @JSONField(name = "end_date")
  private String endDate;

  public Market getMarket() {
    return market;
  }

  public void setMarket(Market market) {
    this.market = market;
  }

  public String getBeginDate() {
    return beginDate;
  }

  public void setBeginDate(String beginDate) {
    this.beginDate = beginDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  @Override
  public String toString() {
    return "TradeCalendarModel{" +
        ", market=" + market +
        ", beginDate=" + beginDate +
        ", endDate=" + endDate +
        '}';
  }
}
