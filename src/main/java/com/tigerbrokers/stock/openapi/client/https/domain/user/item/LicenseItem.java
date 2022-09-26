package com.tigerbrokers.stock.openapi.client.https.domain.user.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by bean on 2022/09/26.
 */
public class LicenseItem extends ApiModel {

  private String license;

  public String getLicense() {
    return license;
  }

  public void setLicense(String license) {
    this.license = license;
  }

  @Override
  public String toString() {
    return "LicenseItem{" +
        "license='" + license + '\'' +
        '}';
  }
}
