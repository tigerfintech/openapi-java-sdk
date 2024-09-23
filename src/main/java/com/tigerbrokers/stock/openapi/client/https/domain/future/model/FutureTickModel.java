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
  private long beginIndex;
  @JSONField(name = "end_index")
  private long endIndex;
  private int limit;

  private static final int DEFAULT_LIMIT = 200;

  public FutureTickModel() {
  }

  public FutureTickModel(String contractCode) {
    this.contractCode = contractCode;
  }

  public FutureTickModel(String contractCode, long beginIndex, long endIndex) {
    this.contractCode = contractCode;
    this.beginIndex = beginIndex;
    this.endIndex = endIndex;
    this.limit = DEFAULT_LIMIT;
  }

  public FutureTickModel(String contractCode, long beginIndex, long endIndex,int limit) {
    this.contractCode = contractCode;
    this.beginIndex = beginIndex;
    this.endIndex = endIndex;
    this.limit = limit;
  }

  public String getContractCode() {
    return contractCode;
  }

  public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
  }

  public long getBeginIndex() {
    return beginIndex;
  }

  public void setBeginIndex(long beginIndex) {
    this.beginIndex = beginIndex;
  }

  public long getEndIndex() {
    return endIndex;
  }

  public void setEndIndex(long endIndex) {
    this.endIndex = endIndex;
  }

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }
}
