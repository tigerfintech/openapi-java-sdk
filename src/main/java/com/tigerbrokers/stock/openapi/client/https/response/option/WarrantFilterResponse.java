package com.tigerbrokers.stock.openapi.client.https.response.option;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.WarrantFilterItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * Description:
 * Created on 2023/02/02.
 */
public class WarrantFilterResponse extends TigerResponse {

  @JSONField(name = "data")
  private WarrantFilterItem item;

  public WarrantFilterItem getItem() {
    return item;
  }

  public void setItem(WarrantFilterItem item) {
    this.item = item;
  }
}
