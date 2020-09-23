package com.tigerbrokers.stock.openapi.client.struct.param;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import java.io.Serializable;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/07/16.
 */
public class QuoteParameter implements Serializable {

  private List<String> symbols;
  private String period;
  private Market market;
  @JSONField(name = "include_ask_bid")
  private Boolean includeAskBid;
  @JSONField(name = "include_hour_trading")
  private Boolean includeHourTrading;
  private int limit;
  private String right;
  @JSONField(name = "begin_time")
  private long beginTime;
  @JSONField(name = "end_time")
  private long endTime;
  @JSONField(name = "begin_index")
  private int beginIndex;
  @JSONField(name = "end_index")
  private int endIndex;
  @JSONField(name = "lang")
  private Language language;
  @JSONField(name = "industry_id")
  private Integer industryId;
  @JSONField(name = "industry_level")
  private String industryLevel;

  public QuoteParameter() {
  }

  public List<String> getSymbols() {
    return symbols;
  }

  public void setSymbols(List<String> symbols) {
    this.symbols = symbols;
  }

  public String getPeriod() {
    return period;
  }

  public void setPeriod(String period) {
    this.period = period;
  }

  public Market getMarket() {
    return market;
  }

  public void setMarket(Market market) {
    this.market = market;
  }

  public Boolean getIncludeAskBid() {
    return includeAskBid;
  }

  public void setIncludeAskBid(Boolean includeAskBid) {
    this.includeAskBid = includeAskBid;
  }

  public Boolean getIncludeHourTrading() {
    return includeHourTrading;
  }

  public void setIncludeHourTrading(Boolean includeHourTrading) {
    this.includeHourTrading = includeHourTrading;
  }

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }

  public String getRight() {
    return right;
  }

  public void setRight(String right) {
    this.right = right;
  }

  public long getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(long beginTime) {
    this.beginTime = beginTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }

  public int getBeginIndex() {
    return beginIndex;
  }

  public void setBeginIndex(int beginIndex) {
    this.beginIndex = beginIndex;
  }

  public int getEndIndex() {
    return endIndex;
  }

  public void setEndIndex(int endIndex) {
    this.endIndex = endIndex;
  }

  public Language getLanguage() {
    return language;
  }

  public void setLanguage(Language language) {
    this.language = language;
  }

  public Integer getIndustryId() {
    return industryId;
  }

  public void setIndustryId(Integer industryId) {
    this.industryId = industryId;
  }

  public String getIndustryLevel() {
    return industryLevel;
  }

  public void setIndustryLevel(String industryLevel) {
    this.industryLevel = industryLevel;
  }

  @Override
  public String toString() {
    return "QuoteParameter{" +
        "symbols=" + symbols +
        ", period='" + period + '\'' +
        ", market=" + market +
        ", includeAskBid=" + includeAskBid +
        ", includeHourTrading=" + includeHourTrading +
        ", limit=" + limit +
        ", right='" + right + '\'' +
        ", beginTime=" + beginTime +
        ", endTime=" + endTime +
        ", beginIndex=" + beginIndex +
        ", endIndex=" + endIndex +
        ", language=" + language +
        ", industryId=" + industryId +
        ", industryLevel='" + industryLevel + '\'' +
        '}';
  }
}