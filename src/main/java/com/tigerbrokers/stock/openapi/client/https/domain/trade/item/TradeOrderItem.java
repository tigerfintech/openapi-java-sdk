package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

public class TradeOrderItem extends ApiModel {
  private long id;
  @Deprecated
  private long orderId;
  private List<Long> subIds;
  private List<TradeOrder> orders;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Deprecated
  public long getOrderId() {
    return orderId;
  }

  @Deprecated
  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }

  public List<Long> getSubIds() {
    return subIds;
  }

  public void setSubIds(List<Long> subIds) {
    this.subIds = subIds;
  }

  public List<TradeOrder> getOrders() {
    return orders;
  }

  public void setOrders(List<TradeOrder> orders) {
    this.orders = orders;
  }
}
