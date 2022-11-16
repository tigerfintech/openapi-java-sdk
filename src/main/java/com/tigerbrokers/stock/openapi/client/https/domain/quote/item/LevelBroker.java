package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Description:
 * Created by bean on 2022/11/14.
 */
public class LevelBroker implements Serializable {

  private Integer level;

  private Double price;

  private Integer brokerCount;

  private List<Broker> broker;

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Integer getBrokerCount() {
    return brokerCount;
  }

  public void setBrokerCount(Integer brokerCount) {
    this.brokerCount = brokerCount;
  }

  public List<Broker> getBroker() {
    return broker;
  }

  public void setBroker(List<Broker> broker) {
    this.broker = broker;
  }

  @Override
  public String toString() {
    return "LevelBroker{" +
        "level=" + level +
        ", price=" + price +
        ", brokerCount=" + brokerCount +
        ", broker=" + broker == null ? null : Arrays.toString(broker.toArray()) +
        '}';
  }
}
