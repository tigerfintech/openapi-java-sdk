package com.tigerbrokers.stock.openapi.client.https.response.option;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionTradeTickItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/26.
 */
public class OptionTradeTickResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<OptionTradeTickItem> optionTradeTickItems;

  public List<OptionTradeTickItem> getOptionTradeTickItems() {
    return optionTradeTickItems;
  }

  public void setOptionTradeTickItems(List<OptionTradeTickItem> optionTradeTickItems) {
    this.optionTradeTickItems = optionTradeTickItems;
  }
}
