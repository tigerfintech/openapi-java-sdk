package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * author：bean date：2024/12/19
 */
public class StockFundamentalItem extends ApiModel {

  /**
   * Stock symbol
   */
  private String symbol;

  /**
   * Rate of return
   */
  private Double roe;

  /**
   * Price-to-book ratio
   */
  private Double roa;

  /**
   * Price-to-book ratio
   */
  private Double pbRate;

  /**
   * Price-to-sales ratio
   */
  private Double psRate;

  /**
   * Divide rate
   */
  private Double divideRate;

  /**
   * 52-week high
   */
  private Double week52High;

  /**
   * 52-week low
   */
  private Double week52Low;

  /**
   * Earnings per share (TTM)
   */
  private Double ttmEps;

  /**
   * Earnings per share (LYR, last year)
   */
  private Double lyrEps;

  /**
   * Volume ratio
   */
  private Double volumeRatio;

  /**
   * Turnover rate
   */
  private Double turnoverRate;

  /**
   * Price-to-earnings ratio (TTM)
   */
  private Double ttmPeRate;

  /**
   * Price-to-earnings ratio (LYR, last year)
   */
  private Double lyrPeRate;

  /**
   * Total market capitalization
   */
  private Double marketCap;

  /**
   * Free-float market capitalization
   */
  private Double floatMarketCap;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Double getRoe() {
    return roe;
  }

  public void setRoe(Double roe) {
    this.roe = roe;
  }

  public Double getPbRate() {
    return pbRate;
  }

  public void setPbRate(Double pbRate) {
    this.pbRate = pbRate;
  }

  public Double getDivideRate() {
    return divideRate;
  }

  public void setDivideRate(Double divideRate) {
    this.divideRate = divideRate;
  }

  public Double getRoa() {
    return roa;
  }

  public void setRoa(Double roa) {
    this.roa = roa;
  }

  public Double getPsRate() {
    return psRate;
  }

  public void setPsRate(Double psRate) {
    this.psRate = psRate;
  }

  public Double getWeek52High() {
    return week52High;
  }

  public void setWeek52High(Double week52High) {
    this.week52High = week52High;
  }

  public Double getWeek52Low() {
    return week52Low;
  }

  public void setWeek52Low(Double week52Low) {
    this.week52Low = week52Low;
  }

  public Double getTtmEps() {
    return ttmEps;
  }

  public void setTtmEps(Double ttmEps) {
    this.ttmEps = ttmEps;
  }

  public Double getLyrEps() {
    return lyrEps;
  }

  public void setLyrEps(Double lyrEps) {
    this.lyrEps = lyrEps;
  }

  public Double getVolumeRatio() {
    return volumeRatio;
  }

  public void setVolumeRatio(Double volumeRatio) {
    this.volumeRatio = volumeRatio;
  }

  public Double getTurnoverRate() {
    return turnoverRate;
  }

  public void setTurnoverRate(Double turnoverRate) {
    this.turnoverRate = turnoverRate;
  }

  public Double getTtmPeRate() {
    return ttmPeRate;
  }

  public void setTtmPeRate(Double ttmPeRate) {
    this.ttmPeRate = ttmPeRate;
  }

  public Double getLyrPeRate() {
    return lyrPeRate;
  }

  public void setLyrPeRate(Double lyrPeRate) {
    this.lyrPeRate = lyrPeRate;
  }

  public Double getMarketCap() {
    return marketCap;
  }

  public void setMarketCap(Double marketCap) {
    this.marketCap = marketCap;
  }

  public Double getFloatMarketCap() {
    return floatMarketCap;
  }

  public void setFloatMarketCap(Double floatMarketCap) {
    this.floatMarketCap = floatMarketCap;
  }
}
