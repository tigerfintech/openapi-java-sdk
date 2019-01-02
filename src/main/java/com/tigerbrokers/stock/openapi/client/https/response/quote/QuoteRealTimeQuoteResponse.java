package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.RealTimeQuoteItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteRealTimeQuoteResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<RealTimeQuoteItem> realTimeQuoteItems;

  public List<RealTimeQuoteItem> getRealTimeQuoteItems() {
    return realTimeQuoteItems;
  }

  public void setRealTimeQuoteItems(List<RealTimeQuoteItem> realTimeQuoteItems) {
    this.realTimeQuoteItems = realTimeQuoteItems;
  }
}
