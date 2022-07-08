package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.TradeCalendar;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by bean on 2022/06/23.
 */
public class QuoteTradeCalendarResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<TradeCalendar> items;

  public List<TradeCalendar> getItems() {
    return items;
  }

  public void setItems(List<TradeCalendar> items) {
    this.items = items;
  }
}
