package com.tigerbrokers.stock.openapi.client.https.domain.future.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureTickModel extends ApiModel {

  @JSONField(name = "contract_codes")
  private List<String> contractCodes;
  @JSONField(name = "begin_index")
  private Integer beginIndex;
  @JSONField(name = "end_index")
  private Integer endIndex;

  public FutureTickModel() {
  }

  public FutureTickModel(List<String> contractCodes) {
    this.contractCodes = contractCodes;
  }

  public FutureTickModel(List<String> contractCodes, Integer beginIndex, Integer endIndex) {
    this.contractCodes = contractCodes;
    this.beginIndex = beginIndex;
    this.endIndex = endIndex;
  }

  public List<String> getContractCodes() {
    return contractCodes;
  }

  public void setContractCodes(List<String> contractCodes) {
    this.contractCodes = contractCodes;
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
