package com.tigerbrokers.stock.openapi.client.struct;

/**
 * Options Fundamentals Information
 * author：ltc
 * date：2019/08/27
 */
public class OptionFundamentals {

  /**
   * delta
   */
  double delta;
  /**
   * gamma
   */
  double gamma;
  /**
   * theta
   */
  double theta;
  /**
   * vega
   */
  double vega;
  /**
   * rho
   */
  double rho;

  /**
   * Option Predictive Value
   */
  double predictedValue;

  /**
   * option time value
   */
  double timeValue;
  /**
   * Premium rate
   */
  double premiumRate;
  /**
   * Profit rate
   */
  double profitRate;
  /**
   * implied volatility
   */
  double volatility;
  /**
   * leverage
   */
  double leverage;
  /**
   * inside value
   */
  double insideValue;
  /**
   * historical volatility
   */
  double historyVolatility;
  /**
   * open interest
   */
  double openInterest;

  public double getDelta() {
    return delta;
  }

  public void setDelta(double delta) {
    this.delta = delta;
  }

  public double getGamma() {
    return gamma;
  }

  public void setGamma(double gamma) {
    this.gamma = gamma;
  }

  public double getTheta() {
    return theta;
  }

  public void setTheta(double theta) {
    this.theta = theta;
  }

  public double getVega() {
    return vega;
  }

  public void setVega(double vega) {
    this.vega = vega;
  }

  public double getRho() {
    return rho;
  }

  public void setRho(double rho) {
    this.rho = rho;
  }

  public double getPredictedValue() {
    return predictedValue;
  }

  public void setPredictedValue(double predictedValue) {
    this.predictedValue = predictedValue;
  }

  public double getTimeValue() {
    return timeValue;
  }

  public void setTimeValue(double timeValue) {
    this.timeValue = timeValue;
  }

  public double getPremiumRate() {
    return premiumRate;
  }

  public void setPremiumRate(double premiumRate) {
    this.premiumRate = premiumRate;
  }

  public double getProfitRate() {
    return profitRate;
  }

  public void setProfitRate(double profitRate) {
    this.profitRate = profitRate;
  }

  public double getVolatility() {
    return volatility;
  }

  public void setVolatility(double volatility) {
    this.volatility = volatility;
  }

  public double getLeverage() {
    return leverage;
  }

  public void setLeverage(double leverage) {
    this.leverage = leverage;
  }

  public double getInsideValue() {
    return insideValue;
  }

  public void setInsideValue(double insideValue) {
    this.insideValue = insideValue;
  }

  public double getHistoryVolatility() {
    return historyVolatility;
  }

  public void setHistoryVolatility(double historyVolatility) {
    this.historyVolatility = historyVolatility;
  }

  public double getOpenInterest() {
    return openInterest;
  }

  public void setOpenInterest(double openInterest) {
    this.openInterest = openInterest;
  }
}
