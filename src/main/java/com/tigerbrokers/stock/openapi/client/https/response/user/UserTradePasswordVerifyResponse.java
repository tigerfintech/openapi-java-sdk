package com.tigerbrokers.stock.openapi.client.https.response.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.user.item.UserTradePasswordVerifyItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * Description:
 * Created by lijiawen on 2019/05/05.
 */
public class UserTradePasswordVerifyResponse extends TigerResponse {

  @JSONField(name = "data")
  private UserTradePasswordVerifyItem userTradePasswordVerifyItem;

  public UserTradePasswordVerifyItem getUserTradePasswordVerifyItem() {
    return userTradePasswordVerifyItem;
  }

  public void setUserTradePasswordVerifyItem(
      UserTradePasswordVerifyItem userTradePasswordVerifyItem) {
    this.userTradePasswordVerifyItem = userTradePasswordVerifyItem;
  }
}
