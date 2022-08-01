package com.tigerbrokers.stock.openapi.client.https.domain.future.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;

/**
 * Description:
 * Created by lijiawen on 2018/12/21.
 */
public class FutureExchangeModel extends ApiModel {

  @JSONField(name = "sec_type")
  private String secType;

  public FutureExchangeModel() {
  }

  public FutureExchangeModel(String secType) {
    this.secType = secType;
  }

  public FutureExchangeModel(String secType, Language lang) {
    this.secType = secType;
    this.lang = lang;
  }

  public String getSecType() {
    return secType;
  }

  public void setSecType(String secType) {
    this.secType = secType;
  }
}
