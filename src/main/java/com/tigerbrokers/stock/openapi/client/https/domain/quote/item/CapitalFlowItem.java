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

  private List<CapitalFlowPoint> points;


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

  public List<CapitalFlowPoint> getPoints() {
    return points;
  }

  public void setPoints(List<CapitalFlowPoint> points) {
    this.points = points;
  }

  @Override
  public String toString() {
    return "CapitalFlowItem{" +
        "symbol='" + symbol + '\'' +
        "period=" + period +
        ", points=" + points == null ? null : Arrays.toString(points.toArray()) +
        '}';
  }
}
