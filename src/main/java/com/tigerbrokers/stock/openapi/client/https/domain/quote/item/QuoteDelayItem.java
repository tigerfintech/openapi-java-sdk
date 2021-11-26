package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by sk on 2021/11/18.
 */
public class QuoteDelayItem extends ApiModel {

  /**
   * 股票代码
   */
  private String symbol;

  /**
   * 开盘价
   */
  private Double open;

  /**
   * 最高价
   */
  private Double high;

  /**
   * 最低价
   */
  private Double low;

  /**
   * 收盘价
   */
  private Double close;

  /**
   * 昨日收盘价
   */
  private Double preClose;

  /**
   * 个股状态 (0: 正常 3: 停牌 4: 退市 7: 新股 8: 变更)
   */
  private Double halted;

  /**
   * 最后的行情时间
   */
  private Long time;

  /**
   * 当日成交量
   */
  private Long volume;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Double getOpen() {
    return open;
  }

  public void setOpen(Double open) {
    this.open = open;
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

  public Double getClose() {
    return close;
  }

  public void setClose(Double close) {
    this.close = close;
  }

  public Double getPreClose() {
    return preClose;
  }

  public void setPreClose(Double preClose) {
    this.preClose = preClose;
  }

  public Double getHalted() {
    return halted;
  }

  public void setHalted(Double halted) {
    this.halted = halted;
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
}
