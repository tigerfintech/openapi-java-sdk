package com.tigerbrokers.stock.openapi.client.https.domain.user.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by bean on 2023/02/08.
 */
public class UserTokenItem extends ApiModel {

  private String tigerId;
  private String license;
  private String token;
  private Long createTime;
  private Long expriedTime;

  public String getTigerId() {
    return tigerId;
  }

  public void setTigerId(String tigerId) {
    this.tigerId = tigerId;
  }

  public String getLicense() {
    return license;
  }

  public void setLicense(String license) {
    this.license = license;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Long createTime) {
    this.createTime = createTime;
  }

  public Long getExpriedTime() {
    return expriedTime;
  }

  public void setExpriedTime(Long expriedTime) {
    this.expriedTime = expriedTime;
  }

  @Override
  public String toString() {
    return "UserTokenItem{" +
        "tigerId='" + tigerId + '\'' +
        ", license='" + license + '\'' +
        ", token='" + token + '\'' +
        ", createTime='" + createTime + '\'' +
        ", expriedTime='" + expriedTime + '\'' +
        '}';
  }
}
