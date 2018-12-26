package com.tigerbrokers.stock.openapi.client.https.domain.future.model;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureContinuousContractModel extends ApiModel {

  private String type;
  private Language lang;

  public FutureContinuousContractModel() {
  }

  public FutureContinuousContractModel(String type) {
    this.type = type;
  }

  public FutureContinuousContractModel(String type, Language lang) {
    this.type = type;
    this.lang = lang;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Language getLang() {
    return lang;
  }

  public void setLang(Language lang) {
    this.lang = lang;
  }
}
