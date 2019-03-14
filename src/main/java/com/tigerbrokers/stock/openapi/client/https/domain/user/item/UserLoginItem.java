package com.tigerbrokers.stock.openapi.client.https.domain.user.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by lijiawen on 2019/03/13.
 */
public class UserLoginItem extends ApiModel {

  private String accessToken;
  private String refreshToken;
  private long expiresIn;
  private long uuid;

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public long getExpiresIn() {
    return expiresIn;
  }

  public void setExpiresIn(long expiresIn) {
    this.expiresIn = expiresIn;
  }

  public long getUuid() {
    return uuid;
  }

  public void setUuid(long uuid) {
    this.uuid = uuid;
  }

  @Override
  public String toString() {
    return "UserLoginItem{" +
        "accessToken='" + accessToken + '\'' +
        ", refreshToken='" + refreshToken + '\'' +
        ", expiresIn=" + expiresIn +
        ", uuid=" + uuid +
        '}';
  }
}
