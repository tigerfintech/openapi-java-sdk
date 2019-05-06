package com.tigerbrokers.stock.openapi.client.https.response.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.user.item.UserTradePasswordResetItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * Description:
 * Created by lijiawen on 2019/05/05.
 */
public class UserTradePasswordResetResponse extends TigerResponse {

  @JSONField(name = "data")
  private UserTradePasswordResetItem userTradePasswordResetItem;

  public UserTradePasswordResetItem getUserTradePasswordResetItem() {
    return userTradePasswordResetItem;
  }

  public void setUserTradePasswordResetItem(
      UserTradePasswordResetItem userTradePasswordResetItem) {
    this.userTradePasswordResetItem = userTradePasswordResetItem;
  }
}
