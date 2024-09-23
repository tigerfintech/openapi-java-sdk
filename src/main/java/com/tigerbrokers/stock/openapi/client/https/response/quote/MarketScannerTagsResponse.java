package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.MarketScannerTagItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 *
 * @author kevin
 * @date 2023/04/20
 */
public class MarketScannerTagsResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<MarketScannerTagItem> items;

  public List<MarketScannerTagItem> getItems() {
    return items;
  }

  public void setItems(List<MarketScannerTagItem> items) {
    this.items = items;
  }
}
