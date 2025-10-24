package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * author：bean date：2024/12/19
 */
public class StockFundamentalItem extends ApiModel {

  /**
   * 股票代码
   */
  private String symbol;

  /**
   * 净资产收益率
   */
  private Double roe;

  /**
   * 资产收益率
   */
  private Double roa;

  /**
   * 市净率
   */
  private Double pbRate;

  /**
   * 市销率
   */
  private Double psRate;

  /**
   * 股息收益率TTM
   */
  private Double divideRate;

  /**
   * 52周最高
   */
  private Double week52High;

  /**
   * 52周最低
   */
  private Double week52Low;

  /**
   * 每股收益(TTM)
   */
  private Double ttmEps;

  /**
   * 每股静态收益(LYR)
   */
  private Double lyrEps;

  /**
   * 量比
   */
  private Double volumeRatio;

  /**
   * 换手率
   */
  private Double turnoverRate;

  /**
   * 市盈率(TTM)
   */
  private Double ttmPeRate;

  /**
   * 市盈率(LYR)
   */
  private Double lyrPeRate;

  /**
   * 总市值
   */
  private Double marketCap;

  /**
   * 流通市值
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
