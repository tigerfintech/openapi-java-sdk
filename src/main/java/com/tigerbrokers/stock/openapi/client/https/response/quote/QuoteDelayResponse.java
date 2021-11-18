package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.QuoteDelayItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by sk on 2021/11/18.
 */
public class QuoteDelayResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<QuoteDelayItem> quoteDelayItems;

  public List<QuoteDelayItem> getQuoteDelayItems() {
    return quoteDelayItems;
  }

  public void setQuoteDelayItems(List<QuoteDelayItem> quoteDelayItems) {
    this.quoteDelayItems = quoteDelayItems;
  }
}
