package com.tigerbrokers.stock.openapi.client.https.request.option;

import com.alibaba.fastjson.JSONObject;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/06.
 */
public class OptionChainQueryRequest {

  private List<Item> items;

  public OptionChainQueryRequest(List<Item> items) {
    this.items = items;
  }

  public static OptionChainQueryRequest of(List<Item> items) {
    return new OptionChainQueryRequest(items);
  }

  public String toJson() {
    return JSONObject.toJSONString(this);
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }

  public List<Item> getItems() {
    return items;
  }

  public static class Item {

    String symbol;
    Long expiry;

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
  }
}
