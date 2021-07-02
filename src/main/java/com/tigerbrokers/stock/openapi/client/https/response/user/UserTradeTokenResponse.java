package com.tigerbrokers.stock.openapi.client.https.response.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.user.item.UserTradeTokenItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * Description:
 * Created by lijiawen on 2019/03/13.
 */
public class UserTradeTokenResponse extends TigerResponse {

  @JSONField(name = "data")
  private UserTradeTokenItem userTradeTokenItem;

  public UserTradeTokenItem getUserTradeTokenItem() {
    return userTradeTokenItem;
  }

  public void setUserTradeTokenItem(UserTradeTokenItem userTradeTokenItem) {
    this.userTradeTokenItem = userTradeTokenItem;
  }
}
