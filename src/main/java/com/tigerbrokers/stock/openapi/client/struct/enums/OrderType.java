package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by lijiawen on 2018/05/16.
 */
public enum OrderType {
  MKT("MKT", "Market Order"),
  LMT("LMT", "Limit Order"),
  STP("STP", "Stop Loss Order"),
  STP_LMT("STP_LMT", "Stop Limit Order"),
  TRAIL("TRAIL", "Trailing Stop Order"),
  AM("AM", "Auction Market Order"),
  AL("AL", "Auction Limit Order"),
  TWAP("TWAP", "Time Weighted Average Price"),
  VWAP("VWAP", "Volume Weighted Average Price"),
  ;

  private String type;
  private String desc;

  OrderType(String type, String desc) {
    this.type = type;
    this.desc = desc;
  }
}

