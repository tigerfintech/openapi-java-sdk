package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import java.io.Serializable;

/**
 * Description:
 * Created by bean on 2022/11/25.
 */
public class CapitalFlowPoint implements Serializable {

  private String time;

  private Long timestamp;

  /**
   * net inflow
   */
  private Double netInflow;

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public Double getNetInflow() {
    return netInflow;
  }

  public void setNetInflow(Double netInflow) {
    this.netInflow = netInflow;
  }

  @Override
  public String toString() {
    return "CapitalFlowPoint{" +
        "time=" + time +
        ", timestamp=" + timestamp +
        ", netInflow=" + netInflow +
        '}';
  }
}
