/*
 * TigerBrokers
 * Copyright (C) 2014-2023 All Rights Reserved.
 */
package com.tigerbrokers.stock.openapi.client.https.domain.option.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.Range;
import com.tigerbrokers.stock.openapi.client.struct.enums.SortDir;
import java.util.Set;

public class WarrantFilterModel extends ApiModel {

  private String symbol;
  private Integer page;
  @JSONField(name = "page_size")
  private Integer pageSize;
  @JSONField(name = "sort_field_name")
  private String sortFieldName;
  /** sort directions  */
  @JSONField(name = "sort_dir")
  private SortDir sortDir;

  /** 1:Call, 2: Put, 3: Bull,4: Bear, 0: All */
  @JSONField(name = "warrant_type")
  private Set<Integer> warrantType;

  /** broker name */
  @JSONField(name = "issuer_name")
  private String issuerName;
  /** expiry date: yyyy-MM */
  @JSONField(name = "expire_ym")
  private String expireYM;
  /** 0 All, 1 Normal, 2 Terminate Trades, 3 Waiting to be listed */
  private Integer state;

  /** -1:out the money, 1: in the money */
  @JSONField(name = "in_out_price")
  private Set<Integer> inOutPrice;

  @JSONField(name = "lot_size")
  private Set<Integer> lotSize;
  @JSONField(name = "entitlement_ratio")
  private Set<Double> entitlementRatio;

  /** strike price */
  private Range<Double> strike;

  @JSONField(name = "effective_leverage")
  private Range<Double> effectiveLeverage;

  @JSONField(name = "leverage_ratio")
  private Range<Double> leverageRatio;

  @JSONField(name = "call_price")
  private Range<Double> callPrice;

  private Range<Long> volume;
  private Range<Double> premium;

  @JSONField(name = "outstanding_ratio")
  private Range<Double> outstandingRatio;

  @JSONField(name = "implied_volatility")
  private Range<Double> impliedVolatility;

  public WarrantFilterModel() {
  }

  public WarrantFilterModel(String symbol) {
    this.symbol = symbol;
  }

  public WarrantFilterModel symbol(String symbol) {
    this.symbol = symbol;
    return this;
  }

  public WarrantFilterModel page(Integer page) {
    this.page = page;
    return this;
  }

  public WarrantFilterModel pageSize(Integer pageSize) {
    this.pageSize = pageSize;
    return this;
  }

  public WarrantFilterModel sortFieldName(String sortFieldName) {
    this.sortFieldName = sortFieldName;
    return this;
  }

  public WarrantFilterModel sortDir(SortDir sortDir) {
    this.sortDir = sortDir;
    return this;
  }

  public WarrantFilterModel warrantType(Set<Integer> warrantType) {
    this.warrantType = warrantType;
    return this;
  }

  public WarrantFilterModel issuerName(String issuerName) {
    this.issuerName = issuerName;
    return this;
  }

  public WarrantFilterModel expireYM(String expireYM) {
    this.expireYM = expireYM;
    return this;
  }

  public WarrantFilterModel state(Integer state) {
    this.state = state;
    return this;
  }

  public WarrantFilterModel inOutPrice(Set<Integer> inOutPrice) {
    this.inOutPrice = inOutPrice;
    return this;
  }

  public WarrantFilterModel lotSize(Set<Integer> lotSize) {
    this.lotSize = lotSize;
    return this;
  }

  public WarrantFilterModel entitlementRatio(Set<Double> entitlementRatio) {
    this.entitlementRatio = entitlementRatio;
    return this;
  }

  public WarrantFilterModel strike(Double min, Double max) {
    this.strike = new Range<>(min, max);
    return this;
  }

  public WarrantFilterModel effectiveLeverage(Double min, Double max) {
    this.effectiveLeverage = new Range<>(min, max);
    return this;
  }

  public WarrantFilterModel leverageRatio(Double min, Double max) {
    this.leverageRatio = new Range<>(min, max);
    return this;
  }

  public WarrantFilterModel callPrice(Double min, Double max) {
    this.callPrice = new Range<>(min, max);
    return this;
  }

  public WarrantFilterModel volume(Long min, Long max) {
    this.volume = new Range<>(min, max);
    return this;
  }

  public WarrantFilterModel premium(Double min, Double max) {
    this.premium = new Range<>(min, max);
    return this;
  }

  public WarrantFilterModel outstandingRatio(Double min, Double max) {
    this.outstandingRatio = new Range<>(min, max);
    return this;
  }

  public WarrantFilterModel impliedVolatility(Double min, Double max) {
    this.impliedVolatility = new Range<>(min, max);
    return this;
  }

  public Range<Double> getImpliedVolatility() {
    return impliedVolatility;
  }

  public void setImpliedVolatility(Range<Double> impliedVolatility) {
    this.impliedVolatility = impliedVolatility;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public String getSortFieldName() {
    return sortFieldName;
  }

  public void setSortFieldName(String sortFieldName) {
    this.sortFieldName = sortFieldName;
  }

  public SortDir getSortDir() {
    return sortDir;
  }

  public void setSortDir(SortDir sortDir) {
    this.sortDir = sortDir;
  }

  public Set<Integer> getWarrantType() {
    return warrantType;
  }

  public void setWarrantType(Set<Integer> warrantType) {
    this.warrantType = warrantType;
  }

  public String getIssuerName() {
    return issuerName;
  }

  public void setIssuerName(String issuerName) {
    this.issuerName = issuerName;
  }

  public String getExpireYM() {
    return expireYM;
  }

  public void setExpireYM(String expireYM) {
    this.expireYM = expireYM;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public Set<Integer> getInOutPrice() {
    return inOutPrice;
  }

  public void setInOutPrice(Set<Integer> inOutPrice) {
    this.inOutPrice = inOutPrice;
  }

  public Set<Integer> getLotSize() {
    return lotSize;
  }

  public void setLotSize(Set<Integer> lotSize) {
    this.lotSize = lotSize;
  }

  public Set<Double> getEntitlementRatio() {
    return entitlementRatio;
  }

  public void setEntitlementRatio(Set<Double> entitlementRatio) {
    this.entitlementRatio = entitlementRatio;
  }

  public Range<Double> getStrike() {
    return strike;
  }

  public void setStrike(Range<Double> strike) {
    this.strike = strike;
  }

  public Range<Double> getEffectiveLeverage() {
    return effectiveLeverage;
  }

  public void setEffectiveLeverage(Range<Double> effectiveLeverage) {
    this.effectiveLeverage = effectiveLeverage;
  }

  public Range<Double> getLeverageRatio() {
    return leverageRatio;
  }

  public void setLeverageRatio(Range<Double> leverageRatio) {
    this.leverageRatio = leverageRatio;
  }

  public Range<Double> getCallPrice() {
    return callPrice;
  }

  public void setCallPrice(Range<Double> callPrice) {
    this.callPrice = callPrice;
  }

  public Range<Long> getVolume() {
    return volume;
  }

  public void setVolume(Range<Long> volume) {
    this.volume = volume;
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
}