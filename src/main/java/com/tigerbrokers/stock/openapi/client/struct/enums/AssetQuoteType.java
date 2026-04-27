package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by bean on 2025/03/04.
 */
public enum AssetQuoteType {
  ETH,  // Includes pre-market, intra-day, and after-hours trading data. For night session, the closing price of the previous after-hours trading is used for calculation.
  RTH,  // Only intra-day trading data. For pre-market, after-hours, and night session, the intra-day closing price is used for calculation.
  OVERNIGHT // Includes night session trading data. For night session, the night session trading data is used for calculation.
}
