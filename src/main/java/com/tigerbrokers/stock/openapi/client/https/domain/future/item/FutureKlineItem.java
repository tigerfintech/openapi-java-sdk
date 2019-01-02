package com.tigerbrokers.stock.openapi.client.https.domain.future.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.math.BigDecimal;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureKlineItem extends ApiModel {

  private long time;
  private long lastTime;
  private BigDecimal open;
  private BigDecimal close;
  private BigDecimal high;
  private BigDecimal low;
  private long volume;
  private long openInterest;
  private BigDecimal settlement;

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public long getLastTime() {
    return lastTime;
  }

  public void setLastTime(long lastTime) {
    this.lastTime = lastTime;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public void setOpen(BigDecimal open) {
    this.open = open;
  }

  public BigDecimal getClose() {
    return close;
  }

  public void setClose(BigDecimal close) {
    this.close = close;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public void setHigh(BigDecimal high) {
    this.high = high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public void setLow(BigDecimal low) {
    this.low = low;
  }

  public long getVolume() {
    return volume;
  }

  public void setVolume(long volume) {
    this.volume = volume;
  }

  public long getOpenInterest() {
    return openInterest;
  }

  public void setOpenInterest(long openInterest) {
    this.openInterest = openInterest;
  }

  public BigDecimal getSettlement() {
    return settlement;
  }

  public void setSettlement(BigDecimal settlement) {
    this.settlement = settlement;
  }

  @Override
  public String toString() {
    return "FutureKlineItem{" +
        "time=" + time +
        ", lastTime=" + lastTime +
        ", open=" + open +
        ", close=" + close +
        ", high=" + high +
        ", low=" + low +
        ", volume=" + volume +
        ", openInterest=" + openInterest +
        ", settlement=" + settlement +
        '}';
  }
}
