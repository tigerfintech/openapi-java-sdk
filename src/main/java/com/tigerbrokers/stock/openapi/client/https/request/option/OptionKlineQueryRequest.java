package com.tigerbrokers.stock.openapi.client.https.request.option;

import com.alibaba.fastjson.JSONObject;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/06.
 */
public class OptionKlineQueryRequest {

  private List<Item> items;

  public OptionKlineQueryRequest(List<Item> items) {
    this.items = items;
  }

  public static OptionKlineQueryRequest of(List<Item> items) {
    return new OptionKlineQueryRequest(items);
  }

  public String toJson() {
    return JSONObject.toJSONString(this);
  }

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }

  public static class Item {

    private String symbol;
    private String right;
    private Long expiry;
    private String strike;
    private Long beginTime;
    private Long endTime;

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

    public Long getBeginTime() {
      return beginTime;
    }

    public void setBeginTime(Long beginTime) {
      this.beginTime = beginTime;
    }

    public Long getEndTime() {
      return endTime;
    }

    public void setEndTime(Long endTime) {
      this.endTime = endTime;
    }
  }
}
