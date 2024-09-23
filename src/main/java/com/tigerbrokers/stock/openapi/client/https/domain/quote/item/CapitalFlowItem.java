package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.Arrays;
import java.util.List;

/**
 * Description:
 * Created by bean on 2022/11/25.
 */
public class CapitalFlowItem extends ApiModel {

  /**
   * symbol
   */
  private String symbol;

  /**
   * period
   */
  private String period;

  private List<CapitalFlowPoint> items;


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

  public List<CapitalFlowPoint> getItems() {
    return items;
  }

  public void setItems(List<CapitalFlowPoint> items) {
    this.items = items;
  }

  @Override
  public String toString() {
    return "CapitalFlowItem{" +
        "symbol='" + symbol + '\'' +
        "period=" + period +
        ", items=" + items == null ? null : Arrays.toString(items.toArray()) +
        '}';
  }
}
