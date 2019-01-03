package com.tigerbrokers.stock.openapi.client.https.domain.option.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/26.
 */
public class OptionTradeTickItem extends ApiModel {

  private String symbol;
  private Long expiry;
  private String strike;
  private String right;
  private List<TradeTickPoint> items;

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

  public String getStrike() {
    return strike;
  }

  public void setStrike(String strike) {
    this.strike = strike;
  }

  public String getRight() {
    return right;
  }

  public void setRight(String right) {
    this.right = right;
  }

  public List<TradeTickPoint> getItems() {
    return items;
  }

  public void setItems(List<TradeTickPoint> items) {
    this.items = items;
  }

  @Override
  public String toString() {
    return "OptionTradeTickItem{" +
        "symbol='" + symbol + '\'' +
        ", expiry=" + expiry +
        ", strike='" + strike + '\'' +
        ", right='" + right + '\'' +
        ", items=" + items +
        '}';
  }
}
