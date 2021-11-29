package com.tigerbrokers.stock.openapi.client.https.domain.option.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.util.DateUtils;

/**
 * Description:
 * Created by lijiawen on 2018/12/26.
 */
public class OptionBriefItem extends ApiModel {

  private String identifier;
  private String symbol;
  private String strike;
  private Double bidPrice;
  private Integer bidSize;
  private Double askPrice;
  private Integer askSize;
  private Double latestPrice;
  private Integer volume;
  private Double high;
  private Double low;
  private Double open;
  private Double preClose;
  private Integer openInterest;
  private Double change;
  private Integer multiplier;
  private String right;
  private String volatility;
  private Long expiry;
  protected Double ratesBonds;
  private Long timestamp;
  private String latestTime;

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getStrike() {
    return strike;
  }

  public void setStrike(String strike) {
    this.strike = strike;
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

  public Double getLatestPrice() {
    return latestPrice;
  }

  public void setLatestPrice(Double latestPrice) {
    this.latestPrice = latestPrice;
  }

  public Integer getVolume() {
    return volume;
  }

  public void setVolume(Integer volume) {
    this.volume = volume;
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

  public Double getOpen() {
    return open;
  }

  public void setOpen(Double open) {
    this.open = open;
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

  public Double getChange() {
    return change;
  }

  public void setChange(Double change) {
    this.change = change;
  }

  public Integer getMultiplier() {
    return multiplier;
  }

  public void setMultiplier(Integer multiplier) {
    this.multiplier = multiplier;
  }

  public String getRight() {
    return right;
  }

  public void setRight(String right) {
    this.right = right;
  }

  public String getVolatility() {
    return volatility;
  }

  public void setVolatility(String volatility) {
    this.volatility = volatility;
  }

  public Long getExpiry() {
    return expiry;
  }

  public void setExpiry(Long expiry) {
    this.expiry = expiry;
  }

  public Double getRatesBonds() {
    return ratesBonds;
  }

  public void setRatesBonds(Double ratesBonds) {
    this.ratesBonds = ratesBonds;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public void setLatestTime(String latestTime) {
    this.latestTime = latestTime;
  }

  public String getLatestTime() {
    if (this.latestTime == null && timestamp != null) {
      this.latestTime = DateUtils.printTimeZoneET(timestamp);
    }
    return this.latestTime;
  }

  @Override
  public String toString() {
    return "OptionBriefItem{" +
        "identifier='" + identifier + '\'' +
        ", symbol='" + symbol + '\'' +
        ", strike='" + strike + '\'' +
        ", bidPrice=" + bidPrice +
        ", bidSize=" + bidSize +
        ", askPrice=" + askPrice +
        ", askSize=" + askSize +
        ", latestPrice=" + latestPrice +
        ", volume=" + volume +
        ", high=" + high +
        ", low=" + low +
        ", open=" + open +
        ", preClose=" + preClose +
        ", openInterest=" + openInterest +
        ", change=" + change +
        ", multiplier=" + multiplier +
        ", right='" + right + '\'' +
        ", volatility='" + volatility + '\'' +
        ", expiry=" + expiry +
        ", ratesBonds=" + ratesBonds +
        ", timestamp=" + timestamp +
        ", latestTime=" + getLatestTime() +
        '}';
  }
}
