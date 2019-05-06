package com.tigerbrokers.stock.openapi.client.https.domain.user.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by lijiawen on 2019/05/05.
 */
public class UserTradePasswordVerifyModel extends ApiModel {

  @JSONField(name = "id_no")
  private String idNo;

  public UserTradePasswordVerifyModel(String idNo) {
    this.idNo = idNo;
  }

  public String getIdNo() {
    return idNo;
  }

  public void setIdNo(String idNo) {
    this.idNo = idNo;
  }
}
