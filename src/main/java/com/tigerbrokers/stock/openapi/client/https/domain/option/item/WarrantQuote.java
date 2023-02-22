package com.tigerbrokers.stock.openapi.client.https.domain.option.item;

import com.tigerbrokers.stock.openapi.client.struct.enums.HaltedStatus;
import java.io.Serializable;

/**
 * Description:
 * Created on 2023/02/02.
 */
public class WarrantQuote implements Serializable {
  private static final long serialVersionUID = 1L;

  /** symbol */
  private String symbol;
  /** name */
  private String name;
  /** exchange */
  private String exchange;
  /** market */
  private String market;
  /** security type */
  private String secType;
  /** currency */
  private String currency;

  /** expiry date，formate：yyyy-MM-dd */
  private String expiry;
  /** strike price */
  private String strike;
  /** PUT/CALL */
  private String right;

  /** multiplier */
  private Double multiplier;
  /** last trading date */
  private Long lastTradingDate;

  /** entitlement ratio */
  private Double entitlementRatio;
  /** entitlement price */
  private Double entitlementPrice;
  /** min tick */
  private Double minTick;
  /** listing date */
  private Long listingDate;
  /** call price（only IOPT） */
  private Double callPrice;
  /** halted。0: Normal 3: Suspension 4: Delisted */
  private HaltedStatus halted;
  /** underlying symbol */
  private String underlyingSymbol;
  /** timestamp */
  private Long timestamp;

  /** latest price */
  private Double latestPrice;
  /** previous close price */
  private Double preClose;
  /** open price */
  private Double open;
  /** high */
  private Double high;
  /** low */
  private Double low;

  /** volume */
  private Long volume;
  /** amount */
  private Double amount;
  /** premium (2.141% -> 0.02141) */
  private Double premium;
  /** outstanding ratio (0.19% -> 0.0019) */
  private Double outstandingRatio;
  /** implied volatility(only for warrant) */
  private Double impliedVolatility;
  /** in/out price (in the money, 20.744% -> 0.20744) */
  private Double inOutPrice;
  /** delta(only for warrant) */
  private Double delta;
  /** leverage ratio */
  private Double leverageRatio;
  /** breakeven point */
  private Double breakevenPoint;

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

  public String getExchange() {
    return exchange;
  }

  public void setExchange(String exchange) {
    this.exchange = exchange;
  }

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public String getSecType() {
    return secType;
  }

  public void setSecType(String secType) {
    this.secType = secType;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getExpiry() {
    return expiry;
  }

  public void setExpiry(String expiry) {
    this.expiry = expiry;
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

  public Double getMultiplier() {
    return multiplier;
  }

  public void setMultiplier(Double multiplier) {
    this.multiplier = multiplier;
  }

  public Long getLastTradingDate() {
    return lastTradingDate;
  }

  public void setLastTradingDate(Long lastTradingDate) {
    this.lastTradingDate = lastTradingDate;
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

  public Double getMinTick() {
    return minTick;
  }

  public void setMinTick(Double minTick) {
    this.minTick = minTick;
  }

  public Long getListingDate() {
    return listingDate;
  }

  public void setListingDate(Long listingDate) {
    this.listingDate = listingDate;
  }

  public Double getCallPrice() {
    return callPrice;
  }

  public void setCallPrice(Double callPrice) {
    this.callPrice = callPrice;
  }

  public HaltedStatus getHalted() {
    return halted;
  }

  public void setHalted(HaltedStatus halted) {
    this.halted = halted;
  }

  public String getUnderlyingSymbol() {
    return underlyingSymbol;
  }

  public void setUnderlyingSymbol(String underlyingSymbol) {
    this.underlyingSymbol = underlyingSymbol;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
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

  public Double getOpen() {
    return open;
  }

  public void setOpen(Double open) {
    this.open = open;
  }

  public Double getHigh() {
    return high;
  }

  public void setHigh(Double high) {
    this.high = high;
  }

  public Double getLow() {
    return low;
  }

  public void setLow(Double low) {
    this.low = low;
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

  public Double getPremium() {
    return premium;
  }

  public void setPremium(Double premium) {
    this.premium = premium;
  }

  public Double getOutstandingRatio() {
    return outstandingRatio;
  }

  public void setOutstandingRatio(Double outstandingRatio) {
    this.outstandingRatio = outstandingRatio;
  }

  public Double getImpliedVolatility() {
    return impliedVolatility;
  }

  public void setImpliedVolatility(Double impliedVolatility) {
    this.impliedVolatility = impliedVolatility;
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

  public Double getBreakevenPoint() {
    return breakevenPoint;
  }

  public void setBreakevenPoint(Double breakevenPoint) {
    this.breakevenPoint = breakevenPoint;
  }
}
