package com.tigerbrokers.stock.openapi.client.https.domain.option.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2019/01/07.
 */
public class OptionKlineItem extends ApiModel {

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
   * K线周期
   */
  private String period;

  /**
   * K线数组
   */
  private List<OptionKlinePoint> items;

  public String getSymbol() {
    return symbol;
  }

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

  public String getPeriod() {
    return period;
  }

  public void setPeriod(String period) {
    this.period = period;
  }

  public List<OptionKlinePoint> getItems() {
    return items;
  }

  public void setItems(List<OptionKlinePoint> items) {
    this.items = items;
  }

  @Override
  public String toString() {
    return "OptionKlineItem{" +
        "symbol='" + symbol + '\'' +
        ", expiry=" + expiry +
        ", right='" + right + '\'' +
        ", strike='" + strike + '\'' +
        ", period='" + period + '\'' +
        ", items=" + items +
        '}';
  }
}
