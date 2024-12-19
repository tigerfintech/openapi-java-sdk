package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.StockFundamentalItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

import java.util.List;

/**
 * author：bean
 * date：2024/12/19
 */
public class QuoteStockFundamentalResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<StockFundamentalItem> stockFundamentalItems;

  public List<StockFundamentalItem> getStockFundamentalItems() {
    return stockFundamentalItems;
  }

  public void setStockFundamentalItems(List<StockFundamentalItem> stockFundamentalItems) {
    this.stockFundamentalItems = stockFundamentalItems;
  }
}
