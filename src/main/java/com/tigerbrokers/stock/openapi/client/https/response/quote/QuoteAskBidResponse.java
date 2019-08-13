package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.AskBidItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * 作者：ltc
 * 时间：2019/08/13
 */
public class QuoteAskBidResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<AskBidItem> askBidItems;

  public List<AskBidItem> getAskBidItems() {
    return askBidItems;
  }

  public void setAskBidItems(
      List<AskBidItem> askBidItems) {
    this.askBidItems = askBidItems;
  }
}
