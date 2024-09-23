package com.tigerbrokers.stock.openapi.client.https.domain.future.item;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * Description:
 * Created by bean on 2023/08/29.
 */
public class FutureHistoryMainContractItem extends ApiModel {

  private String contractCode;
  private List<FutureHistoryContractItem> mainReferItems;

  public String getContractCode() {
    return contractCode;
  }

  public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
  }

  public List<FutureHistoryContractItem> getMainReferItems() {
    return mainReferItems;
  }

  public void setMainReferItems(
      List<FutureHistoryContractItem> mainReferItems) {
    this.mainReferItems = mainReferItems;
  }

  @Override
  public String toString() {
    return "FutureHistoryMainContractItem{" +
        "contractCode='" + contractCode + '\'' +
        ", mainReferItems=" + JSONObject.toJSONString(mainReferItems) +
        '}';
  }
}
