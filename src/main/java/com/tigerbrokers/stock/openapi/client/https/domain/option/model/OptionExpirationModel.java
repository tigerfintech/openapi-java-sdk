package com.tigerbrokers.stock.openapi.client.https.domain.option.model;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/26.
 */
public class OptionExpirationModel extends ApiModel {

  private List<String> symbols;

  public OptionExpirationModel() {

  }

  public OptionExpirationModel(List<String> symbols) {
    this.symbols = symbols;
  }

  public List<String> getSymbols() {
    return symbols;
  }

  public void setSymbols(List<String> symbols) {
    this.symbols = symbols;
  }

  @Override
  public String toString() {
    return "OptionExpirationModel{" +
        "symbols=" + symbols +
        '}';
  }
}
