package com.tigerbrokers.stock.openapi.client.https.domain;

import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/26.
 */
public class BatchApiModel<T> extends ApiModel {

  private List<? extends ApiModel> items;

  public BatchApiModel(List<? extends ApiModel> items) {
    this.items = items;
  }

  public List<? extends ApiModel> getItems() {
    return items;
  }

  public void setItems(List<? extends ApiModel> items) {
    this.items = items;
  }
}
