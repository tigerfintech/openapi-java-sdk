package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class ShortableStockItem extends ApiModel {

  /**
   * 股票代码
   */
  private String symbol;

  /**
   * 做空数据列表
   */
  private List<ShortInterest> items;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public List<ShortInterest> getItems() {
    return items;
  }

  public void setItems(List<ShortInterest> items) {
    this.items = items;
  }

  @Override
  public String toString() {
    return "ShortableStockItem{" +
        "symbol='" + symbol + '\'' +
        ", items=" + items +
        '}';
  }
}
