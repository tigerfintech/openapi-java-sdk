package com.tigerbrokers.stock.openapi.client.https.domain.future.item;

import java.math.BigDecimal;

public class FutureDepthAskBidItem {

  private BigDecimal price;
  private Long volume;

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Long getVolume() {
    return volume;
  }

  public void setVolume(Long volume) {
    this.volume = volume;
  }
}
