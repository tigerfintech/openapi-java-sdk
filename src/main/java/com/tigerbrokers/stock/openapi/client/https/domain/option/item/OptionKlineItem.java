package com.tigerbrokers.stock.openapi.client.https.domain.option.item;

import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.KlineItem;

/**
 * Description:
 * Created by lijiawen on 2019/01/07.
 */
public class OptionKlineItem extends KlineItem {

  /**
   * 股票代码
   */
  private String symbol;

  /**
   * 到期时间，毫秒,当天0点
   */
  private Long expiry;

  /**
   * 看多或看空  CALL  PUT
   */
  private String right;

  /**
   * 行权价
   */
  private String strike;

  /**
   * 未平仓量
   */
  private Integer openInterest;

  @Override
  public String getSymbol() {
    return symbol;
  }

  @Override
  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Long getExpiry() {
    return expiry;
  }

  public void setExpiry(Long expiry) {
    this.expiry = expiry;
  }

  public String getRight() {
    return right;
  }

  public void setRight(String right) {
    this.right = right;
  }

  public String getStrike() {
    return strike;
  }

  public void setStrike(String strike) {
    this.strike = strike;
  }

  public Integer getOpenInterest() {
    return openInterest;
  }

  public void setOpenInterest(Integer openInterest) {
    this.openInterest = openInterest;
  }

  @Override
  public String toString() {
    return "OptionKlineItem{" +
        "symbol='" + symbol + '\'' +
        ", expiry=" + expiry +
        ", right='" + right + '\'' +
        ", strike='" + strike + '\'' +
        ", openInterest=" + openInterest +
        '}';
  }
}
