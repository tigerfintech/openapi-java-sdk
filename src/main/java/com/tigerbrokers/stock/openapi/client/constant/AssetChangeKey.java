package com.tigerbrokers.stock.openapi.client.constant;

/**
 * Description:
 * Created by lijiawen on 2018/06/06.
 */
public interface AssetChangeKey {

  String type = "type";
  String account = "account";
  String timestamp = "timestamp";
  String netLiquidation = "netLiquidation";
  String equityWithLoan = "equityWithLoan";
  String initMarginReq = "initMarginReq";
  String maintMarginReq = "maintMarginReq";
  String availableFunds = "availableFunds";

  String dayTradesRemaining = "dayTradesRemaining";
  String dayTradesRemainingT1 = "dayTradesRemainingT1";
  String dayTradesRemainingT2 = "dayTradesRemainingT2";
  String dayTradesRemainingT3 = "dayTradesRemainingT3";
  String dayTradesRemainingT4 = "dayTradesRemainingT4";

  String amount = "amount";
  String currency = "currency";
  String deposit = "deposit";

  String excessLiquidity = "excessLiquidity";
  String buyingPower = "buyingPower";
  String cashBalance = "cashBalance";
  String grossPositionValue = "grossPositionValue";
}
