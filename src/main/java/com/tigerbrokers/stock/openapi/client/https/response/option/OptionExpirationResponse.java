package com.tigerbrokers.stock.openapi.client.https.response.option;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionExpirationItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/26.
 */
public class OptionExpirationResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<OptionExpirationItem> optionExpirationItems;

  public List<OptionExpirationItem> getOptionExpirationItems() {
    return optionExpirationItems;
  }

  public void setOptionExpirationItems(List<OptionExpirationItem> optionExpirationItems) {
    this.optionExpirationItems = optionExpirationItems;
  }
}
