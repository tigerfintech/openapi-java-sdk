package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

public class SearchSymbolItem  extends ApiModel {

  private String symbol;
  private String name;
  private String market;
  private String nameCN;
  private Integer type;
  private Float halted;
  private List<String> labels;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public String getNameCN() {
    return nameCN;
  }

  public void setNameCN(String nameCN) {
    this.nameCN = nameCN;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Float getHalted() {
    return halted;
  }

  public void setHalted(Float halted) {
    this.halted = halted;
  }

  public List<String> getLabels() {
    return labels;
  }

  public void setLabels(List<String> labels) {
    this.labels = labels;
  }

  @Override
  public String toString() {
    return "SearchSymbolItem{" +
        "symbol='" + symbol + '\'' +
        ", name='" + name + '\'' +
        ", market='" + market + '\'' +
        ", nameCN='" + nameCN + '\'' +
        ", type=" + type +
        ", halted=" + halted +
        ", labels=" + labels +
        '}';
  }
}