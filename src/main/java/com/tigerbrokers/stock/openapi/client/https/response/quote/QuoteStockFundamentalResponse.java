package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.StockFundamental;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.StockFundamentalItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

import java.util.Collections;
import java.util.List;

/**
 * author：bean
 * date：2024/12/19
 */
public class QuoteStockFundamentalResponse extends TigerResponse {

  @JSONField(name = "data")
  private StockFundamental data;

  public void setData(StockFundamental data) {
    this.data = data;
  }

  public List<StockFundamentalItem> getStockFundamentalItems() {
    return data == null ? Collections.emptyList() : data.getItems();
  }
}
