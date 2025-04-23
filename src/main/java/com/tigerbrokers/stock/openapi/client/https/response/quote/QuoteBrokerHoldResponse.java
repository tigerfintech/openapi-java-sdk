package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.BrokerHoldPageItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * Created on 2025/4/15
 *
 * @author: sukai
 */
public class QuoteBrokerHoldResponse extends TigerResponse {

  @JSONField(name = "data")
  private BrokerHoldPageItem brokerHoldPageItem;

  public BrokerHoldPageItem getBrokerHoldPageItem() {
    return brokerHoldPageItem;
  }

  public void setBrokerHoldPageItem(BrokerHoldPageItem brokerHoldPageItem) {
    this.brokerHoldPageItem = brokerHoldPageItem;
  }
}
