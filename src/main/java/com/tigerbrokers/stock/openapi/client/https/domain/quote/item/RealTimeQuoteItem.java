package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.StockStatus;

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
   * 当日成交额
   */
  private Double amount;

  /**
   * 成交量（支持小数，用于数字货币）
   * 数字货币的成交量可能包含小数部分，使用此字段获取精确值
   * 例如：volumeDecimal = 123.456
   */
  private Double volumeDecimal;

  /**
   * 个股状态：
   * 0: 正常 3: 停牌 4: 退市 7: 新股 8: 变更
   */
  private StockStatus status;

  private Double change;

  private Double changeRate;

  private Double amplitude;

  private HourTrading hourTrading;

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

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Double getVolumeDecimal() {
    return volumeDecimal;
  }

  public void setVolumeDecimal(Double volumeDecimal) {
    this.volumeDecimal = volumeDecimal;
  }

  public StockStatus getStatus() {
    return status;
  }

  public void setStatus(StockStatus status) {
    this.status = status;
  }

  public Double getChange() {
    return change;
  }

  public void setChange(Double change) {
    this.change = change;
  }

  public Double getChangeRate() {
    return changeRate;
  }

  public void setChangeRate(Double changeRate) {
    this.changeRate = changeRate;
  }

  public Double getAmplitude() {
    return amplitude;
  }

  public void setAmplitude(Double amplitude) {
    this.amplitude = amplitude;
  }

  public HourTrading getHourTrading() {
    return hourTrading;
  }

  public void setHourTrading(HourTrading hourTrading) {
    this.hourTrading = hourTrading;
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
        ", amount=" + amount +
        ", volumeDecimal=" + volumeDecimal +
        ", status=" + status +
        ", change=" + change +
        ", changeRate=" + changeRate +
        ", amplitude=" + amplitude +
        ", hourTrading=" + hourTrading +
        '}';
  }
}
