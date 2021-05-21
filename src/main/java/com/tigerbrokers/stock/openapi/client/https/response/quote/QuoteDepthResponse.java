package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.QuoteDepthItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * 作者：ltc
 * 时间：2019/08/13
 */
public class QuoteDepthResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<QuoteDepthItem> quoteDepthItems;

  public List<QuoteDepthItem> getQuoteDepthItems() {
    return quoteDepthItems;
  }

  public void setQuoteDepthItems(List<QuoteDepthItem> quoteDepthItems) {
    this.quoteDepthItems = quoteDepthItems;
  }
}
