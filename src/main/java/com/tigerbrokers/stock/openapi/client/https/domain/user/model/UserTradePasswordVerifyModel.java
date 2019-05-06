package com.tigerbrokers.stock.openapi.client.https.domain.user.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by lijiawen on 2019/05/05.
 */
public class UserTradePasswordVerifyModel extends ApiModel {

  @JSONField(name = "access_token")
  private String accessToken;

  @JSONField(name = "id_no")
  private String idNo;

  public UserTradePasswordVerifyModel(String accessToken, String idNo) {
    this.accessToken = accessToken;
    this.idNo = idNo;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getIdNo() {
    return idNo;
  }

  public void setIdNo(String idNo) {
    this.idNo = idNo;
  }
}
