package com.tigerbrokers.stock.openapi.client.https.domain.fund.item;

import java.io.Serializable;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FundQuotePoint implements Serializable {

  private Double nav;

  private Long time;

  public Double getNav() {
    return nav;
  }

  public void setNav(Double nav) {
    this.nav = nav;
  }

  public Long getTime() {
    return time;
  }

  public void setTime(Long time) {
    this.time = time;
  }

  @Override
  public String toString() {
    return "FundQuotePoint{" +
        "nav=" + nav +
        ", time=" + time +
        '}';
  }
}
