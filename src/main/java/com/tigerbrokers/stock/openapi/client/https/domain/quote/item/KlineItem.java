package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class KlineItem extends ApiModel {

  /**
   * 股票代码
   */
  private String symbol;

  /**
   * K线周期
   */
  private String period;

  /**
   * Token that can be used to query the next page. Nullable
   */
  private String nextPageToken;

  /**
   * K线数组
   */
  private List<KlinePoint> items;

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

  public String getNextPageToken() {
    return nextPageToken;
  }

  public void setNextPageToken(String nextPageToken) {
    this.nextPageToken = nextPageToken;
  }

  public List<KlinePoint> getItems() {
    return items;
  }

  public void setItems(List<KlinePoint> items) {
    this.items = items;
  }

  @Override
  public String toString() {
    return "KlineItem{" +
        "symbol='" + symbol + '\'' +
        ", period='" + period + '\'' +
        ", nextPageToken=" + nextPageToken +
        ", items=" + items +
        '}';
  }
}
