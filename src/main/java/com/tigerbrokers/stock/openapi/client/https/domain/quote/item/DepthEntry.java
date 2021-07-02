package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import java.io.Serializable;

/**
 * 作者：ltc
 * 时间：2019/08/13
 */
public class DepthEntry implements Serializable {

  /**
   * 价格
   */
  private Double price;

  /**
   * 订单数量
   */
  private Integer count;

  /**
   * 股数
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
