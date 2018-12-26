package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteSymbolModel extends ApiModel {

  private List<String> symbols;
  @JSONField(name = "lang")
  private Language lang;

  public QuoteSymbolModel() {
  }

  public QuoteSymbolModel(List<String> symbols) {
    this.symbols = symbols;
  }

  public QuoteSymbolModel(List<String> symbols, Language lang) {
    this.symbols = symbols;
    this.lang = lang;
  }

  public List<String> getSymbols() {
    return symbols;
  }

  public void setSymbols(List<String> symbols) {
    this.symbols = symbols;
  }

  public Language getLang() {
    return lang;
  }

  public void setLang(Language lang) {
    this.lang = lang;
  }
}
