package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.KlineItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteKlineResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<KlineItem> klineItems;

  public List<KlineItem> getKlineItems() {
    return klineItems;
  }

  public void setKlineItems(List<KlineItem> klineItems) {
    this.klineItems = klineItems;
  }
}
