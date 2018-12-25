package com.tigerbrokers.stock.openapi.client.https.domain.future.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureContractByExchCodeModel extends ApiModel {

  @JSONField(name = "exchange_code")
  private String exchangeCode;
  private String lang;

  public FutureContractByExchCodeModel() {
  }

  public FutureContractByExchCodeModel(String exchangeCode) {
    this.exchangeCode = exchangeCode;
  }

  public FutureContractByExchCodeModel(String exchangeCode, String lang) {
    this.exchangeCode = exchangeCode;
    this.lang = lang;
  }

  public String getExchangeCode() {
    return exchangeCode;
  }

  public void setExchangeCode(String exchangeCode) {
    this.exchangeCode = exchangeCode;
  }

  public String getLang() {
    return lang;
  }

  public void setLang(String lang) {
    this.lang = lang;
  }
}
