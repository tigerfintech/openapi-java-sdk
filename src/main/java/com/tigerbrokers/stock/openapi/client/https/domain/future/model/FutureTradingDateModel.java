package com.tigerbrokers.stock.openapi.client.https.domain.future.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureTradingDateModel extends ApiModel {

  @JSONField(name = "contract_code")
  private String contractCode;
  @JSONField(name = "trading_date")
  private Long tradingDate;

  public FutureTradingDateModel() {
  }

  public FutureTradingDateModel(String contractCode) {
    this.contractCode = contractCode;
  }

  public FutureTradingDateModel(String contractCode, Long tradingDate) {
    this.contractCode = contractCode;
    this.tradingDate = tradingDate;
  }

  public String getContractCode() {
    return contractCode;
  }

  public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
  }

  public Long getTradingDate() {
    return tradingDate;
  }

  public void setTradingDate(Long tradingDate) {
    this.tradingDate = tradingDate;
  }
}
