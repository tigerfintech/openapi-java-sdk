package com.tigerbrokers.stock.openapi.client.struct;

import java.util.Map;
import java.util.Set;

/**
 * Description:
 * Created by lijiawen on 2018/08/29.
 */
public class SubscribedSymbol {

  public static final int MAX_SYMBOLS = 100;
  public static final int MAX_ASK_BID_SYMBOLS = 100;

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
   * stock quote focus keys
   */
  Map<String, Set<String>> symbolFocusKeys;

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

  public Map<String, Set<String>> getSymbolFocusKeys() {
    return symbolFocusKeys;
  }

  public void setSymbolFocusKeys(Map<String, Set<String>> symbolFocusKeys) {
    this.symbolFocusKeys = symbolFocusKeys;
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
}
