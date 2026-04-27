package com.tigerbrokers.stock.openapi.client.https.domain.option.item;

import java.io.Serializable;

/**
 * Description: Volatility history item
 */
public class VolatilityItem implements Serializable {

  private static final long serialVersionUID = 1L;

  private Double impliedVol;
  private Double percentile;
  private Double rank;
  private Double hisVolatility;
  private Long timestamp;

  public Double getImpliedVol() {
    return impliedVol;
  }

  public void setImpliedVol(Double impliedVol) {
    this.impliedVol = impliedVol;
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

  public Double getHisVolatility() {
    return hisVolatility;
  }

  public void setHisVolatility(Double hisVolatility) {
    this.hisVolatility = hisVolatility;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return "VolatilityItem{" +
        "impliedVol=" + impliedVol +
        ", percentile=" + percentile +
        ", rank=" + rank +
        ", hisVolatility=" + hisVolatility +
        ", timestamp=" + timestamp +
        '}';
  }
}
