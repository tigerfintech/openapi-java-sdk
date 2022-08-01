package com.tigerbrokers.stock.openapi.client.https.domain.future.model;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureContinuousContractModel extends ApiModel {

  private String type;

  public FutureContinuousContractModel() {
  }

  public FutureContinuousContractModel(String type) {
    this.type = type;
    this.lang = ClientConfig.DEFAULT_CONFIG.getDefaultLanguage();
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
}
