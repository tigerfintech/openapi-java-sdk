package com.tigerbrokers.stock.openapi.client.https.domain.future.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureContractByConCodeModel extends ApiModel {

  @JSONField(name = "contract_code")
  private String contractCode;

  public FutureContractByConCodeModel() {
  }

  public FutureContractByConCodeModel(String contractCode) {
    this.contractCode = contractCode;
  }

  public FutureContractByConCodeModel(String contractCode, Language lang) {
    this.contractCode = contractCode;
    this.lang = lang;
  }

  public String getContractCode() {
    return contractCode;
  }

  public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
  }
}
