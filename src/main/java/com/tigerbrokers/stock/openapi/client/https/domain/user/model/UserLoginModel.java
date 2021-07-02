package com.tigerbrokers.stock.openapi.client.https.domain.user.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.GrantType;

/**
 * Description:
 * Created by lijiawen on 2019/03/13.
 */
public class UserLoginModel extends ApiModel {

  @JSONField(name = "user_name")
  private String userName;

  @JSONField(name = "login_password")
  private String password;

  @JSONField(name = "grant_type")
  private String grantType = GrantType.phone.name();

  public UserLoginModel(String userName, String password) {
    this.userName = userName;
    this.password = password;
  }

  public UserLoginModel(String userName, String password, GrantType grantType) {
    this.userName = userName;
    this.password = password;
    if (grantType != null) {
      this.grantType = grantType.name();
    }
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getGrantType() {
    return grantType;
  }

  public void setGrantType(String grantType) {
    this.grantType = grantType;
  }
}
