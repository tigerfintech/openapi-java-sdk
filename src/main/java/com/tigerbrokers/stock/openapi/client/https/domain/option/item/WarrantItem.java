package com.tigerbrokers.stock.openapi.client.https.domain.option.item;

import com.tigerbrokers.stock.openapi.client.struct.enums.WarrantState;
import com.tigerbrokers.stock.openapi.client.struct.enums.WarrantType;
import java.io.Serializable;

/**
 * Description:
 * Created on 2023/02/02.
 */
public class WarrantItem implements Serializable {
  private static final long serialVersionUID = 1L;

  /** symbol */
  private String symbol;
  /** name */
  private String name;
  /** warrant type 1：Call，2：Put，3：Bull，4：Bear */
  private WarrantType type;
  /** security type */
  private String secType;
  /** market */
  private String market;

  /** entitlement ratio */
  private Double entitlementRatio;
  /** entitlement price */
  private Double entitlementPrice;
  /** premium */
  private Double premium;
  /** breakeven point */
  private Double breakevenPoint;
  /** call price */
  private Double callPrice;
  /**
   * before call level(%)
   */
  private Double beforeCallLevel;
  /** expiry date，formate：yyyy-MM-dd */
  private String expireDate;
  /** last trading date, formate: yyyy-MM-dd */
  private String lastTradingDate;
  /** state */
  private WarrantState state;

  /**
   * change rate
   */
  private Double changeRate;
  /**
   * change amount
   */
  private Double change;
  private Double latestPrice;
  private Long volume;
  private Double amount;
  /** outstanding ratio */
  private Double outstandingRatio;
  /** lot size */
  private Integer lotSize;
  /** strike price */
  private String strike;
  /** in/out */
  private Double inOutPrice;
  /** delta */
  private Double delta;
  /** leverage ratio */
  private Double leverageRatio;
  /**
   * effective leverage
   */
  private Double effectiveLeverage;
  /**
   * implied volatility
   */
  private Double impliedVolatility;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public WarrantType getType() {
    return type;
  }

  public void setType(WarrantType type) {
    this.type = type;
  }

  public String getSecType() {
    return secType;
  }

  public void setSecType(String secType) {
    this.secType = secType;
  }

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public Double getEntitlementRatio() {
    return entitlementRatio;
  }

  public void setEntitlementRatio(Double entitlementRatio) {
    this.entitlementRatio = entitlementRatio;
  }

  public Double getEntitlementPrice() {
    return entitlementPrice;
  }

  public void setEntitlementPrice(Double entitlementPrice) {
    this.entitlementPrice = entitlementPrice;
  }

  public Double getPremium() {
    return premium;
  }

  public void setPremium(Double premium) {
    this.premium = premium;
  }

  public Double getBreakevenPoint() {
    return breakevenPoint;
  }

  public void setBreakevenPoint(Double breakevenPoint) {
    this.breakevenPoint = breakevenPoint;
  }

  public Double getCallPrice() {
    return callPrice;
  }

  public void setCallPrice(Double callPrice) {
    this.callPrice = callPrice;
  }

  public Double getBeforeCallLevel() {
    return beforeCallLevel;
  }

  public void setBeforeCallLevel(Double beforeCallLevel) {
    this.beforeCallLevel = beforeCallLevel;
  }

  public String getExpireDate() {
    return expireDate;
  }

  public void setExpireDate(String expireDate) {
    this.expireDate = expireDate;
  }

  public String getLastTradingDate() {
    return lastTradingDate;
  }

  public void setLastTradingDate(String lastTradingDate) {
    this.lastTradingDate = lastTradingDate;
  }

  public WarrantState getState() {
    return state;
  }

  public void setState(WarrantState state) {
    this.state = state;
  }

  public Double getChangeRate() {
    return changeRate;
  }

  public void setChangeRate(Double changeRate) {
    this.changeRate = changeRate;
  }

  public Double getChange() {
    return change;
  }

  public void setChange(Double change) {
    this.change = change;
  }

  public Double getLatestPrice() {
    return latestPrice;
  }

  public void setLatestPrice(Double latestPrice) {
    this.latestPrice = latestPrice;
  }

  public Long getVolume() {
    return volume;
  }

  public void setVolume(Long volume) {
    this.volume = volume;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Double getOutstandingRatio() {
    return outstandingRatio;
  }

  public void setOutstandingRatio(Double outstandingRatio) {
    this.outstandingRatio = outstandingRatio;
  }

  public Integer getLotSize() {
    return lotSize;
  }

  public void setLotSize(Integer lotSize) {
    this.lotSize = lotSize;
  }

  public String getStrike() {
    return strike;
  }

  public void setStrike(String strike) {
    this.strike = strike;
  }

  public Double getInOutPrice() {
    return inOutPrice;
  }

  public void setInOutPrice(Double inOutPrice) {
    this.inOutPrice = inOutPrice;
  }

  public Double getDelta() {
    return delta;
  }

  public void setDelta(Double delta) {
    this.delta = delta;
  }

  public Double getLeverageRatio() {
    return leverageRatio;
  }

  public void setLeverageRatio(Double leverageRatio) {
    this.leverageRatio = leverageRatio;
  }

  public Double getEffectiveLeverage() {
    return effectiveLeverage;
  }

  public void setEffectiveLeverage(Double effectiveLeverage) {
    this.effectiveLeverage = effectiveLeverage;
  }

  public Double getImpliedVolatility() {
    return impliedVolatility;
  }

  public void setImpliedVolatility(Double impliedVolatility) {
    this.impliedVolatility = impliedVolatility;
  }
}
