package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by lijiawen on 2018/05/16.
 */
public enum OrderType {
  MKT("MKT", "市价"),
  LMT("LMT", "限价"),
  STP("STP", "止损"),
  STP_LMT("STP_LMT", "止损限价"),
  TRAIL("TRAIL", "跟踪止损"),
  AM("AM", "Auction Market Order"),
  AL("AL", "Auction Limit Order"),
  TWAP("TWAP", "Time Weighted Average Price"),// 时间加权平均价格算法
  VWAP("VWAP", "Volume Weighted Average Price"),// 成交量加权平均价格算法
  ;

  private String type;
  private String desc;

  OrderType(String type, String desc) {
    this.type = type;
    this.desc = desc;
  }
}

