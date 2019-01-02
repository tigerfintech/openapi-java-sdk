package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.SymbolNameItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteSymbolNameResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<SymbolNameItem> symbolNameItems;

  public List<SymbolNameItem> getSymbolNameItems() {
    return symbolNameItems;
  }

  public void setSymbolNameItems(
      List<SymbolNameItem> symbolNameItems) {
    this.symbolNameItems = symbolNameItems;
  }
}
