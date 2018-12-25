package com.tigerbrokers.stock.openapi.client.https.domain.future.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by lijiawen on 2018/12/21.
 */
public class FutureExchangeModel extends ApiModel {

  @JSONField(name = "sec_type")
  private String secType;
  private String lang;

  public FutureExchangeModel() {
  }

  public FutureExchangeModel(String secType) {
    this.secType = secType;
  }

  public FutureExchangeModel(String secType, String lang) {
    this.secType = secType;
    this.lang = lang;
  }

  public String getSecType() {
    return secType;
  }

  public void setSecType(String secType) {
    this.secType = secType;
  }

  public String getLang() {
    return lang;
  }

  public void setLang(String lang) {
    this.lang = lang;
  }
}
