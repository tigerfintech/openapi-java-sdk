package com.tigerbrokers.stock.openapi.client.https.domain.future.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2019/01/09.
 */
public class FutureTickBatchItem extends ApiModel {

  private String contractCode;
  private List<FutureTickItem> items;

  public String getContractCode() {
    return contractCode;
  }

  public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
  }

  public List<FutureTickItem> getItems() {
    return items;
  }

  public void setItems(List<FutureTickItem> items) {
    this.items = items;
  }

  @Override
  public String toString() {
    return "FutureTickBatchItem{" +
        "contractCode='" + contractCode + '\'' +
        ", items=" + items +
        '}';
  }
}
