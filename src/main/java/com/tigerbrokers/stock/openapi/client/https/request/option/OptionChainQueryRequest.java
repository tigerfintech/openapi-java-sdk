package com.tigerbrokers.stock.openapi.client.https.request.option;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import com.tigerbrokers.stock.openapi.client.util.DateUtils;
import java.util.ArrayList;
import java.util.Date;
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

  public static OptionChainQueryRequest of(Item item) {
    List<Item> items = new ArrayList<>();
    items.add(item);
    return new OptionChainQueryRequest(items);
  }

  public static OptionChainQueryRequest of(Item item1, Item item2) {
    List<Item> items = new ArrayList<>();
    items.add(item1);
    items.add(item2);
    return new OptionChainQueryRequest(items);
  }

  public static OptionChainQueryRequest of(Item item1, Item item2, Item item3) {
    List<Item> items = new ArrayList<>();
    items.add(item1);
    items.add(item2);
    items.add(item3);
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

    public void setExpiry(String expiry) {
      Date date = DateUtils.getZoneDate(expiry, TimeZoneId.NewYork);
      if (date != null) {
        this.expiry = date.getTime();
      }
    }

    public void setExpiry(Long expiry) {
      this.expiry = expiry;
    }
  }
}
