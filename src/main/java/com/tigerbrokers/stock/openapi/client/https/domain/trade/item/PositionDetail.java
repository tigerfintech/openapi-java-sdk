package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

import java.io.Serializable;
import java.util.List;

/**
 * @author bean
 * @date 2023/6/20 11:03 AM
 */
public class PositionDetail implements Serializable {
  private static final long serialVersionUID = 1L;

  private String account;
  private long position;
  private int positionScale;
  private Double positionQty;
  private Double salableQty;
  private Double averageCost;
  private Double averageCostByAverage;
  private Double averageCostOfCarry;
  private Double marketValue;
  private Double latestPrice;
  private Boolean isLevel0Price;
  private Double realizedPnl;
  private Double realizedPnlByAverage;
  private Double unrealizedPnl;
  private Double unrealizedPnlByAverage;
  private Double unrealizedPnlPercent;
  private Double unrealizedPnlPercentByAverage;
  private Double unrealizedPnlByCostOfCarry;
  private Double unrealizedPnlPercentByCostOfCarry;
  // please use 'salableQty' instead of 'salable'
  @Deprecated
  private Integer salable;
  private String secType;
  private String market;
  private String currency;
  private String identifier;
  private String symbol;
  private String expiry;
  private String strike;
  private String right;
  private Double multiplier;
  private Long updateTimestamp;
  /** maintenance margin percent */
  private Double mmPercent;
  /** maintenance margin value */
  private Double mmValue;
  /** today's profit and loss */
  private Double todayPnl;
  /** today's profit and loss percent */
  private Double todayPnlPercent;
  /** Fund only, yesterday's profit and loss */
  private Double yesterdayPnl;
  /** The closing price on the last trading day (pre-adjusted for stock rights) */
  private Double lastClosePrice;
  /** contract categories */
  private List<String> categories;

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public long getPosition() {
    return position;
  }

  public void setPosition(long position) {
    this.position = position;
  }

  public int getPositionScale() {
    return positionScale;
  }

  public void setPositionScale(int positionScale) {
    this.positionScale = positionScale;
  }

  public Double getAverageCost() {
    return averageCost;
  }

  public void setAverageCost(Double averageCost) {
    this.averageCost = averageCost;
  }

  public Double getAverageCostByAverage() {
    return averageCostByAverage;
  }

  public void setAverageCostByAverage(Double averageCostByAverage) {
    this.averageCostByAverage = averageCostByAverage;
  }

  public Double getMarketValue() {
    return marketValue;
  }

  public void setMarketValue(Double marketValue) {
    this.marketValue = marketValue;
  }

  public Double getLatestPrice() {
    return latestPrice;
  }

  public void setLatestPrice(Double latestPrice) {
    this.latestPrice = latestPrice;
  }

  public Double getRealizedPnl() {
    return realizedPnl;
  }

  public void setRealizedPnl(Double realizedPnl) {
    this.realizedPnl = realizedPnl;
  }

  public Double getRealizedPnlByAverage() {
    return realizedPnlByAverage;
  }

  public void setRealizedPnlByAverage(Double realizedPnlByAverage) {
    this.realizedPnlByAverage = realizedPnlByAverage;
  }

  public Double getUnrealizedPnl() {
    return unrealizedPnl;
  }

  public void setUnrealizedPnl(Double unrealizedPnl) {
    this.unrealizedPnl = unrealizedPnl;
  }

  public Double getUnrealizedPnlByAverage() {
    return unrealizedPnlByAverage;
  }

  public void setUnrealizedPnlByAverage(Double unrealizedPnlByAverage) {
    this.unrealizedPnlByAverage = unrealizedPnlByAverage;
  }

  public Double getUnrealizedPnlPercent() {
    return unrealizedPnlPercent;
  }

  public void setUnrealizedPnlPercent(Double unrealizedPnlPercent) {
    this.unrealizedPnlPercent = unrealizedPnlPercent;
  }

  public Double getUnrealizedPnlPercentByAverage() {
    return unrealizedPnlPercentByAverage;
  }

  public void setUnrealizedPnlPercentByAverage(Double unrealizedPnlPercentByAverage) {
    this.unrealizedPnlPercentByAverage = unrealizedPnlPercentByAverage;
  }

  public Integer getSalable() {
    return salable;
  }

  public void setSalable(Integer salable) {
    this.salable = salable;
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

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
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

  public Long getUpdateTimestamp() {
    return updateTimestamp;
  }

  public void setUpdateTimestamp(Long updateTimestamp) {
    this.updateTimestamp = updateTimestamp;
  }

  public Double getMmPercent() {
    return mmPercent;
  }

  public void setMmPercent(Double mmPercent) {
    this.mmPercent = mmPercent;
  }

  public Double getMmValue() {
    return mmValue;
  }

  public void setMmValue(Double mmValue) {
    this.mmValue = mmValue;
  }

  public Double getPositionQty() {
    return positionQty;
  }

  public void setPositionQty(Double positionQty) {
    this.positionQty = positionQty;
  }

  public Double getSalableQty() {
    return salableQty;
  }

  public void setSalableQty(Double salableQty) {
    this.salableQty = salableQty;
  }

  public Double getAverageCostOfCarry() {
    return averageCostOfCarry;
  }

  public void setAverageCostOfCarry(Double averageCostOfCarry) {
    this.averageCostOfCarry = averageCostOfCarry;
  }

  public Boolean getLevel0Price() {
    return isLevel0Price;
  }

  public void setLevel0Price(Boolean level0Price) {
    isLevel0Price = level0Price;
  }

  public Double getUnrealizedPnlByCostOfCarry() {
    return unrealizedPnlByCostOfCarry;
  }

  public void setUnrealizedPnlByCostOfCarry(Double unrealizedPnlByCostOfCarry) {
    this.unrealizedPnlByCostOfCarry = unrealizedPnlByCostOfCarry;
  }

  public Double getUnrealizedPnlPercentByCostOfCarry() {
    return unrealizedPnlPercentByCostOfCarry;
  }

  public void setUnrealizedPnlPercentByCostOfCarry(Double unrealizedPnlPercentByCostOfCarry) {
    this.unrealizedPnlPercentByCostOfCarry = unrealizedPnlPercentByCostOfCarry;
  }

  public Double getTodayPnl() {
    return todayPnl;
  }

  public void setTodayPnl(Double todayPnl) {
    this.todayPnl = todayPnl;
  }

  public Double getTodayPnlPercent() {
    return todayPnlPercent;
  }

  public void setTodayPnlPercent(Double todayPnlPercent) {
    this.todayPnlPercent = todayPnlPercent;
  }

  public Double getYesterdayPnl() {
    return yesterdayPnl;
  }

  public void setYesterdayPnl(Double yesterdayPnl) {
    this.yesterdayPnl = yesterdayPnl;
  }

  public Double getLastClosePrice() {
    return lastClosePrice;
  }

  public void setLastClosePrice(Double lastClosePrice) {
    this.lastClosePrice = lastClosePrice;
  }

  public List<String> getCategories() {
    return categories;
  }

  public void setCategories(List<String> categories) {
    this.categories = categories;
  }
}
