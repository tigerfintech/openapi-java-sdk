package com.tigerbrokers.stock.openapi.client.https.response.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.user.item.UserTokenItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * Description:
 * Created by bean on 2023/02/08.
 */
public class UserTokenResponse extends TigerResponse {

  @JSONField(name = "data")
  private UserTokenItem userToken;

  public UserTokenItem getUserToken() {
    return userToken;
  }

  public void setUserToken(UserTokenItem userToken) {
    this.userToken = userToken;
  }
}
