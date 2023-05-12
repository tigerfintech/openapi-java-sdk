package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

public class BatchOrderItem extends ApiModel {
  private String nextPageToken;
  private List<TradeOrder> orders;

  public String getNextPageToken() {
    return nextPageToken;
  }

  public void setNextPageToken(String nextPageToken) {
    this.nextPageToken = nextPageToken;
  }

  public List<TradeOrder> getOrders() {
    return orders;
  }

  public void setOrders(List<TradeOrder> orders) {
    this.orders = orders;
  }
}
