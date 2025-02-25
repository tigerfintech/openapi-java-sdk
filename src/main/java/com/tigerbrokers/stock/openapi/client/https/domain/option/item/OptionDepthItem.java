package com.tigerbrokers.stock.openapi.client.https.domain.option.item;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

import java.util.List;

/**
 * Description:
 * Created by liutongping on 2024/05/23.
 */
public class OptionDepthItem extends ApiModel {

  private String symbol;
  private Long expiry;
  private String strike;
  private String right;
  private Long timestamp;
  private List<OptionDepthOrderBook> ask;
  private List<OptionDepthOrderBook> bid;

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

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public List<OptionDepthOrderBook> getAsk() {
    return ask;
  }

  public void setAsk(List<OptionDepthOrderBook> ask) {
    this.ask = ask;
  }

  public List<OptionDepthOrderBook> getBid() {
    return bid;
  }

  public void setBid(List<OptionDepthOrderBook> bid) {
    this.bid = bid;
  }

  @Override
  public String toString() {
    return "OptionDepthItem{" +
        "symbol='" + symbol + '\'' +
        ", expiry=" + expiry +
        ", strike='" + strike + '\'' +
        ", right='" + right + '\'' +
        ", ask=" + JSONObject.toJSONString(ask) +
        ", bid=" + JSONObject.toJSONString(bid) +
        '}';
  }
}
