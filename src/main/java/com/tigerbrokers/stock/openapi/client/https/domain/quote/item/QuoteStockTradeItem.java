package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by lijiawen on 2019/01/07.
 */
public class QuoteStockTradeItem extends ApiModel {

  /**
   * 股票代码
   */
  String symbol;

  /**
   * 每手股数
   */
  Integer lotSize;

  /**
   * 报价精度
   */
  Integer spreadScale;

  /**
   * 股价最小变动单位
   */
  Double minTick;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Integer getLotSize() {
    return lotSize;
  }

  public void setLotSize(Integer lotSize) {
    this.lotSize = lotSize;
  }

  public Integer getSpreadScale() {
    return spreadScale;
  }

  public void setSpreadScale(Integer spreadScale) {
    this.spreadScale = spreadScale;
  }

  public Double getMinTick() {
    return minTick;
  }

  public void setMinTick(Double minTick) {
    this.minTick = minTick;
  }

  @Override
  public String toString() {
    return "QuoteStockTradeItem{" +
        "symbol='" + symbol + '\'' +
        ", lotSize=" + lotSize +
        ", spreadScale=" + spreadScale +
        ", minTick=" + minTick +
        '}';
  }
}
