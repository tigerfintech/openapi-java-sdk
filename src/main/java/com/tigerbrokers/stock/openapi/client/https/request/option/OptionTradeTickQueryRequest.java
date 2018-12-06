package com.tigerbrokers.stock.openapi.client.https.request.option;

import com.alibaba.fastjson.JSONObject;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/06.
 */
public class OptionTradeTickQueryRequest {

  private List<Item> items;

  public OptionTradeTickQueryRequest(List<Item> items) {
    this.items = items;
  }

  public static OptionTradeTickQueryRequest of(List<Item> items) {
    return new OptionTradeTickQueryRequest(items);
  }

  public String toJson() {
    return JSONObject.toJSONString(this);
  }

  public List<Item> getItems() {
    return items;
  }

  public void setItems(
      List<Item> items) {
    this.items = items;
  }

  public static class Item {

    private String symbol;
    private String right;
    private Long expiry;
    private String strike;

    public String getSymbol() {
      return symbol;
    }

    public void setSymbol(String symbol) {
      this.symbol = symbol;
    }

    public String getRight() {
      return right;
    }

    public void setRight(String right) {
      this.right = right;
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
  }
}
