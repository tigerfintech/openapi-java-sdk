package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.QuoteOvernight;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

import java.util.List;

/**
 * Description:
 * Created by bean on 2025/02/28.
 */
public class QuoteOvernightResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<QuoteOvernight> data;

  public List<QuoteOvernight> getData() {
    return data;
  }

  public void setData(List<QuoteOvernight> data) {
    this.data = data;
  }
}
