package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import java.io.Serializable;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class TickPoint implements Serializable {

  /**
   * 成交价
   */
  private Double price;

  /**
   * +表示涨，*表示不变，-表示跌
   */
  private String type;

  private Long time;

  private Long volume;

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
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
    return "TickPoint{" +
        "price=" + price +
        ", type='" + type + '\'' +
        ", time=" + time +
        ", volume=" + volume +
        '}';
  }
}
