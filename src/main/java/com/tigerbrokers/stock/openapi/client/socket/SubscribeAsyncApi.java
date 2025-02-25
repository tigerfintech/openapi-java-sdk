package com.tigerbrokers.stock.openapi.client.socket;

import com.tigerbrokers.stock.openapi.client.struct.Indicator;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.QuoteSubject;
import com.tigerbrokers.stock.openapi.client.struct.enums.Subject;
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
   * @param account subscribe account
   * @param subject trade subject
   * @return string id
   */
  String subscribe(String account, Subject subject);

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
   * subscribe bar data
   *
   * @param symbols symbol list
   * @return string id
   */
  String subscribeKline(Set<String> symbols);

  /**
   * cancel subscribe bar data
   *
   * @param symbols symbol list
   * @return string id
   */
  String cancelSubscribeKline(Set<String> symbols);

  /**
   * subscribe quote-data of the specified market
   * @param market Market
   * @param subject QuoteSubject
   * @return
   */
  String subscribeMarketQuote(Market market, QuoteSubject subject);

  /**
   * cancel subscribe quote-data of the specified market
   * @param market Market
   * @param subject QuoteSubject
   * @return
   */
  String cancelSubscribeMarketQuote(Market market, QuoteSubject subject);

  /**
   * subscribe stock-top-data of the specified market
   * @param market Market
   * @param indicators stock top quote's indicator
   * @return
   */
  String subscribeStockTop(Market market, Set<Indicator> indicators);

  /**
   * cancel subscribe stock-top-data of the specified market
   * @param market Market
   * @param indicators stock top quote's indicator
   * @return
   */
  String cancelSubscribeStockTop(Market market, Set<Indicator> indicators);

  /**
   * subscribe option-top-data of the specified market
   * @param market Market
   * @param indicators option top quote's indicator
   * @return
   */
  public String subscribeOptionTop(Market market, Set<Indicator> indicators);

  /**
   * cancel subscribe option-top-data of the specified market
   * @param market Market
   * @param indicators option top quote's indicator
   * @return
   */
  public String cancelSubscribeOptionTop(Market market, Set<Indicator> indicators);

  /**
   * query subscribed symbol list
   *
   * @return string id
   */
  String getSubscribedSymbols();
}
