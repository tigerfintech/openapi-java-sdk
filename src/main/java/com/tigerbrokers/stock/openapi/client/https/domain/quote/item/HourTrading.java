package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import java.io.Serializable;

/**
 * Description:
 * Created by lijiawen on 2018/12/28.
 */
public class HourTrading implements Serializable {

  /**
   * pre-market、after hours
   */
  private String tag;

  protected Double latestPrice;

  protected Double preClose;

  private String latestTime;

  private Long volume;

  private Long timestamp;

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public Double getLatestPrice() {
    return latestPrice;
  }

  public void setLatestPrice(Double latestPrice) {
    this.latestPrice = latestPrice;
  }

  public Double getPreClose() {
    return preClose;
  }

  public void setPreClose(Double preClose) {
    this.preClose = preClose;
  }

  public String getLatestTime() {
    return latestTime;
  }

  public void setLatestTime(String latestTime) {
    this.latestTime = latestTime;
  }

  public Long getVolume() {
    return volume;
  }

  public void setVolume(Long volume) {
    this.volume = volume;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return "HourTrading{" +
        "tag='" + tag + '\'' +
        ", latestPrice=" + latestPrice +
        ", preClose=" + preClose +
        ", latestTime='" + latestTime + '\'' +
        ", volume=" + volume +
        ", timestamp=" + timestamp +
        '}';
  }
}
