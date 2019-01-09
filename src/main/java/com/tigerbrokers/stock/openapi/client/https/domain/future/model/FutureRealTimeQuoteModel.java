package com.tigerbrokers.stock.openapi.client.https.domain.future.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureRealTimeQuoteModel extends ApiModel {

  @JSONField(name = "contract_codes")
  private List<String> contractCodes;

  public FutureRealTimeQuoteModel() {
  }

  public FutureRealTimeQuoteModel(List<String> contractCodes) {
    this.contractCodes = contractCodes;
  }

  public List<String> getContractCodes() {
    return contractCodes;
  }

  public void setContractCodes(List<String> contractCodes) {
    this.contractCodes = contractCodes;
  }
}
