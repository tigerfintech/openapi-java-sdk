package com.tigerbrokers.stock.openapi.client.https.response.fund;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.fund.item.FundHistoryQuoteItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FundHistoryQuoteResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<FundHistoryQuoteItem> quoteItems;

  public List<FundHistoryQuoteItem> getQuoteItems() {
    return quoteItems;
  }

  public void setQuoteItems(
      List<FundHistoryQuoteItem> quoteItems) {
    this.quoteItems = quoteItems;
  }
}
