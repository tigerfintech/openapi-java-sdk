package com.tigerbrokers.stock.openapi.client.struct;

import java.util.Set;

/**
 * Description:
 * Created by lijiawen on 2018/08/29.
 */
public class SubscribedSymbol {

  /**
   * subscribed quote symbol limit
   */
  private int limit;

  /**
   * subscribed quote symbol size
   */
  private int used;

  private int askBidLimit;

  private int askBidUsed;

  private int tradeTickLimit;
  private int tradeTickUsed;

  private int klineLimit;
  private int klineUsed;

  /**
   * subscribed quote symbol's detail
   */
  Set<String> subscribedSymbols;

  /**
   * subscribed depth-quote symbol's detail
   */
  Set<String> subscribedAskBidSymbols;

  /**
   * subscribed trade-tick symbol's detail
   */
  Set<String> subscribedTradeTickSymbols;

  /**
   * subscribed kline symbol's detail
   */
  Set<String> subscribedKlineSymbols;

  Set<String> subscribedMarketQuote;

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }

  public int getUsed() {
    return used;
  }

  public void setUsed(int used) {
    this.used = used;
  }

  public int getAskBidLimit() {
    return askBidLimit;
  }

  public void setAskBidLimit(int askBidLimit) {
    this.askBidLimit = askBidLimit;
  }

  public int getAskBidUsed() {
    return askBidUsed;
  }

  public void setAskBidUsed(int askBidUsed) {
    this.askBidUsed = askBidUsed;
  }

  public Set<String> getSubscribedAskBidSymbols() {
    return subscribedAskBidSymbols;
  }

  public void setSubscribedAskBidSymbols(Set<String> subscribedAskBidSymbols) {
    this.subscribedAskBidSymbols = subscribedAskBidSymbols;
  }

  public Set<String> getSubscribedSymbols() {
    return subscribedSymbols;
  }

  public void setSubscribedSymbols(Set<String> subscribedSymbols) {
    this.subscribedSymbols = subscribedSymbols;
  }

  public Set<String> getSubscribedMarketQuote() {
    return subscribedMarketQuote;
  }

  public void setSubscribedMarketQuote(Set<String> subscribedMarketQuote) {
    this.subscribedMarketQuote = subscribedMarketQuote;
  }

  public int getTradeTickLimit() {
    return tradeTickLimit;
  }

  public void setTradeTickLimit(int tradeTickLimit) {
    this.tradeTickLimit = tradeTickLimit;
  }

  public int getTradeTickUsed() {
    return tradeTickUsed;
  }

  public void setTradeTickUsed(int tradeTickUsed) {
    this.tradeTickUsed = tradeTickUsed;
  }

  public Set<String> getSubscribedTradeTickSymbols() {
    return subscribedTradeTickSymbols;
  }

  public void setSubscribedTradeTickSymbols(Set<String> subscribedTradeTickSymbols) {
    this.subscribedTradeTickSymbols = subscribedTradeTickSymbols;
  }

  public int getKlineLimit() {
    return klineLimit;
  }

  public void setKlineLimit(int klineLimit) {
    this.klineLimit = klineLimit;
  }

  public int getKlineUsed() {
    return klineUsed;
  }

  public void setKlineUsed(int klineUsed) {
    this.klineUsed = klineUsed;
  }

  public Set<String> getSubscribedKlineSymbols() {
    return subscribedKlineSymbols;
  }

  public void setSubscribedKlineSymbols(Set<String> subscribedKlineSymbols) {
    this.subscribedKlineSymbols = subscribedKlineSymbols;
  }
}
