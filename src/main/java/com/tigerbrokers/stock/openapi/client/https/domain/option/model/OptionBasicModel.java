/*
 * TigerBrokers
 * Copyright (C) 2014-2024 All Rights Reserved.
 */
package com.tigerbrokers.stock.openapi.client.https.domain.option.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class OptionBasicModel extends OptionModel {

  @JSONField(name = "option_basic")
  private List<OptionCommonModel> optionBasic;

  public List<OptionCommonModel> getOptionBasic() {
    return optionBasic;
  }

  public void setOptionBasic(List<OptionCommonModel> optionBasic) {
    this.optionBasic = optionBasic;
  }

  @Override
  public String toString() {
    return "OptionBasicModel{" +
        "market=" + market +
        ", optionBasic=" + optionBasic +
        '}';
  }
}