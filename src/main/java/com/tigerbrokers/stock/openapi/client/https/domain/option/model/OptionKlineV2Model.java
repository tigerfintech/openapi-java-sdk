/*
 * TigerBrokers
 * Copyright (C) 2014-2024 All Rights Reserved.
 */
package com.tigerbrokers.stock.openapi.client.https.domain.option.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class OptionKlineV2Model extends OptionModel {

  @JSONField(name = "option_query")
  private List<OptionKlineModel> optionQuery;

  public List<OptionKlineModel> getOptionQuery() {
    return optionQuery;
  }

  public void setOptionQuery(List<OptionKlineModel> optionQuery) {
    this.optionQuery = optionQuery;
  }

  @Override
  public String toString() {
    return "OptionKlineV2Model{" +
        "market=" + market +
        ", optionQuery=" + optionQuery +
        '}';
  }
}