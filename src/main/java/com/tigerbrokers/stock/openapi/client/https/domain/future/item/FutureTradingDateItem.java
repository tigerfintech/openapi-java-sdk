package com.tigerbrokers.stock.openapi.client.https.domain.future.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureTradingDateItem extends ApiModel {

  List<TimeSection> biddingTimes;
  List<TimeSection> tradingTimes;
  String timeSection;

  public List<TimeSection> getBiddingTimes() {
    return biddingTimes;
  }

  public void setBiddingTimes(List<TimeSection> biddingTimes) {
    this.biddingTimes = biddingTimes;
  }

  public List<TimeSection> getTradingTimes() {
    return tradingTimes;
  }

  public void setTradingTimes(List<TimeSection> tradingTimes) {
    this.tradingTimes = tradingTimes;
  }

  public String getTimeSection() {
    return timeSection;
  }

  public void setTimeSection(String timeSection) {
    this.timeSection = timeSection;
  }

  @Override
  public String toString() {
    return "FutureTradingDateItem{" +
        "biddingTimes=" + biddingTimes +
        ", tradingTimes=" + tradingTimes +
        ", timeSection='" + timeSection + '\'' +
        '}';
  }
}
