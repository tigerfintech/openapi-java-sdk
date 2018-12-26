package com.tigerbrokers.stock.openapi.client.https.domain.option.item;

import java.io.Serializable;

/**
 * Description:
 * Created by lijiawen on 2018/12/26.
 */
public class TradeTickPoint implements Serializable {

  private Double price;
  private Long time;
  private Long volume;

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
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
    return "TradeTickPoint{" +
        "price=" + price +
        ", time=" + time +
        ", volume=" + volume +
        '}';
  }
}
