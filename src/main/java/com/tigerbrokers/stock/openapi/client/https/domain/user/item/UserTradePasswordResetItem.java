package com.tigerbrokers.stock.openapi.client.https.domain.user.item;

/**
 * Description:
 * Created by lijiawen on 2019/05/05.
 */
public class UserTradePasswordResetItem {

  private String message;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "UserTradePasswordResetItem{" +
        "message='" + message + '\'' +
        '}';
  }
}
