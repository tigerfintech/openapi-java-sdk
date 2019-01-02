package com.tigerbrokers.stock.openapi.client.https.domain.future.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureRealTimeQuoteModel extends ApiModel {

  @JSONField(name = "contract_code")
  private String contractCode;

  public FutureRealTimeQuoteModel() {
  }

  public FutureRealTimeQuoteModel(String contractCode) {
    this.contractCode = contractCode;
  }

  public String getContractCode() {
    return contractCode;
  }

  public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
  }
}
