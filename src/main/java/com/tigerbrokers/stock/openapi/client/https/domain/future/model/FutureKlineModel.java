package com.tigerbrokers.stock.openapi.client.https.domain.future.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.FutureKType;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureKlineModel extends ApiModel {

  @JSONField(name = "contract_code")
  private String contractCode;
  private FutureKType period;
  @JSONField(name = "begin_time")
  private Long beginTime;
  @JSONField(name = "end_time")
  private Long endTime;

  public FutureKlineModel() {
  }

  public FutureKlineModel(String contractCode, FutureKType kType) {
    this.contractCode = contractCode;
    this.period = kType;
  }

  public FutureKlineModel(String contractCode, FutureKType kType, Long beginTime, Long endTime) {
    this.contractCode = contractCode;
    this.period = kType;
    this.beginTime = beginTime;
    this.endTime = endTime;
  }

  public String getContractCode() {
    return contractCode;
  }

  public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
  }

  public FutureKType getPeriod() {
    return period;
  }

  public void setPeriod(FutureKType period) {
    this.period = period;
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
}
