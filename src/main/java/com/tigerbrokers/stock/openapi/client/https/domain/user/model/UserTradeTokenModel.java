package com.tigerbrokers.stock.openapi.client.https.domain.user.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by lijiawen on 2019/03/13.
 */
public class UserTradeTokenModel extends ApiModel {

  @JSONField(name = "trade_password")
  private String tradePassword;

  public UserTradeTokenModel(String tradePassword) {
    this.tradePassword = tradePassword;
  }

  public String getTradePassword() {
    return tradePassword;
  }

  public void setTradePassword(String tradePassword) {
    this.tradePassword = tradePassword;
  }
}
