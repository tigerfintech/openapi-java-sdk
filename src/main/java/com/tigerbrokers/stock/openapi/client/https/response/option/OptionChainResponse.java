package com.tigerbrokers.stock.openapi.client.https.response.option;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionChainItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/26.
 */
public class OptionChainResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<OptionChainItem> optionChainItems;

  public List<OptionChainItem> getOptionChainItems() {
    return optionChainItems;
  }

  public void setOptionChainItems(List<OptionChainItem> optionChainItems) {
    this.optionChainItems = optionChainItems;
  }
}
