package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import java.io.Serializable;

/**
 * author：ltc
 * date：2019/08/13
 */
public class DepthEntry implements Serializable {

  /**
   * price
   */
  private Double price;

  /**
   * quantity of order
   */
  private Integer count;

  /**
   * quantity of symbol
   */
  private Integer volume;

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public Integer getVolume() {
    return volume;
  }

  public void setVolume(Integer volume) {
    this.volume = volume;
  }

  @Override
  public String toString() {
    return "DepthEntry{" +
        "price=" + price +
        ", count=" + count +
        ", volume=" + volume +
        '}';
  }
}
