package com.tigerbrokers.stock.openapi.client.https.domain.future.model;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureCurrentContractModel extends ApiModel {

  private String type;
  private String lang;

  public FutureCurrentContractModel() {
  }

  public FutureCurrentContractModel(String type) {
    this.type = type;
  }

  public FutureCurrentContractModel(String type, String lang) {
    this.type = type;
    this.lang = lang;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getLang() {
    return lang;
  }

  public void setLang(String lang) {
    this.lang = lang;
  }
}
