package com.tigerbrokers.stock.openapi.client.https.response.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.user.item.LicenseItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * Description:
 * Created by bean on 2022/12/13.
 */
public class UserLicenseResponse extends TigerResponse {

  @JSONField(name = "data")
  private LicenseItem licenseItem;

  public LicenseItem getLicenseItem() {
    return licenseItem;
  }

  public void setLicenseItem(LicenseItem licenseItem) {
    this.licenseItem = licenseItem;
  }
}
