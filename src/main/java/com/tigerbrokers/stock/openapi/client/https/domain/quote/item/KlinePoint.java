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

  /**
   * Fractional volume, when supplied for assets such as cryptocurrencies.
   */
  private Double volumeDecimal;

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

  /**
   * Returns the fractional volume, or {@code null} when it is not supplied.
   */
  public Double getVolumeDecimal() {
    return volumeDecimal;
  }

  public void setVolumeDecimal(Double volumeDecimal) {
    this.volumeDecimal = volumeDecimal;
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
        ", volumeDecimal=" + volumeDecimal +
        ", amount=" + amount +
        ", turnoverRate=" + turnoverRate +
        ", ttmPe=" + ttmPe +
        ", lyrPe=" + lyrPe +
        '}';
  }
}
