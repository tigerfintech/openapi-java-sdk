package com.tigerbrokers.stock.openapi.client.https.response.fund;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.fund.item.FundQuoteItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FundQuoteResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<FundQuoteItem> quoteItems;

  public List<FundQuoteItem> getQuoteItems() {
    return quoteItems;
  }

  public void setQuoteItems(List<FundQuoteItem> quoteItems) {
    this.quoteItems = quoteItems;
  }
}
