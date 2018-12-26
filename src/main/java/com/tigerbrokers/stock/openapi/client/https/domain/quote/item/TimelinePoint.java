package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import java.io.Serializable;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class TimelinePoint implements Serializable {

  private Double price;

  private Double avgPrice;

  private Long time;

  private Long volume;

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Double getAvgPrice() {
    return avgPrice;
  }

  public void setAvgPrice(Double avgPrice) {
    this.avgPrice = avgPrice;
  }

  public Long getTime() {
    return time;
  }

  public void setTime(Long time) {
    this.time = time;
  }

  public Long getVolume() {
    return volume;
  }

  public void setVolume(Long volume) {
    this.volume = volume;
  }

  @Override
  public String toString() {
    return "TimelinePoint{" +
        "price=" + price +
        ", avgPrice=" + avgPrice +
        ", time=" + time +
        ", volume=" + volume +
        '}';
  }
}
