package com.tigerbrokers.stock.openapi.client.https.domain.option.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.TigerApiException;


public class OptionTimelineModel extends OptionCommonModel {

  @JSONField(name = "begin_time")
  private Long beginTime;

  public OptionTimelineModel() {
  }

  public OptionTimelineModel(String identifier) throws TigerApiException {
    super(identifier);
  }

  public Long getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(Long beginTime) {
    this.beginTime = beginTime;
  }

  @Override
  public String toString() {
    return "OptionTimelineModel{" +
        "beginTime=" + beginTime +
        ", symbol='" + symbol + '\'' +
        ", right='" + right + '\'' +
        ", strike='" + strike + '\'' +
        ", expiry=" + expiry +
        ", lang=" + lang +
        '}';
  }
}
