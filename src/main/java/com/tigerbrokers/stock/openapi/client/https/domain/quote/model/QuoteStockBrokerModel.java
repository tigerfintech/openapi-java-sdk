package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;

/**
 * Description:
 * Created by bean on 2022/11/14.
 */
public class QuoteStockBrokerModel extends ApiModel {

  private String symbol;

  private Integer limit;

  public QuoteStockBrokerModel() {
    this.lang = ClientConfig.DEFAULT_CONFIG.getDefaultLanguage();
  }

  public QuoteStockBrokerModel(String symbol) {
    this(symbol, null, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public QuoteStockBrokerModel(String symbol, Language lang) {
    this(symbol, null, lang);
  }

  public QuoteStockBrokerModel(String symbol, Integer limit) {
    this(symbol, limit, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public QuoteStockBrokerModel(String symbol, Integer limit, Language lang) {
    this.symbol = symbol;
    this.limit = limit;
    this.lang = lang;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }
}
