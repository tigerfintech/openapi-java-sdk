package com.tigerbrokers.stock.openapi.client.https.domain.option.item;

import java.io.Serializable;

/**
 * Description: Implied volatility metric
 */
public class ImpliedVolMetric implements Serializable {

  private static final long serialVersionUID = 1L;

  private String period;
  private Double percentile;
  private Double rank;

  public String getPeriod() {
    return period;
  }

  public void setPeriod(String period) {
    this.period = period;
  }

  public Double getPercentile() {
    return percentile;
  }

  public void setPercentile(Double percentile) {
    this.percentile = percentile;
  }

  public Double getRank() {
    return rank;
  }

  public void setRank(Double rank) {
    this.rank = rank;
  }

  @Override
  public String toString() {
    return "ImpliedVolMetric{" +
        "period='" + period + '\'' +
        ", percentile=" + percentile +
        ", rank=" + rank +
        '}';
  }
}
