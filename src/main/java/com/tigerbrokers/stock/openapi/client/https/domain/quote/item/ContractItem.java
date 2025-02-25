package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class ContractItem extends ApiModel {

  /**
   * stock code
   */
  private String symbol;

  /**
   * contract type（WAR：warrant、IOPT：callable bull bear contract）
   */
  private String secType;

  /**
   * quote contract list
   */
  private List<QuoteContract> items;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getSecType() {
    return secType;
  }

  public void setSecType(String secType) {
    this.secType = secType;
  }

  public List<QuoteContract> getItems() {
    return items;
  }

  public void setItems(List<QuoteContract> items) {
    this.items = items;
  }

  @Override
  public String toString() {
    return "ContractItem{" +
        "symbol='" + symbol + '\'' +
        ", secType='" + secType + '\'' +
        ", items=" + items +
        '}';
  }
}
