package com.tigerbrokers.stock.openapi.client.https.domain.future.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureContractByExchCodeModel extends ApiModel {

  @JSONField(name = "exchange_code")
  private String exchangeCode;
  private Language lang;

  public FutureContractByExchCodeModel() {
  }

  public FutureContractByExchCodeModel(String exchangeCode) {
    this.exchangeCode = exchangeCode;
  }

  public FutureContractByExchCodeModel(String exchangeCode, Language lang) {
    this.exchangeCode = exchangeCode;
    this.lang = lang;
  }

  public String getExchangeCode() {
    return exchangeCode;
  }

  public void setExchangeCode(String exchangeCode) {
    this.exchangeCode = exchangeCode;
  }

  public Language getLang() {
    return lang;
  }

  public void setLang(Language lang) {
    this.lang = lang;
  }
}
