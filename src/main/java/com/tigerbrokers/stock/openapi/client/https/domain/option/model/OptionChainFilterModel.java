/*
 * TigerBrokers
 * Copyright (C) 2014-2021 All Rights Reserved.
 */
package com.tigerbrokers.stock.openapi.client.https.domain.option.model;

import com.alibaba.fastjson.annotation.JSONField;
import java.io.Serializable;

public class OptionChainFilterModel implements Serializable {
  private static final long serialVersionUID = 223342119732411L;

  @JSONField(name = "in_the_money")
  private Boolean inTheMoney;
  @JSONField(name = "implied_volatility")
  private Range<Double> impliedVolatility;
  @JSONField(name = "open_interest")
  private Range<Integer> openInterest;
  private Greeks greeks;

  public OptionChainFilterModel inTheMoney(Boolean inTheMoney) {
    this.inTheMoney = inTheMoney;
    return this;
  }

  public OptionChainFilterModel impliedVolatility(Double min, Double max) {
    this.impliedVolatility = new Range<>(min, max);
    return this;
  }

  public OptionChainFilterModel openInterest(Integer min, Integer max) {
    this.openInterest = new Range<>(min, max);
    return this;
  }

  public OptionChainFilterModel greeks(Greeks greeks) {
    this.greeks = greeks;
    return this;
  }

  public Boolean getInTheMoney() {
    return inTheMoney;
  }

  public void setInTheMoney(Boolean inTheMoney) {
    this.inTheMoney = inTheMoney;
  }

  public Range<Double> getImpliedVolatility() {
    return impliedVolatility;
  }

  public void setImpliedVolatility(Range<Double> impliedVolatility) {
    this.impliedVolatility = impliedVolatility;
  }

  public Range<Integer> getOpenInterest() {
    return openInterest;
  }

  public void setOpenInterest(Range<Integer> openInterest) {
    this.openInterest = openInterest;
  }

  public Greeks getGreeks() {
    return greeks;
  }

  public void setGreeks(Greeks greeks) {
    this.greeks = greeks;
  }


  @Override
  public String toString() {
    return "OptionFilterModel{" +
        "inTheMoney=" + inTheMoney +
        ", impliedVolatility=" + impliedVolatility +
        ", openInterest=" + openInterest +
        ", greeks=" + greeks +
        '}';
  }

  public static class Greeks implements Serializable {
    private static final long serialVersionUID = 2827635512071L;

    private Range<Double> delta;
    private Range<Double> gamma;
    private Range<Double> vega;
    private Range<Double> theta;
    private Range<Double> rho;

    public Greeks delta(Double min, Double max) {
      this.delta = new Range<>(min, max);
      return this;
    }

    public Greeks gamma(Double min, Double max) {
      this.gamma = new Range<>(min, max);
      return this;
    }

    public Greeks vega(Double min, Double max) {
      this.vega = new Range<>(min, max);
      return this;
    }

    public Greeks theta(Double min, Double max) {
      this.theta = new Range<>(min, max);
      return this;
    }

    public Greeks rho(Double min, Double max) {
      this.rho = new Range<>(min, max);
      return this;
    }

    public Range<Double> getDelta() {
      return delta;
    }

    public void setDelta(Range<Double> delta) {
      this.delta = delta;
    }

    public Range<Double> getGamma() {
      return gamma;
    }

    public void setGamma(Range<Double> gamma) {
      this.gamma = gamma;
    }

    public Range<Double> getVega() {
      return vega;
    }

    public void setVega(Range<Double> vega) {
      this.vega = vega;
    }

    public Range<Double> getTheta() {
      return theta;
    }

    public void setTheta(Range<Double> theta) {
      this.theta = theta;
    }

    public Range<Double> getRho() {
      return rho;
    }

    public void setRho(Range<Double> rho) {
      this.rho = rho;
    }

    @Override
    public String toString() {
      return "Greeks{" +
          "delta=" + delta +
          ", gamma=" + gamma +
          ", vega=" + vega +
          ", theta=" + theta +
          ", rho=" + rho +
          '}';
    }
  }

  private static class Range<T> implements Serializable {
    private static final long serialVersionUID = 2276635627221L;

    private T min;
    private T max;

    private Range() {

    }

    private Range(T min, T max) {
      this.min = min;
      this.max = max;
    }

    public T getMax() {
      return max;
    }

    public void setMax(T max) {
      this.max = max;
    }

    public T getMin() {
      return min;
    }

    public void setMin(T min) {
      this.min = min;
    }

    @Override
    public String toString() {
      return "Range{" +
          "min=" + min +
          ", max=" + max +
          '}';
    }
  }
}