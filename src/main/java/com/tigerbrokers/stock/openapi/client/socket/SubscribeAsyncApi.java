package com.tigerbrokers.stock.openapi.client.socket;

import com.tigerbrokers.stock.openapi.client.struct.enums.MarketDataProvider;
import com.tigerbrokers.stock.openapi.client.struct.enums.QuoteKeyType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Subject;
import java.util.List;
import java.util.Set;

/**
 * Description:
 * Created by lijiawen on 2018/08/29.
 */
public interface SubscribeAsyncApi {

  /**
   * subscribe trade data , include order / position / asset
   *
   * @param subject trade subject
   * @return string id
   */
  String subscribe(Subject subject);

  /**
   * subscribe trade data , include order / position / asset
   *
   * @param subject trade subject
   * @param focusKeys focus keys
   * @return string id
   */
  String subscribe(Subject subject, List<String> focusKeys);

  /**
   * subscribe trade data , include order / position / asset
   *
   * @param account subscribe account
   * @param subject trade subject
   * @return string id
   */
  String subscribe(String account, Subject subject);

  /**
   * subscribe trade data , include order / position / asset
   *
   * @param account account
   * @param subject subject
   * @param focusKeys focus keys
   * @return string id
   */
  String subscribe(String account, Subject subject, List<String> focusKeys);

  /**
   * cancel subscribe trade data , include order / position / asset
   *
   * @param subject trade subject
   * @return string id
   */
  String cancelSubscribe(Subject subject);

  /**
   * subscrie quote
   *
   * @param symbols symbol list
   * @return string id
   */
  String subscribeQuote(Set<String> symbols);

  /**
   * subscribe quote data
   *
   * @param symbols symbol list
   * @param quoteKeyType quote key type
   * @return string id
   */
  String subscribeQuote(Set<String> symbols, QuoteKeyType quoteKeyType);

  /**
   * subscribe quote data
   *
   * @param symbols symbol list
   * @param focusKeys focus keys
   * @return string id
   */
  String subscribeQuote(Set<String> symbols, List<String> focusKeys);

  /**
   * cancel subscribe quote data,include stock / option / futures
   *
   * @param symbols symbol list
   * @return string id
   */
  String cancelSubscribeQuote(Set<String> symbols);

  /**
   * subscribe stock trade tick data
   *
   * @param symbols symbol list
   * @return string id
   */
  String subscribeTradeTick(Set<String> symbols);

  /**
   * cancel subscribe stock trade tick data
   *
   * @param symbols symbol list
   * @return string id
   */
  String cancelSubscribeTradeTick(Set<String> symbols);

  /**
   * subscribe option data
   *
   * @param symbols symbol list
   * @return string id
   */
  String subscribeOption(Set<String> symbols);

  /**
   * cancel subscribe option data
   *
   * @param symbols symbol list
   * @return string id
   */
  String cancelSubscribeOption(Set<String> symbols);

  /**
   * subscribe futures data
   *
   * @param symbols symbol list
   * @return string id
   */
  String subscribeFuture(Set<String> symbols);

  /**
   * cancel subscribe futures data
   *
   * @param symbols symbol list
   * @return string id
   */
  String cancelSubscribeFuture(Set<String> symbols);

  /**
   * subscribe depth data
   *
   * @param symbols symbol list
   * @return string id
   */
  String subscribeDepthQuote(Set<String> symbols);

  /**
   * cancel subscribe depth data
   *
   * @param symbols symbol list
   * @return string id
   */
  String cancelSubscribeDepthQuote(Set<String> symbols);

  /**
   * query subscribed symbol list
   *
   * @return string id
   */
  String getSubscribedSymbols();
}
