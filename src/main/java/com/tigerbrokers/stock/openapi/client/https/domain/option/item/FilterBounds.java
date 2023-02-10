package com.tigerbrokers.stock.openapi.client.https.domain.option.item;

import com.tigerbrokers.stock.openapi.client.struct.Range;
import java.io.Serializable;
import java.util.List;

/**
 * Description:
 * Created on 2023/02/02.
 */
public class FilterBounds implements Serializable {
  private static final long serialVersionUID = 1L;

  private List<String> issuerName;

  private List<String> expireDate;

  private List<Integer> lotSize;

  private List<Double> entitlementRatio;

  private Range<Double> leverageRatio;

  private Range<Double> strike;

  private Range<Double> premium;

  private Range<Double> outstandingRatio;

  private Range<Double> impliedVolatility;

  private Range<Double> effectiveLeverage;

  private Range<Double> callPrice;

  public List<String> getIssuerName() {
    return issuerName;
  }

  public void setIssuerName(List<String> issuerName) {
    this.issuerName = issuerName;
  }

  public List<String> getExpireDate() {
    return expireDate;
  }

  public void setExpireDate(List<String> expireDate) {
    this.expireDate = expireDate;
  }

  public List<Integer> getLotSize() {
    return lotSize;
  }

  public void setLotSize(List<Integer> lotSize) {
    this.lotSize = lotSize;
  }

  public List<Double> getEntitlementRatio() {
    return entitlementRatio;
  }

  public void setEntitlementRatio(List<Double> entitlementRatio) {
    this.entitlementRatio = entitlementRatio;
  }

  public Range<Double> getLeverageRatio() {
    return leverageRatio;
  }

  public void setLeverageRatio(Range<Double> leverageRatio) {
    this.leverageRatio = leverageRatio;
  }

  public Range<Double> getStrike() {
    return strike;
  }

  public void setStrike(Range<Double> strike) {
    this.strike = strike;
  }

  public Range<Double> getPremium() {
    return premium;
  }

  public void setPremium(Range<Double> premium) {
    this.premium = premium;
  }

  public Range<Double> getOutstandingRatio() {
    return outstandingRatio;
  }

  public void setOutstandingRatio(Range<Double> outstandingRatio) {
    this.outstandingRatio = outstandingRatio;
  }

  public Range<Double> getImpliedVolatility() {
    return impliedVolatility;
  }

  public void setImpliedVolatility(Range<Double> impliedVolatility) {
    this.impliedVolatility = impliedVolatility;
  }

  public Range<Double> getEffectiveLeverage() {
    return effectiveLeverage;
  }

  public void setEffectiveLeverage(Range<Double> effectiveLeverage) {
    this.effectiveLeverage = effectiveLeverage;
  }

  public Range<Double> getCallPrice() {
    return callPrice;
  }

  public void setCallPrice(Range<Double> callPrice) {
    this.callPrice = callPrice;
  }
}
