package com.tigerbrokers.stock.openapi.client.https.domain.option.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

import java.util.List;

public class OptionTimelineItem extends ApiModel {

  /**
   * 股票代码
   */
  private String symbol;

  /**
   * 到期时间，毫秒,当天0点
   */
  private Long expiry;

  /**
   * 看多或看空  CALL  PUT
   */
  private String right;

  /**
   * 行权价
   */
  private String strike;

  /**
   * 昨日收盘价
   */
  private Double preClose;

  /**
   * 交易时间段列表
   */
  private List<List<Long>> openAndCloseTimeList;

  /**
   * 分时数组
   */
  private List<OptionTimelinePoint> minutes;

  public static class OptionTimelinePoint {

    private Double price;
    private Double avgPrice;
    private Long time;
    private Long volume;

    public Double getPrice() {
      return price;
    }

    public void setPrice(Double price) {
      this.price = price;
    }

    public Double getAvgPrice() {
      return avgPrice;
    }

    public void setAvgPrice(Double avgPrice) {
      this.avgPrice = avgPrice;
    }

    public Long getTime() {
      return time;
    }

    public void setTime(Long time) {
      this.time = time;
    }

    public Long getVolume() {
      return volume;
    }

    public void setVolume(Long volume) {
      this.volume = volume;
    }

    @Override
    public String toString() {
      return "OptionTimelinePoint{" +
          "price=" + price +
          ", avgPrice=" + avgPrice +
          ", time=" + time +
          ", volume=" + volume +
          '}';
    }
  }

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

  public String getRight() {
    return right;
  }

  public void setRight(String right) {
    this.right = right;
  }

  public String getStrike() {
    return strike;
  }

  public void setStrike(String strike) {
    this.strike = strike;
  }

  public Double getPreClose() {
    return preClose;
  }

  public void setPreClose(Double preClose) {
    this.preClose = preClose;
  }

  public List<List<Long>> getOpenAndCloseTimeList() {
    return openAndCloseTimeList;
  }

  public void setOpenAndCloseTimeList(List<List<Long>> openAndCloseTimeList) {
    this.openAndCloseTimeList = openAndCloseTimeList;
  }

  public List<OptionTimelinePoint> getMinutes() {
    return minutes;
  }

  public void setMinutes(List<OptionTimelinePoint> minutes) {
    this.minutes = minutes;
  }

  @Override
  public String toString() {
    return "OptionTimelineItem{" +
        "symbol='" + symbol + '\'' +
        ", expiry=" + expiry +
        ", right='" + right + '\'' +
        ", strike='" + strike + '\'' +
        ", preClose=" + preClose +
        ", openAndCloseTimeList=" + openAndCloseTimeList +
        ", minutes=" + minutes +
        '}';
  }
}
