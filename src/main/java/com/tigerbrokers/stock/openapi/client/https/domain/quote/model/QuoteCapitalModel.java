package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;

/**
 * Description:
 * Created by bean on 2022/11/14.
 */
public class QuoteCapitalModel extends ApiModel {

  private String symbol;

  private String market;

  public QuoteCapitalModel(String symbol, String market) {
    this(symbol, market, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public QuoteCapitalModel(String symbol, String market, Language lang) {
    this.symbol = symbol;
    this.market = market;
    this.lang = lang;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }
}
