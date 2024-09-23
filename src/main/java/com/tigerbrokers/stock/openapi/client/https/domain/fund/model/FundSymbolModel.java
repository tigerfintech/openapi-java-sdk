package com.tigerbrokers.stock.openapi.client.https.domain.fund.model;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import java.util.List;

/**
 * Description:
 * Created by bean on 2023/07/25.
 */
public class FundSymbolModel extends ApiModel {

  private List<String> symbols;

  public FundSymbolModel() {
    this.lang = ClientConfig.DEFAULT_CONFIG.getDefaultLanguage();
  }

  public FundSymbolModel(List<String> symbols) {
    this(symbols, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public FundSymbolModel(List<String> symbols, Language lang) {
    this.symbols = symbols;
    this.lang = lang;
  }

  public List<String> getSymbols() {
    return symbols;
  }

  public void setSymbols(List<String> symbols) {
    this.symbols = symbols;
  }
}
