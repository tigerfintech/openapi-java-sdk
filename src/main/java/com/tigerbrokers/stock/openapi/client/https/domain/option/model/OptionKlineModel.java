package com.tigerbrokers.stock.openapi.client.https.domain.option.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import com.tigerbrokers.stock.openapi.client.util.DateUtils;
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

  public Long getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(Long beginTime) {
    this.beginTime = beginTime;
  }

  public void setBeginTime(String beginTime) {
    setBeginTime(beginTime, TimeZoneId.NewYork);
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
    setEndTime(endTime, TimeZoneId.NewYork);
  }

  public void setEndTime(String endTime, TimeZoneId zoneId) {
    Date date = DateUtils.getZoneDate(endTime, zoneId);
    if (date != null) {
      this.endTime = date.getTime();
    }
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
        '}';
  }
}
