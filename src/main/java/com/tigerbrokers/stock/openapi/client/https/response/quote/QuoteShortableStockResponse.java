package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.ShortableStockItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteShortableStockResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<ShortableStockItem> shortableStockItems;

  public List<ShortableStockItem> getShortableStockItems() {
    return shortableStockItems;
  }

  public void setShortableStockItems(List<ShortableStockItem> shortableStockItems) {
    this.shortableStockItems = shortableStockItems;
  }
}
