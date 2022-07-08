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
   * 分时数据数组
   */
  private List<TimelinePoint> items;

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

  @Override
  public String toString() {
    return "TimelineItem{" +
        "symbol='" + symbol + '\'' +
        ", items=" + items +
        '}';
  }
}
