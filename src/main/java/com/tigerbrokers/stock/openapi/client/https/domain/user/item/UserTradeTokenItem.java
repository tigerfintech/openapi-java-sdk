package com.tigerbrokers.stock.openapi.client.https.domain.user.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by lijiawen on 2019/03/13.
 */
public class UserTradeTokenItem extends ApiModel {

  private String tradeToken;
  private long expiresIn;

  public String getTradeToken() {
    return tradeToken;
  }

  public void setTradeToken(String tradeToken) {
    this.tradeToken = tradeToken;
  }

  public long getExpiresIn() {
    return expiresIn;
  }

  public void setExpiresIn(long expiresIn) {
    this.expiresIn = expiresIn;
  }

  @Override
  public String toString() {
    return "UserTradeTokenItem{" +
        "tradeToken='" + tradeToken + '\'' +
        ", expiresIn=" + expiresIn +
        '}';
  }
}
