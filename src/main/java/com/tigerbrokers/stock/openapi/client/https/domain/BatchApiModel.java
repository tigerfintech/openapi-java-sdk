package com.tigerbrokers.stock.openapi.client.https.domain;

import com.tigerbrokers.stock.openapi.client.struct.enums.Market;

import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/26.
 */
public class BatchApiModel<T> extends ApiModel {
  private Market market;

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

  public Market getMarket() {
    return market;
  }

  public void setMarket(Market market) {
    this.market = market;
  }
}
