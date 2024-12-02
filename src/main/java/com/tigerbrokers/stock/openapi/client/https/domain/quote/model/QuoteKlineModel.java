package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.struct.enums.RightOption;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import com.tigerbrokers.stock.openapi.client.util.DateUtils;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteKlineModel extends QuoteSymbolModel {

  @JSONField(name = "period")
  private String kType;
  private String right;
  @JSONField(name = "begin_time")
  private Long beginTime;
  @JSONField(name = "end_time")
  private Long endTime;
  private Integer limit;
  @JSONField(name = "page_token")
  private String pageToken;
  @JSONField(name = "only_hour_trading")
  private Boolean onlyHourTrading;

  public QuoteKlineModel() {

  }

  public QuoteKlineModel(List<String> symbols, String kType) {
    super(symbols);
    this.kType = kType;
  }

  public QuoteKlineModel(List<String> symbols, String kType, Long beginTime, Long endTime) {
    super(symbols);
    this.kType = kType;
    this.beginTime = beginTime;
    this.endTime = endTime;
  }

  public QuoteKlineModel(List<String> symbols, String kType, String beginTime, String endTime) {
    super(symbols);
    this.kType = kType;
    setBeginTime(beginTime);
    setEndTime(endTime);
  }

  public QuoteKlineModel(List<String> symbols, String kType, String beginTime, String endTime, TimeZoneId zoneId) {
    super(symbols);
    this.kType = kType;
    setBeginTime(beginTime, zoneId);
    setEndTime(endTime, zoneId);
  }

  public String getkType() {
    return kType;
  }

  public void setkType(String kType) {
    this.kType = kType;
  }

  public String getRight() {
    return right;
  }

  public void setRight(RightOption right) {
    this.right = right == null ? null : right.name();
  }

  public void setRight(String right) {
    this.right = right;
  }

  public Long getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(Long beginTime) {
    this.beginTime = beginTime;
  }

  public void setBeginTime(String beginTime) {
    setBeginTime(beginTime, ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone());
  }

  public void setBeginTime(String beginTime, TimeZoneId zoneId) {
    Date date = DateUtils.getZoneDate(beginTime, zoneId);
    if (date != null) {
      this.beginTime = date.getTime();
    }
  }

  public Long getEndTime() {
    return endTime;
  }

  public void setEndTime(Long endTime) {
    this.endTime = endTime;
  }

  public void setEndTime(String endTime) {
    setEndTime(endTime, ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone());
  }

  public void setEndTime(String endTime, TimeZoneId zoneId) {
    Date date = DateUtils.getZoneDate(endTime, zoneId);
    if (date != null) {
      this.endTime = date.getTime();
    }
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public String getPageToken() {
    return pageToken;
  }

  /**
   * set pageToken，only for single symbol
   * @param pageToken
   */
  public void setPageToken(String pageToken) {
    this.pageToken = pageToken;
  }

  public Boolean getOnlyHourTrading() {
    return onlyHourTrading;
  }

  public void setOnlyHourTrading(Boolean onlyHourTrading) {
    this.onlyHourTrading = onlyHourTrading;
  }
}
