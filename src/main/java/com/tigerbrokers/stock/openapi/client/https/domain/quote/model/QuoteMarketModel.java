package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.PackageName;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteMarketModel extends ApiModel {

  private Market market;
  @JSONField(name = "package_name")
  private PackageName packageName;

  public QuoteMarketModel() {
  }

  public QuoteMarketModel(Market market) {
    this.market = market;
  }

  public QuoteMarketModel(Market market, Language language) {
    this.market = market;
    this.lang = language;
  }

  public QuoteMarketModel(PackageName packageName) {
    this.packageName = packageName;
  }

  public Market getMarket() {
    return market;
  }

  public void setMarket(Market market) {
    this.market = market;
  }

  public Language getLanguage() {
    return lang;
  }

  public void setLanguage(Language language) {
    this.lang = language;
  }

  public PackageName getPackageName() {
    return packageName;
  }

  public void setPackageName(PackageName packageName) {
    this.packageName = packageName;
  }
}
