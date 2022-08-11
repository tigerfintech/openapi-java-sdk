package com.tigerbrokers.stock.openapi.client.struct;

/**
 * 期权基本面信息
 * 作者：ltc
 * 时间：2019/08/27
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
   * 时间价值
   */
  double timeValue;
  /**
   * 溢价率
   */
  double premiumRate;
  /**
   * 买入盈利率
   */
  double profitRate;
  /**
   * 隐含波动率
   */
  double volatility;
  /**
   * 杠杆率
   */
  double leverage;
  /**
   * 内在价值
   */
  double insideValue;
  /**
   * 历史波动率
   */
  double historyVolatility;
  /**
   * 未平合约数
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
