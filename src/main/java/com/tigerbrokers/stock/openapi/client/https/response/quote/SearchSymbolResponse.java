package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.SearchSymbolItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

public class SearchSymbolResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<SearchSymbolItem> searchSymbolItems;

  public List<SearchSymbolItem> getSearchSymbolItems() {
    return searchSymbolItems;
  }

  public void setSearchSymbolItems(
      List<SearchSymbolItem> searchSymbolItems) {
    this.searchSymbolItems = searchSymbolItems;
  }
}
