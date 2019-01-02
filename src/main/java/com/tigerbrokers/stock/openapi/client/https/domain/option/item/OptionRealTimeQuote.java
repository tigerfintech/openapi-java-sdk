package com.tigerbrokers.stock.openapi.client.https.domain.option.item;

import java.io.Serializable;

/**
 * Description:
 * Created by lijiawen on 2018/12/26.
 */
public class OptionRealTimeQuote implements Serializable {

  private String identifier;
  private String strike;
  private String right;
  private Double bidPrice;
  private Integer bidSize;
  private Double askPrice;
  private Integer askSize;
  private Integer volume;
  private Double latestPrice;
  private Double preClose;
  private Integer openInterest;
  private Integer multiplier;
  private Long lastTimestamp;

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getStrike() {
    return strike;
  }

  public void setStrike(String strike) {
    this.strike = strike;
  }

  public String getRight() {
    return right;
  }

  public void setRight(String right) {
    this.right = right;
  }

  public Double getBidPrice() {
    return bidPrice;
  }

  public void setBidPrice(Double bidPrice) {
    this.bidPrice = bidPrice;
  }

  public Integer getBidSize() {
    return bidSize;
  }

  public void setBidSize(Integer bidSize) {
    this.bidSize = bidSize;
  }

  public Double getAskPrice() {
    return askPrice;
  }

  public void setAskPrice(Double askPrice) {
    this.askPrice = askPrice;
  }

  public Integer getAskSize() {
    return askSize;
  }

  public void setAskSize(Integer askSize) {
    this.askSize = askSize;
  }

  public Integer getVolume() {
    return volume;
  }

  public void setVolume(Integer volume) {
    this.volume = volume;
  }

  public Double getLatestPrice() {
    return latestPrice;
  }

  public void setLatestPrice(Double latestPrice) {
    this.latestPrice = latestPrice;
  }

  public Double getPreClose() {
    return preClose;
  }

  public void setPreClose(Double preClose) {
    this.preClose = preClose;
  }

  public Integer getOpenInterest() {
    return openInterest;
  }

  public void setOpenInterest(Integer openInterest) {
    this.openInterest = openInterest;
  }

  public Integer getMultiplier() {
    return multiplier;
  }

  public void setMultiplier(Integer multiplier) {
    this.multiplier = multiplier;
  }

  public Long getLastTimestamp() {
    return lastTimestamp;
  }

  public void setLastTimestamp(Long lastTimestamp) {
    this.lastTimestamp = lastTimestamp;
  }

  @Override
  public String toString() {
    return "OptionRealTimeQuote{" +
        "identifier='" + identifier + '\'' +
        ", strike='" + strike + '\'' +
        ", right='" + right + '\'' +
        ", bidPrice=" + bidPrice +
        ", bidSize=" + bidSize +
        ", askPrice=" + askPrice +
        ", askSize=" + askSize +
        ", volume=" + volume +
        ", latestPrice=" + latestPrice +
        ", preClose=" + preClose +
        ", openInterest=" + openInterest +
        ", multiplier=" + multiplier +
        ", lastTimestamp=" + lastTimestamp +
        '}';
  }
}
