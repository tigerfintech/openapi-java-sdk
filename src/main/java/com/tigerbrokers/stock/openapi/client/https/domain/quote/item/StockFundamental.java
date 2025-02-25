package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import java.util.List;

/**
 * author：bean
 * date：2024/12/19
 */
public class StockFundamental {
  private List<StockFundamentalItem> items;

  public List<StockFundamentalItem> getItems() {
    return items;
  }

  public void setItems(List<StockFundamentalItem> items) {
    this.items = items;
  }
}
