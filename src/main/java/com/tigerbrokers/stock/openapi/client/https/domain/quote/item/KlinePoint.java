package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import java.io.Serializable;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class KlinePoint implements Serializable {

  private Double open;

  private Double close;

  private Double high;

  private Double low;

  private Long time;

  private Long volume;

  private Double volumeDouble;

  private Integer volumeScale;

  private Double amount;

  private Double turnoverRate;
  private Double ttmPe;
  private Double lyrPe;

  public Double getOpen() {
    return open;
  }

  public void setOpen(Double open) {
    this.open = open;
  }

  public Double getClose() {
    return close;
  }

  public void setClose(Double close) {
    this.close = close;
  }

  public Double getHigh() {
    return high;
  }

  public void setHigh(Double high) {
    this.high = high;
  }

  public Double getLow() {
    return low;
  }

  public void setLow(Double low) {
    this.low = low;
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

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Double getTurnoverRate() {
    return turnoverRate;
  }

  public void setTurnoverRate(Double turnoverRate) {
    this.turnoverRate = turnoverRate;
  }

  public Double getTtmPe() {
    return ttmPe;
  }

  public void setTtmPe(Double ttmPe) {
    this.ttmPe = ttmPe;
  }

  public Double getLyrPe() {
    return lyrPe;
  }

  public void setLyrPe(Double lyrPe) {
    this.lyrPe = lyrPe;
  }

  @Override
  public String toString() {
    return "KlinePoint{" +
        "open=" + open +
        ", close=" + close +
        ", high=" + high +
        ", low=" + low +
        ", time=" + time +
        ", volume=" + volume +
        ", volumeDouble=" + volumeDouble +
        ", volumeScale=" + volumeScale +
        ", amount=" + amount +
        ", turnoverRate=" + turnoverRate +
        ", ttmPe=" + ttmPe +
        ", lyrPe=" + lyrPe +
        '}';
  }
}
