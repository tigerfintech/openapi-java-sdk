package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.StockBrokerItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * Description:
 * Created by bean on 2022/11/14.
 */
public class QuoteStockBrokerResponse extends TigerResponse {
  @JSONField(name = "data")
  private StockBrokerItem stockBrokerItem;

  public StockBrokerItem getStockBrokerItem() {
    return stockBrokerItem;
  }

  public void setStockBrokerItem(StockBrokerItem stockBrokerItem) {
    this.stockBrokerItem = stockBrokerItem;
  }
}
