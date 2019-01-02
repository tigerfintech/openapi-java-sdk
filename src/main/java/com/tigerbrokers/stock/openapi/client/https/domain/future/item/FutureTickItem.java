package com.tigerbrokers.stock.openapi.client.https.domain.future.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.math.BigDecimal;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureTickItem extends ApiModel {

  private long index;
  private BigDecimal price;
  private long volume;
  private long time;

  public long getIndex() {
    return index;
  }

  public void setIndex(long index) {
    this.index = index;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public long getVolume() {
    return volume;
  }

  public void setVolume(long volume) {
    this.volume = volume;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  @Override
  public String toString() {
    return "FutureTickItem{" +
        "index=" + index +
        ", price=" + price +
        ", volume=" + volume +
        ", time=" + time +
        '}';
  }
}
