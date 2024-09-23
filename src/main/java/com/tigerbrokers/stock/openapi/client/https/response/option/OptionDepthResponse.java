package com.tigerbrokers.stock.openapi.client.https.response.option;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionDepthItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

import java.util.List;

/**
 * Description:
 * Created by liutongping on 2024/05/23.
 */
public class OptionDepthResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<OptionDepthItem> optionDepthItems;

  public List<OptionDepthItem> getOptionDepthItems() {
    return optionDepthItems;
  }

  public void setOptionDepthItems(List<OptionDepthItem> optionDepthItems) {
    this.optionDepthItems = optionDepthItems;
  }
}
