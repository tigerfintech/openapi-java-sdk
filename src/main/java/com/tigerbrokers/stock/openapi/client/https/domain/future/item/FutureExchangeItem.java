package com.tigerbrokers.stock.openapi.client.https.domain.future.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by lijiawen on 2018/12/24.
 */
public class FutureExchangeItem extends ApiModel {

  private String code;
  private String name;
  private String zoneId;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getZoneId() {
    return zoneId;
  }

  public void setZoneId(String zoneId) {
    this.zoneId = zoneId;
  }

  @Override
  public String toString() {
    return "FutureExchangeItem{" +
        "code='" + code + '\'' +
        ", name='" + name + '\'' +
        ", zoneId='" + zoneId + '\'' +
        '}';
  }
}
