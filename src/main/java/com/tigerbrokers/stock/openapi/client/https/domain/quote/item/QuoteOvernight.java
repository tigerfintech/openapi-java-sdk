package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import java.io.Serializable;

/**
 * Description:
 * Created by bean on 2025/02/28.
 */
public class QuoteOvernight implements Serializable {

  /**
   * symbol
   */
  private String symbol;

  /**
   * latest price
   */
  private Double latestPrice;

  private Double askPrice;
  private Long askSize;
  private Double bidPrice;
  private Long bidSize;

  protected Double preClose;

  private Long volume;

  private Double amount;

  private Double change;

  private Double changeRate;

  private Double amplitude;

  private Long timestamp;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Double getLatestPrice() {
    return latestPrice;
  }

  public void setLatestPrice(Double latestPrice) {
    this.latestPrice = latestPrice;
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

  public Double getPreClose() {
    return preClose;
  }

  public void setPreClose(Double preClose) {
    this.preClose = preClose;
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

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return "QuoteOvernight{" +
        "symbol='" + symbol + '\'' +
        ", latestPrice=" + latestPrice +
        ", askPrice=" + askPrice +
        ", askSize=" + askSize +
        ", bidPrice=" + bidPrice +
        ", bidSize=" + bidSize +
        ", preClose=" + preClose +
        ", volume=" + volume +
        ", amount=" + amount +
        ", change=" + change +
        ", changeRate=" + changeRate +
        ", amplitude=" + amplitude +
        ", timestamp=" + timestamp +
        '}';
  }
}
