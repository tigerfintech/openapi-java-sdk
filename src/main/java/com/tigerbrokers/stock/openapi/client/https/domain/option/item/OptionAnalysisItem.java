package com.tigerbrokers.stock.openapi.client.https.domain.option.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description: Option analysis response item
 */
public class OptionAnalysisItem extends ApiModel {

  private String symbol;
  private Double impliedVol30Days;
  private Double hisVolatility;
  private Double ivHisVRatio;
  private Double callPutRatio;
  private ImpliedVolMetric impliedVolMetric;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Double getImpliedVol30Days() {
    return impliedVol30Days;
  }

  public void setImpliedVol30Days(Double impliedVol30Days) {
    this.impliedVol30Days = impliedVol30Days;
  }

  public Double getHisVolatility() {
    return hisVolatility;
  }

  public void setHisVolatility(Double hisVolatility) {
    this.hisVolatility = hisVolatility;
  }

  public Double getIvHisVRatio() {
    return ivHisVRatio;
  }

  public void setIvHisVRatio(Double ivHisVRatio) {
    this.ivHisVRatio = ivHisVRatio;
  }

  public Double getCallPutRatio() {
    return callPutRatio;
  }

  public void setCallPutRatio(Double callPutRatio) {
    this.callPutRatio = callPutRatio;
  }

  public ImpliedVolMetric getImpliedVolMetric() {
    return impliedVolMetric;
  }

  public void setImpliedVolMetric(ImpliedVolMetric impliedVolMetric) {
    this.impliedVolMetric = impliedVolMetric;
  }

  @Override
  public String toString() {
    return "OptionAnalysisItem{" +
        "symbol='" + symbol + '\'' +
        ", impliedVol30Days=" + impliedVol30Days +
        ", hisVolatility=" + hisVolatility +
        ", ivHisVRatio=" + ivHisVRatio +
        ", callPutRatio=" + callPutRatio +
        ", impliedVolMetric=" + impliedVolMetric +
        '}';
  }
}
