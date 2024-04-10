/*
 * TigerBrokers
 * Copyright (C) 2014-2021 All Rights Reserved.
 */
package com.tigerbrokers.stock.openapi.client.https.domain.option.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

public class OptionChainV3Model extends ApiModel {

  @JSONField(name = "option_basic")
  private List<OptionChainModel> optionBasic;
  @JSONField(name = "option_filter")
  private OptionChainFilterModel optionFilter;
  @JSONField(name = "return_greek_value")
  private Boolean returnGreekValue;

  public List<OptionChainModel> getOptionBasic() {
    return optionBasic;
  }

  public void setOptionBasic(List<OptionChainModel> optionBasic) {
    this.optionBasic = optionBasic;
  }

  public OptionChainFilterModel getOptionFilter() {
    return optionFilter;
  }

  public void setOptionFilter(OptionChainFilterModel optionFilter) {
    this.optionFilter = optionFilter;
  }

  public Boolean getReturnGreekValue() {
    return returnGreekValue;
  }

  public void setReturnGreekValue(Boolean returnGreekValue) {
    this.returnGreekValue = returnGreekValue;
  }

  @Override
  public String toString() {
    return "OptionChainV2Model{" +
        "optionBasic=" + optionBasic +
        ", optionFilter=" + optionFilter +
        ", returnGreekValue=" + returnGreekValue +
        '}';
  }
}