package com.tigerbrokers.stock.openapi.client.https.domain.future.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * Description:
 * Created by bean on 2023/08/29.
 */
public class FutureHistoryMainContractModel extends ApiModel {

  @JSONField(name = "contract_codes")
  private List<String> contractCodes;
  @JSONField(name = "begin_time")
  private Long beginTime;
  @JSONField(name = "end_time")
  private Long endTime;

  public FutureHistoryMainContractModel() {
  }

  public FutureHistoryMainContractModel(List<String> contractCodes) {
    this.contractCodes = contractCodes;
  }

  public FutureHistoryMainContractModel(List<String> contractCodes, Long beginTime, Long endTime) {
    this.contractCodes = contractCodes;
    this.beginTime = beginTime;
    this.endTime = endTime;
  }

  public List<String> getContractCodes() {
    return contractCodes;
  }

  public void setContractCodes(List<String> contractCodes) {
    this.contractCodes = contractCodes;
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
