package com.tigerbrokers.stock.openapi.client.https.domain.option.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import com.tigerbrokers.stock.openapi.client.util.DateUtils;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import com.tigerbrokers.stock.openapi.client.util.SymbolUtil;
import java.util.Date;

/**
 * Description:
 * Created by lijiawen on 2018/12/26.
 */
public class OptionKlineModel extends OptionCommonModel {

  @JSONField(name = "begin_time")
  private Long beginTime;
  @JSONField(name = "end_time")
  private Long endTime;
  @JSONField(name = "period")
  private String period;

  public OptionKlineModel() {

  }

  public OptionKlineModel(String identifier) throws TigerApiException {
    super(identifier);
  }

  public Long getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(Long beginTime) {
    this.beginTime = beginTime;
  }

  public void setBeginTime(String beginTime) {
    TimeZoneId timeZoneId = StringUtils.isEmpty(this.symbol) ?
        TimeZoneId.NewYork : SymbolUtil.getZoneIdBySymbol(this.symbol);
    setBeginTime(beginTime, timeZoneId);
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
    TimeZoneId timeZoneId = StringUtils.isEmpty(this.symbol) ?
        TimeZoneId.NewYork : SymbolUtil.getZoneIdBySymbol(this.symbol);
    setEndTime(endTime, timeZoneId);
  }

  public void setEndTime(String endTime, TimeZoneId zoneId) {
    Date date = DateUtils.getZoneDate(endTime, zoneId);
    if (date != null) {
      this.endTime = date.getTime();
    }
  }

  public String getPeriod() {
    return period;
  }

  public void setPeriod(String period) {
    this.period = period;
  }

  @Override
  public String toString() {
    return "OptionKlineModel{" +
        "beginTime=" + beginTime +
        ", endTime=" + endTime +
        ", symbol='" + symbol + '\'' +
        ", right='" + right + '\'' +
        ", strike='" + strike + '\'' +
        ", expiry='" + expiry + '\'' +
        ", period='" + period + '\'' +
        '}';
  }
}
