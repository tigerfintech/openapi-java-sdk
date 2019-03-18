package com.tigerbrokers.stock.openapi.client.https.domain.option.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/26.
 */
public class OptionChainItem extends ApiModel {

  private String symbol;
  private Long expiry;
  private List<OptionRealTimeQuoteGroup> items;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Long getExpiry() {
    return expiry;
  }

  public void setExpiry(Long expiry) {
    this.expiry = expiry;
  }

  public List<OptionRealTimeQuoteGroup> getItems() {
    return items;
  }

  public void setItems(List<OptionRealTimeQuoteGroup> items) {
    this.items = items;
  }

  @Override
  public String toString() {
    return "OptionChainItem{" +
        "symbol='" + symbol + '\'' +
        ", expiry=" + expiry +
        ", items=" + items +
        '}';
  }
}
