package com.tigerbrokers.stock.openapi.client.https.response.option;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionAnalysisItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description: Option analysis response
 */
public class OptionAnalysisResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<OptionAnalysisItem> optionAnalysisItems;

  public List<OptionAnalysisItem> getOptionAnalysisItems() {
    return optionAnalysisItems;
  }

  public void setOptionAnalysisItems(List<OptionAnalysisItem> optionAnalysisItems) {
    this.optionAnalysisItems = optionAnalysisItems;
  }
}
