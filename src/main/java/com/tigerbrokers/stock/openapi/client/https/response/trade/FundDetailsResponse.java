package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.FundDetailsPageItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * Created on 2025/4/23
 *
 * @author: sukai
 */
public class FundDetailsResponse extends TigerResponse {

  @JSONField(name = "data")
  private FundDetailsPageItem item;

  public FundDetailsPageItem getItem() {
    return item;
  }

  public void setItem(FundDetailsPageItem item) {
    this.item = item;
  }
}
