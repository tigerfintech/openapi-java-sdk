package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteSymbolModel extends ApiModel {

  private List<String> symbols;
  @JSONField(name = "include_hour_trading")
  private Boolean includeHourTrading;

  public QuoteSymbolModel() {
    this.lang = ClientConfig.DEFAULT_CONFIG.getDefaultLanguage();
  }

  public QuoteSymbolModel(List<String> symbols) {
    this(symbols, false);
  }

  public QuoteSymbolModel(List<String> symbols, Language lang) {
    this(symbols, false, lang);
  }

  public QuoteSymbolModel(List<String> symbols, Boolean includeHourTrading) {
    this(symbols, includeHourTrading, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public QuoteSymbolModel(List<String> symbols, Boolean includeHourTrading, Language lang) {
    this.symbols = symbols;
    this.includeHourTrading = includeHourTrading;
    this.lang = lang;
  }

  public List<String> getSymbols() {
    return symbols;
  }

  public void setSymbols(List<String> symbols) {
    this.symbols = symbols;
  }

  public Boolean isIncludeHourTrading() {
    return includeHourTrading;
  }

  public void setIncludeHourTrading(Boolean includeHourTrading) {
    this.includeHourTrading = includeHourTrading;
  }
}
