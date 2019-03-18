package com.tigerbrokers.stock.openapi.client.https.response.option;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionKlineItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/26.
 */
public class OptionKlineResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<OptionKlineItem> klineItems;

  public List<OptionKlineItem> getKlineItems() {
    return klineItems;
  }

  public void setKlineItems(List<OptionKlineItem> klineItems) {
    this.klineItems = klineItems;
  }
}
