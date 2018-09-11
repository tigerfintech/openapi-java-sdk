package com.tigerbrokers.stock.openapi.client.struct;

import java.util.Map;
import java.util.Set;

/**
 * Description:
 * Created by lijiawen on 2018/08/29.
 */
public class SubscribedSymbol {

  public static final int MAX_SYMBOLS = 500;

  /**
   * 订阅限制数
   */
  private int limit;
  /**
   * 已订阅数
   */
  private int used;

  /**
   * 订阅详情
   */
  Set<String> subscribedSymbols;

  /**
   * 关注keys
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
}
