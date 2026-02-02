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

  private Double volumeDouble;

  private Integer volumeScale;

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

  public Double getVolumeDouble() {
    return volumeDouble;
  }

  public void setVolumeDouble(Double volumeDouble) {
    this.volumeDouble = volumeDouble;
  }

  public Integer getVolumeScale() {
    return volumeScale;
  }

  public void setVolumeScale(Integer volumeScale) {
    this.volumeScale = volumeScale;
  }

  @Override
  public String toString() {
    return "TimelinePoint{" +
        "price=" + price +
        ", avgPrice=" + avgPrice +
        ", time=" + time +
        ", volume=" + volume +
        ", volumeDouble=" + volumeDouble +
        ", volumeScale=" + volumeScale +
        '}';
  }
}
