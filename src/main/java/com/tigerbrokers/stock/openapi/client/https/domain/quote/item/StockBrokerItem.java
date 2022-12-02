package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.Arrays;
import java.util.List;

/**
 * Description:
 * Created by bean on 2022/11/14.
 */
public class StockBrokerItem extends ApiModel {

  /**
   * symbol
   */
  private String symbol;

  /**
   * bid borker
   */
  private List<LevelBroker> bidBroker;

  /**
   * ask borker
   */
  private List<LevelBroker> askBroker;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public List<LevelBroker> getBidBroker() {
    return bidBroker;
  }

  public void setBidBroker(List<LevelBroker> bidBroker) {
    this.bidBroker = bidBroker;
  }

  public List<LevelBroker> getAskBroker() {
    return askBroker;
  }

  public void setAskBroker(List<LevelBroker> askBroker) {
    this.askBroker = askBroker;
  }

  @Override
  public String toString() {
    return "StockBrokerItem{" +
        "symbol='" + symbol + '\'' +
        ", bidBroker=" + bidBroker == null ? null : Arrays.toString(bidBroker.toArray()) +
        ", askBroker=" + askBroker == null ? null : Arrays.toString(askBroker.toArray()) +
        '}';
  }
}
