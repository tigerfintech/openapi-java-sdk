package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteMarketModel extends ApiModel {

  private Market market;
  @JSONField(name = "lang")
  private Language language;

  public QuoteMarketModel() {
  }

  public QuoteMarketModel(Market market) {
    this.market = market;
  }

  public QuoteMarketModel(Market market, Language language) {
    this.market = market;
    this.language = language;
  }

  public Market getMarket() {
    return market;
  }

  public void setMarket(Market market) {
    this.market = market;
  }

  public Language getLanguage() {
    return language;
  }

  public void setLanguage(Language language) {
    this.language = language;
  }
}
