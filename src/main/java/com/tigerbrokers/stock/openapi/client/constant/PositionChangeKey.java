package com.tigerbrokers.stock.openapi.client.constant;

/**
 * Description:
 * Created by lijiawen on 2018/06/06.
 */
public interface PositionChangeKey {

  String type = "type";
  String account = "account";
  String timestamp = "timestamp";
  String symbol = "symbol";
  String secType = "secType";
  String currency = "currency";
  String name = "name";
  String localSymbol = "localSymbol";
  String originSymbol = "originSymbol";
  String market = "market";
  String latestPrice = "latestPrice";
  String marketValue = "value";
  String position = "position";
  String averageCost = "averageCost";
  String unrealizedPnl = "unrealizedPnl";

  // 期权相关
  String expiry = "expiry";
  String strike = "strike";
  String right = "right";
  String multiplier = "multiplier";
}
