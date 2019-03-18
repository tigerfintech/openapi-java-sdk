package com.tigerbrokers.stock.openapi.client.https.domain.future.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2019/01/09.
 */
public class FutureKlineBatchItem extends ApiModel {

  private String contractCode;
  private List<FutureKlineItem> items;

  public String getContractCode() {
    return contractCode;
  }

  public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
  }

  public List<FutureKlineItem> getItems() {
    return items;
  }

  public void setItems(List<FutureKlineItem> items) {
    this.items = items;
  }

  @Override
  public String toString() {
    return "FutureKlineBatchItem{" +
        "contractCode='" + contractCode + '\'' +
        ", items=" + items +
        '}';
  }
}
