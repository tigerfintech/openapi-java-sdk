package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2022/04/12.
 */
public class HistoryTimelineItem extends ApiModel {

  /**
   * 股票代号
   */
  private String symbol;
  /**
   * 盘中分时数据数组
   */
  private List<TimelinePoint> items;
  /**
   * 盘前分时数据数组
   */
  private List<TimelinePoint> preMarket;
  /**
   * 盘后分时数据数组
   */
  private List<TimelinePoint> afterHours;
  /**
   * 夜盘分时数据数组
   */
  private List<TimelinePoint> overnight;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public List<TimelinePoint> getItems() {
    return items;
  }

  public void setItems(List<TimelinePoint> items) {
    this.items = items;
  }

  public List<TimelinePoint> getPreMarket() {
    return preMarket;
  }

  public void setPreMarket(
      List<TimelinePoint> preMarket) {
    this.preMarket = preMarket;
  }

  public List<TimelinePoint> getAfterHours() {
    return afterHours;
  }

  public void setAfterHours(
      List<TimelinePoint> afterHours) {
    this.afterHours = afterHours;
  }

  public List<TimelinePoint> getOvernight() {
    return overnight;
  }

  public void setOvernight(
      List<TimelinePoint> overnight) {
    this.overnight = overnight;
  }

  @Override
  public String toString() {
    return "HistoryTimelineItem{" +
        "symbol='" + symbol + '\'' +
        ", items=" + items +
        ", preMarket=" + preMarket +
        ", afterHours=" + afterHours +
        ", overnight=" + overnight +
        '}';
  }
}
