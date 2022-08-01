package com.tigerbrokers.stock.openapi.client.https.domain.future.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureContractByExchCodeModel extends ApiModel {

  @JSONField(name = "exchange_code")
  private String exchangeCode;

  public FutureContractByExchCodeModel() {
  }

  public FutureContractByExchCodeModel(String exchangeCode) {
    this.exchangeCode = exchangeCode;
    this.lang = ClientConfig.DEFAULT_CONFIG.getDefaultLanguage();
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
}
