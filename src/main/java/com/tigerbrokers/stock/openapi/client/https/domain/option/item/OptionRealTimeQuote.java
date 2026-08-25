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
  private Double impliedVol;
  private Double delta;
  private Double gamma;
  private Double theta;
  private Double vega;
  private Double rho;
  private Double markPrice;
  private Double preMarkPrice;
  private Long markTimestamp;
  private Double midPrice;
  private Double preMidPrice;
  private Long midTimestamp;

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

  public Double getImpliedVol() {
    return impliedVol;
  }

  public void setImpliedVol(Double impliedVol) {
    this.impliedVol = impliedVol;
  }

  public Double getDelta() {
    return delta;
  }

  public void setDelta(Double delta) {
    this.delta = delta;
  }

  public Double getGamma() {
    return gamma;
  }

  public void setGamma(Double gamma) {
    this.gamma = gamma;
  }

  public Double getTheta() {
    return theta;
  }

  public void setTheta(Double theta) {
    this.theta = theta;
  }

  public Double getVega() {
    return vega;
  }

  public void setVega(Double vega) {
    this.vega = vega;
  }

  public Double getRho() {
    return rho;
  }

  public void setRho(Double rho) {
    this.rho = rho;
  }

  public Double getMarkPrice() {
    return markPrice;
  }

  public void setMarkPrice(Double markPrice) {
    this.markPrice = markPrice;
  }

  public Double getPreMarkPrice() {
    return preMarkPrice;
  }

  public void setPreMarkPrice(Double preMarkPrice) {
    this.preMarkPrice = preMarkPrice;
  }

  public Long getMarkTimestamp() {
    return markTimestamp;
  }

  public void setMarkTimestamp(Long markTimestamp) {
    this.markTimestamp = markTimestamp;
  }

  public Double getMidPrice() {
    return midPrice;
  }

  public void setMidPrice(Double midPrice) {
    this.midPrice = midPrice;
  }

  public Double getPreMidPrice() {
    return preMidPrice;
  }

  public void setPreMidPrice(Double preMidPrice) {
    this.preMidPrice = preMidPrice;
  }

  public Long getMidTimestamp() {
    return midTimestamp;
  }

  public void setMidTimestamp(Long midTimestamp) {
    this.midTimestamp = midTimestamp;
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
        ", impliedVol=" + impliedVol +
        ", delta=" + delta +
        ", gamma=" + gamma +
        ", theta=" + theta +
        ", vega=" + vega +
        ", rho=" + rho +
        ", markPrice=" + markPrice +
        ", preMarkPrice=" + preMarkPrice +
        ", markTimestamp=" + markTimestamp +
        ", midPrice=" + midPrice +
        ", preMidPrice=" + preMidPrice +
        ", midTimestamp=" + midTimestamp +
        '}';
  }
}
