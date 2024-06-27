package com.tigerbrokers.stock.openapi.client.https.response.option;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.OptionSymbolItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

import java.util.List;

/**
 * Description:
 * Created by liutongping on 2024/05/24.
 */
public class OptionSymbolResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<OptionSymbolItem> symbolItems;

  public List<OptionSymbolItem> getSymbolItems() {
    return symbolItems;
  }

  public void setSymbolItems(List<OptionSymbolItem> symbolItems) {
    this.symbolItems = symbolItems;
  }
}
