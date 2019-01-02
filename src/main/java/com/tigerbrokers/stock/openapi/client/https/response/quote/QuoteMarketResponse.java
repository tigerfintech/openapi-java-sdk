package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.MarketItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteMarketResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<MarketItem> marketItems;

  public List<MarketItem> getMarketItems() {
    return marketItems;
  }

  public void setMarketItems(List<MarketItem> marketItems) {
    this.marketItems = marketItems;
  }
}
