package com.tigerbrokers.stock.openapi.client.https.domain.future.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.math.BigDecimal;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureContractItem extends ApiModel {

  private String type;
  private String name;
  private String ibCode;
  private String contractCode;
  private String contractMonth;
  private String exchangeCode;
  private BigDecimal multiplier;
  private BigDecimal minTick;
  private String lastTradingDate;
  private String firstNoticeDate;
  private long lastBiddingCloseTime;
  private String currency;
  private boolean isContinuous;
  private boolean isTrade;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIbCode() {
    return ibCode;
  }

  public void setIbCode(String ibCode) {
    this.ibCode = ibCode;
  }

  public String getContractCode() {
    return contractCode;
  }

  public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
  }

  public String getContractMonth() {
    return contractMonth;
  }

  public void setContractMonth(String contractMonth) {
    this.contractMonth = contractMonth;
  }

  public String getExchangeCode() {
    return exchangeCode;
  }

  public void setExchangeCode(String exchangeCode) {
    this.exchangeCode = exchangeCode;
  }

  public BigDecimal getMultiplier() {
    return multiplier;
  }

  public void setMultiplier(BigDecimal multiplier) {
    this.multiplier = multiplier;
  }

  public BigDecimal getMinTick() {
    return minTick;
  }

  public void setMinTick(BigDecimal minTick) {
    this.minTick = minTick;
  }

  public String getLastTradingDate() {
    return lastTradingDate;
  }

  public void setLastTradingDate(String lastTradingDate) {
    this.lastTradingDate = lastTradingDate;
  }

  public String getFirstNoticeDate() {
    return firstNoticeDate;
  }

  public void setFirstNoticeDate(String firstNoticeDate) {
    this.firstNoticeDate = firstNoticeDate;
  }

  public long getLastBiddingCloseTime() {
    return lastBiddingCloseTime;
  }

  public void setLastBiddingCloseTime(long lastBiddingCloseTime) {
    this.lastBiddingCloseTime = lastBiddingCloseTime;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public boolean isContinuous() {
    return isContinuous;
  }

  public void setContinuous(boolean continuous) {
    isContinuous = continuous;
  }

  public boolean isTrade() {
    return isTrade;
  }

  public void setTrade(boolean trade) {
    isTrade = trade;
  }

  @Override
  public String toString() {
    return "FutureContractItem{" +
        "type='" + type + '\'' +
        ", name='" + name + '\'' +
        ", ibCode='" + ibCode + '\'' +
        ", contractCode='" + contractCode + '\'' +
        ", contractMonth='" + contractMonth + '\'' +
        ", exchangeCode='" + exchangeCode + '\'' +
        ", multiplier=" + (multiplier == null ? multiplier : multiplier.stripTrailingZeros().toPlainString()) +
        ", minTick=" + minTick +
        ", lastTradingDate='" + lastTradingDate + '\'' +
        ", firstNoticeDate='" + firstNoticeDate + '\'' +
        ", lastBiddingCloseTime=" + lastBiddingCloseTime +
        ", currency='" + currency + '\'' +
        ", isContinuous=" + isContinuous +
        ", isTrade=" + isTrade +
        '}';
  }
}
