package com.tigerbrokers.stock.openapi.client.https.response.option;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.WarrantQuoteItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * Description:
 * Created on 2023/02/02.
 */
public class WarrantQuoteResponse extends TigerResponse {

  @JSONField(name = "data")
  private WarrantQuoteItem item;

  public WarrantQuoteItem getItem() {
    return item;
  }

  public void setItem(WarrantQuoteItem item) {
    this.item = item;
  }
}
