package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import lombok.Data;

@Data
public class TradeOrderItem extends ApiModel {
  private long id;
  private long orderId;
}
