package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.QuoteStockTradeItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2019/01/07.
 */
public class QuoteStockTradeResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<QuoteStockTradeItem> stockTradeItems;

  public List<QuoteStockTradeItem> getStockTradeItems() {
    return stockTradeItems;
  }

  public void setStockTradeItems(List<QuoteStockTradeItem> stockTradeItems) {
    this.stockTradeItems = stockTradeItems;
  }
}
