package com.tigerbrokers.stock.openapi.client.constant;

/**
 * Description:
 * Created by lijiawen on 2018/06/06.
 */
public interface OrderChangeKey {

  String type = "type";
  String timestamp = "timestamp";
  String id = "id";
  String orderId = "orderId";
  String account = "account";
  String name = "name";
  String latestPrice = "latestPrice";
  String symbol = "symbol";
  String action = "action";
  String orderType = "orderType";
  String secType = "secType";
  String currency = "currency";
  String localSymbol = "localSymbol";
  String originSymbol = "originSymbol";
  String strike = "strike";
  String expiry = "expiry";
  String right = "right";
  String limitPrice = "limitPrice";
  String auxPrice = "auxPrice";
  String trailingPercent = "trailingPercent";
  String timeInForce = "timeInForce";
  String goodTillDate = "goodTillDate";
  String outsideRth = "outsideRth";
  String totalQuantity = "totalQuantity";
  String filledQuantity = "filledQuantity";
  String avgFillPrice = "avgFillPrice";
  String lastFillPrice = "lastFillPrice";
  String openTime = "openTime";
  String latestTime = "latestTime";
  String remaining = "remaining";
  String status = "status";
  String source = "source";
  String liquidation = "liquidation";

  String errorCode = "errorCode";
  String errorMsg = "errorMsg";
  String errorMsgCn = "errorMsgCn";
  String errorMsgTw = "errorMsgTw";
}
