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
  String delta;
  /**
   * gamma
   */
  String gamma;
  /**
   * theta
   */
  String theta;
  /**
   * vega
   */
  String vega;
  /**
   * 时间价值
   */
  String timeValue;
  /**
   * 溢价率
   */
  String premiumRate;
  /**
   * 买入盈利率
   */
  String profitRate;
  /**
   * 隐含波动率
   */
  String volatility;
  /**
   * 杠杆率
   */
  String leverage;
  /**
   * 内在价值
   */
  String insideValue;
  /**
   * 历史波动率
   */
  String historyVolatility;
  /**
   * 未平合约数
   */
  String openInterest;

  public String getDelta() {
    return delta;
  }

  public void setDelta(String delta) {
    this.delta = delta;
  }

  public String getGamma() {
    return gamma;
  }

  public void setGamma(String gamma) {
    this.gamma = gamma;
  }

  public String getTheta() {
    return theta;
  }

  public void setTheta(String theta) {
    this.theta = theta;
  }

  public String getVega() {
    return vega;
  }

  public void setVega(String vega) {
    this.vega = vega;
  }

  public String getTimeValue() {
    return timeValue;
  }

  public void setTimeValue(String timeValue) {
    this.timeValue = timeValue;
  }

  public String getPremiumRate() {
    return premiumRate;
  }

  public void setPremiumRate(String premiumRate) {
    this.premiumRate = premiumRate;
  }

  public String getProfitRate() {
    return profitRate;
  }

  public void setProfitRate(String profitRate) {
    this.profitRate = profitRate;
  }

  public String getVolatility() {
    return volatility;
  }

  public void setVolatility(String volatility) {
    this.volatility = volatility;
  }

  public String getLeverage() {
    return leverage;
  }

  public void setLeverage(String leverage) {
    this.leverage = leverage;
  }

  public String getInsideValue() {
    return insideValue;
  }

  public void setInsideValue(String insideValue) {
    this.insideValue = insideValue;
  }

  public String getHistoryVolatility() {
    return historyVolatility;
  }

  public void setHistoryVolatility(String historyVolatility) {
    this.historyVolatility = historyVolatility;
  }

  public String getOpenInterest() {
    return openInterest;
  }

  public void setOpenInterest(String openInterest) {
    this.openInterest = openInterest;
  }
}
