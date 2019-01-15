package com.tigerbrokers.stock.openapi.client.https.domain.option.item;

import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.KlinePoint;

/**
 * Description:
 * Created by lijiawen on 2019/01/15.
 */
public class OptionKlinePoint extends KlinePoint {

  /**
   * 未平仓量
   */
  private Integer openInterest;

  public Integer getOpenInterest() {
    return openInterest;
  }

  public void setOpenInterest(Integer openInterest) {
    this.openInterest = openInterest;
  }

  @Override
  public String toString() {
    return "OptionKlinePoint{" +
        "openInterest=" + openInterest +
        '}';
  }
}
