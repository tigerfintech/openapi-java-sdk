package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.WarrantItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteWarrantContractResponse extends TigerResponse {

  @JSONField(name = "data")
  private WarrantItem warrantItem;

  public WarrantItem getWarrantItem() {
    return warrantItem;
  }

  public void setWarrantItem(WarrantItem warrantItem) {
    this.warrantItem = warrantItem;
  }
}
