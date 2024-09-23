/*
 * TigerBrokers
 * Copyright (C) 2014-2023 All Rights Reserved.
 */
package com.tigerbrokers.stock.openapi.client.https.domain.option.model;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

public class WarrantQuoteModel extends ApiModel {

  private List<String> symbols;

  public WarrantQuoteModel() {
  }

  public WarrantQuoteModel(List<String> symbols) {
    this.symbols = symbols;
  }

  public WarrantQuoteModel symbols(List<String> symbols) {
    this.symbols = symbols;
    return this;
  }

  public List<String> getSymbols() {
    return symbols;
  }

  public void setSymbols(List<String> symbols) {
    this.symbols = symbols;
  }
}