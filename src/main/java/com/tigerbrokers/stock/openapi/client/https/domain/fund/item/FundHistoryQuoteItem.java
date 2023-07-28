package com.tigerbrokers.stock.openapi.client.https.domain.fund.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FundHistoryQuoteItem extends ApiModel {

  /**
   * fund code
   */
  private String symbol;

  /**
   * quote data list
   */
  private List<FundQuotePoint> items;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public List<FundQuotePoint> getItems() {
    return items;
  }

  public void setItems(List<FundQuotePoint> items) {
    this.items = items;
  }

  @Override
  public String toString() {
    return "KlineItem{" +
        "symbol='" + symbol + '\'' +
        ", items=" + items +
        '}';
  }
}
