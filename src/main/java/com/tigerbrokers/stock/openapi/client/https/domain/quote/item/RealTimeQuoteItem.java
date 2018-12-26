package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class RealTimeQuoteItem extends ApiModel {

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
   * 最新价
   */
  private Double latestPrice;

  /**
   * 最新成交时间
   */
  private Long latestTime;

  /**
   * 卖盘价
   */
  private Double askPrice;

  /**
   * 卖盘数量
   */
  private Long askSize;

  /**
   * 买盘价
   */
  private Double bidPrice;

  /**
   * 买盘数量
   */
  private Long bidSize;

  /**
   * 当日成交量
   */
  private Long volume;

  /**
   * 个股状态：
   * 0: 正常 3: 停牌 4: 退市 7: 新股 8: 变更
   */
  private Double status;

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

  public Double getLatestPrice() {
    return latestPrice;
  }

  public void setLatestPrice(Double latestPrice) {
    this.latestPrice = latestPrice;
  }

  public Long getLatestTime() {
    return latestTime;
  }

  public void setLatestTime(Long latestTime) {
    this.latestTime = latestTime;
  }

  public Double getAskPrice() {
    return askPrice;
  }

  public void setAskPrice(Double askPrice) {
    this.askPrice = askPrice;
  }

  public Long getAskSize() {
    return askSize;
  }

  public void setAskSize(Long askSize) {
    this.askSize = askSize;
  }

  public Double getBidPrice() {
    return bidPrice;
  }

  public void setBidPrice(Double bidPrice) {
    this.bidPrice = bidPrice;
  }

  public Long getBidSize() {
    return bidSize;
  }

  public void setBidSize(Long bidSize) {
    this.bidSize = bidSize;
  }

  public Long getVolume() {
    return volume;
  }

  public void setVolume(Long volume) {
    this.volume = volume;
  }

  public Double getStatus() {
    return status;
  }

  public void setStatus(Double status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "RealTimeQuoteItem{" +
        "symbol='" + symbol + '\'' +
        ", open=" + open +
        ", high=" + high +
        ", low=" + low +
        ", close=" + close +
        ", preClose=" + preClose +
        ", latestPrice=" + latestPrice +
        ", latestTime=" + latestTime +
        ", askPrice=" + askPrice +
        ", askSize=" + askSize +
        ", bidPrice=" + bidPrice +
        ", bidSize=" + bidSize +
        ", volume=" + volume +
        ", status=" + status +
        '}';
  }
}
