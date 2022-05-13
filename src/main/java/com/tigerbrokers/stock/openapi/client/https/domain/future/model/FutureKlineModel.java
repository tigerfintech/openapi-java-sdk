package com.tigerbrokers.stock.openapi.client.https.domain.future.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureKlineModel extends ApiModel {

  @JSONField(name = "contract_codes")
  private List<String> contractCodes;
  @JSONField(name = "period")
  private String period;
  @JSONField(name = "begin_time")
  private Long beginTime;
  @JSONField(name = "end_time")
  private Long endTime;
  @JSONField(name = "limit")
  private Integer limit;
  @JSONField(name = "page_token")
  private String pageToken;

  public FutureKlineModel() {
  }

  public FutureKlineModel(List<String> contractCodes, String kType) {
    this.contractCodes = contractCodes;
    this.period = kType;
  }

  public FutureKlineModel(List<String> contractCodes, String kType, Long beginTime, Long endTime, Integer limit) {
    this.contractCodes = contractCodes;
    this.period = kType;
    this.beginTime = beginTime;
    this.endTime = endTime;
    this.limit = limit;
  }

  public List<String> getContractCodes() {
    return contractCodes;
  }

  public void setContractCodes(List<String> contractCodes) {
    this.contractCodes = contractCodes;
  }

  public String getPeriod() {
    return period;
  }

  public void setPeriod(String period) {
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
   * set pageToken，only for single contract
   * @param pageToken
   */
  public void setPageToken(String pageToken) {
    this.pageToken = pageToken;
  }
}
