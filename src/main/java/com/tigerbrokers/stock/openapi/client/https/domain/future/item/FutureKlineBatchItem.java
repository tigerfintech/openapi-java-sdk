package com.tigerbrokers.stock.openapi.client.https.domain.future.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2019/01/09.
 */
public class FutureKlineBatchItem extends ApiModel {

  private String contractCode;
  private String nextPageToken;
  private List<FutureKlineItem> items;

  public String getContractCode() {
    return contractCode;
  }

  public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
  }

  public String getNextPageToken() {
    return nextPageToken;
  }

  public void setNextPageToken(String nextPageToken) {
    this.nextPageToken = nextPageToken;
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
        ", nextPageToken=" + nextPageToken +
        ", items=" + items +
        '}';
  }
}
