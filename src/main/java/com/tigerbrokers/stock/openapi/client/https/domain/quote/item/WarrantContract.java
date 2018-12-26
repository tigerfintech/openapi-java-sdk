package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import java.io.Serializable;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class WarrantContract extends ContractBase implements Serializable {

  /**
   * 期权方向（PUT/CALL)
   */
  private String right;

  public String getRight() {
    return right;
  }

  public void setRight(String right) {
    this.right = right;
  }

  @Override
  public String toString() {
    return "WarrantContract{" +
        "right='" + right + '\'' +
        ", symbol='" + symbol + '\'' +
        ", name='" + name + '\'' +
        ", exchange='" + exchange + '\'' +
        ", market='" + market + '\'' +
        ", secType='" + secType + '\'' +
        ", currency='" + currency + '\'' +
        ", expiry='" + expiry + '\'' +
        ", strike='" + strike + '\'' +
        ", multiplier=" + multiplier +
        '}';
  }
}
