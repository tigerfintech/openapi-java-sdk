package com.tigerbrokers.stock.openapi.client.https.domain.fund.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import com.tigerbrokers.stock.openapi.client.util.DateUtils;
import java.util.List;

/**
 * Description:
 * Created by bean on 2023/07/25.
 */
public class FundQuoteHistoryModel extends FundSymbolModel {

  @JSONField(name = "begin_time")
  private Long beginTime;
  @JSONField(name = "end_time")
  private Long endTime;
  private Integer limit;

  public FundQuoteHistoryModel(List<String> symbols) {
    super(symbols);
  }

  public FundQuoteHistoryModel(List<String> symbols, Long beginTime, Long endTime) {
    super(symbols);
    this.beginTime = beginTime;
    this.endTime = endTime;
  }

  public FundQuoteHistoryModel(List<String> symbols, String beginTime, String endTime, TimeZoneId zoneId) {
    super(symbols);
    this.beginTime = DateUtils.getTimestamp(beginTime, zoneId);
    this.endTime = DateUtils.getTimestamp(endTime, zoneId);
  }

  public Long getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(Long beginTime) {
    this.beginTime = beginTime;
  }

  public Long getEndTime() {
    return endTime;
  }

  public void setEndTime(Long endTime) {
    this.endTime = endTime;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }
}
