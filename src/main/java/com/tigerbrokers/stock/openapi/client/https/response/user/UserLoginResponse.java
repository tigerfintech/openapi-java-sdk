package com.tigerbrokers.stock.openapi.client.https.response.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.user.item.UserLoginItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * Description:
 * Created by lijiawen on 2019/03/13.
 */
public class UserLoginResponse extends TigerResponse {

  @JSONField(name = "data")
  private UserLoginItem userLoginItem;

  public UserLoginItem getUserLoginItem() {
    return userLoginItem;
  }

  public void setUserLoginItem(UserLoginItem userLoginItem) {
    this.userLoginItem = userLoginItem;
  }
}
