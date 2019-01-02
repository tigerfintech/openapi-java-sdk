package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class TimelineItem extends ApiModel {

  /**
   * 股票代号
   */
  private String symbol;

  /**
   * 分时 day ; 5日分时 5day
   */
  private String period;

  /**
   * 昨日收盘价
   */
  private Double preClose;

  /**
   * 分时数据,当天分时数据没有endTime
   */
  private List<TimelineRange> items;

  /**
   * 盘前交易分时数据（仅美股）
   */
  private TimelineRange preMarket;

  /**
   * 盘后交易分时数据（仅美股）
   */
  private TimelineRange afterHours;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getPeriod() {
    return period;
  }

  public void setPeriod(String period) {
    this.period = period;
  }

  public Double getPreClose() {
    return preClose;
  }

  public void setPreClose(Double preClose) {
    this.preClose = preClose;
  }

  public List<TimelineRange> getItems() {
    return items;
  }

  public void setItems(List<TimelineRange> items) {
    this.items = items;
  }

  public TimelineRange getPreMarket() {
    return preMarket;
  }

  public void setPreMarket(TimelineRange preMarket) {
    this.preMarket = preMarket;
  }

  public TimelineRange getAfterHours() {
    return afterHours;
  }

  public void setAfterHours(TimelineRange afterHours) {
    this.afterHours = afterHours;
  }

  @Override
  public String toString() {
    return "TimelineItem{" +
        "symbol='" + symbol + '\'' +
        ", period='" + period + '\'' +
        ", preClose=" + preClose +
        ", items=" + items +
        ", preMarket=" + preMarket +
        ", afterHours=" + afterHours +
        '}';
  }
}
