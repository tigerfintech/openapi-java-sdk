package com.tigerbrokers.stock.openapi.client.https.domain.user.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by lijiawen on 2019/05/05.
 */
public class UserTradePasswordResetModel extends ApiModel {

  @JSONField(name = "id_no")
  private String idNo;
  @JSONField(name = "trade_password")
  private String tradePassword;
  @JSONField(name = "verify_code")
  private String verifyCode;

  public UserTradePasswordResetModel(String idNo, String tradePassword, String verifyCode) {
    this.idNo = idNo;
    this.tradePassword = tradePassword;
    this.verifyCode = verifyCode;
  }

  public String getIdNo() {
    return idNo;
  }

  public void setIdNo(String idNo) {
    this.idNo = idNo;
  }

  public String getTradePassword() {
    return tradePassword;
  }

  public void setTradePassword(String tradePassword) {
    this.tradePassword = tradePassword;
  }

  public String getVerifyCode() {
    return verifyCode;
  }

  public void setVerifyCode(String verifyCode) {
    this.verifyCode = verifyCode;
  }
}
