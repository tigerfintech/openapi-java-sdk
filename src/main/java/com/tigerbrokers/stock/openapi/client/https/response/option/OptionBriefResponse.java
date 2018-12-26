package com.tigerbrokers.stock.openapi.client.https.response.option;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionBriefItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/26.
 */
public class OptionBriefResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<OptionBriefItem> optionBriefItems;

  public List<OptionBriefItem> getOptionBriefItems() {
    return optionBriefItems;
  }

  public void setOptionBriefItems(List<OptionBriefItem> optionBriefItems) {
    this.optionBriefItems = optionBriefItems;
  }
}
