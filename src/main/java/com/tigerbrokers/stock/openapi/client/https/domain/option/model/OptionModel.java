/*
 * TigerBrokers
 * Copyright (C) 2014-2024 All Rights Reserved.
 */
package com.tigerbrokers.stock.openapi.client.https.domain.option.model;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;

public class OptionModel extends ApiModel {

  protected Market market;

  public OptionModel() {
  }

  public OptionModel(Market market) {
    this.market = market;
  }

  public OptionModel(Market market, Language language) {
    this.market = market;
    this.lang = language;
  }

  public Market getMarket() {
    return market;
  }

  public void setMarket(Market market) {
    this.market = market;
  }

  @Override
  public String toString() {
    return "OptionModel{" +
        "market=" + market +
        '}';
  }
}