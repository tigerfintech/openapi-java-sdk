package com.tigerbrokers.stock.openapi.client.https.domain.option.item;

import java.io.Serializable;

/**
 * Description:
 * Created by liutongping on 2024/05/23.
 */
public class OptionDepthOrderBook implements Serializable {

  private Float price;
  private String code;
  private Long timestamp;
  private Integer volume;
  private Integer count;

  public Float getPrice() {
    return price;
  }

  public void setPrice(Float price) {
    this.price = price;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public Integer getVolume() {
    return volume;
  }

  public void setVolume(Integer volume) {
    this.volume = volume;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }
}
