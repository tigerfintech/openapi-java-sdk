package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.TradeTickItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteTradeTickResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<TradeTickItem> tradeTickItems;

  public List<TradeTickItem> getTradeTickItems() {
    return tradeTickItems;
  }

  public void setTradeTickItems(List<TradeTickItem> tradeTickItems) {
    this.tradeTickItems = tradeTickItems;
  }
}
