package com.tigerbrokers.stock.openapi.client.https.domain.future.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureTickModel extends ApiModel {

  @JSONField(name = "contract_code")
  private String contractCode;
  @JSONField(name = "begin_index")
  private Integer beginIndex;
  @JSONField(name = "end_index")
  private Integer endIndex;

  public FutureTickModel() {
  }

  public FutureTickModel(String contractCode) {
    this.contractCode = contractCode;
  }

  public FutureTickModel(String contractCode, Integer beginIndex, Integer endIndex) {
    this.contractCode = contractCode;
    this.beginIndex = beginIndex;
    this.endIndex = endIndex;
  }

  public String getContractCode() {
    return contractCode;
  }

  public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
  }

  public Integer getBeginIndex() {
    return beginIndex;
  }

  public void setBeginIndex(Integer beginIndex) {
    this.beginIndex = beginIndex;
  }

  public Integer getEndIndex() {
    return endIndex;
  }

  public void setEndIndex(Integer endIndex) {
    this.endIndex = endIndex;
  }
}
