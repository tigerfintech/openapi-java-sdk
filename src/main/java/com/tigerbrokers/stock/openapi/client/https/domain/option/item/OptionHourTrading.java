package com.tigerbrokers.stock.openapi.client.https.domain.option.item;

import java.io.Serializable;

public class OptionHourTrading implements Serializable {

  private static final long serialVersionUID = 1L;

  private String tag;

  private String session;

  private Double latestPrice;

  private Long latestTimestamp;

  private Integer volume;

  private Double amount;

  private Double preClose;

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getSession() {
    return session;
  }

  public void setSession(String session) {
    this.session = session;
  }

  public Double getLatestPrice() {
    return latestPrice;
  }

  public void setLatestPrice(Double latestPrice) {
    this.latestPrice = latestPrice;
  }

  public Long getLatestTimestamp() {
    return latestTimestamp;
  }

  public void setLatestTimestamp(Long latestTimestamp) {
    this.latestTimestamp = latestTimestamp;
  }

  public Integer getVolume() {
    return volume;
  }

  public void setVolume(Integer volume) {
    this.volume = volume;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Double getPreClose() {
    return preClose;
  }

  public void setPreClose(Double preClose) {
    this.preClose = preClose;
  }

  @Override
  public String toString() {
    return "OptionHourTrading{" +
        "tag='" + tag + '\'' +
        ", session='" + session + '\'' +
        ", latestPrice=" + latestPrice +
        ", latestTimestamp=" + latestTimestamp +
        ", volume=" + volume +
        ", amount=" + amount +
        ", preClose=" + preClose +
        '}';
  }
}
