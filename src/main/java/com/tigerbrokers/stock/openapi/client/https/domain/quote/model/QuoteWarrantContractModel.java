package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteWarrantContractModel extends QuoteSymbolModel {

  @JSONField(name = "sec_type")
  private SecType secType;

  public QuoteWarrantContractModel() {
  }

  public QuoteWarrantContractModel(List<String> symbols) {
    super(symbols);
  }

  public QuoteWarrantContractModel(List<String> symbols, SecType secType) {
    super(symbols);
    this.secType = secType;
  }

  public QuoteWarrantContractModel(List<String> symbols, SecType secType, Language lang) {
    super(symbols, lang);
    this.secType = secType;
  }

  public SecType getSecType() {
    return secType;
  }

  public void setSecType(SecType secType) {
    this.secType = secType;
  }
}
