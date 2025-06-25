package com.tigerbrokers.stock.openapi.client.https.domain.option.model;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;

public class OptionTimelinesModel extends OptionModel {

  @JSONField(name = "option_query")
  private List<OptionTimelineModel> optionQuery;

  public List<OptionTimelineModel> getOptionQuery() {
    return optionQuery;
  }

  public void setOptionQuery(List<OptionTimelineModel> optionQuery) {
    this.optionQuery = optionQuery;
  }

  @Override
  public String toString() {
    return "OptionTimelinesModel{" +
        "market=" + market +
        ", optionQuery=" + optionQuery +
        '}';
  }
}
