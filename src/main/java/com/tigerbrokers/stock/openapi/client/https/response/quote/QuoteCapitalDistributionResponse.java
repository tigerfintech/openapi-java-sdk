package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.CapitalDistributionItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * Description:
 * Created by bean on 2022/11/14.
 */
public class QuoteCapitalDistributionResponse extends TigerResponse {
  @JSONField(name = "data")
  private CapitalDistributionItem capitalDistributionItem;

  public CapitalDistributionItem getCapitalDistributionItem() {
    return capitalDistributionItem;
  }

  public void setCapitalDistributionItem(
      CapitalDistributionItem capitalDistributionItem) {
    this.capitalDistributionItem = capitalDistributionItem;
  }
}
