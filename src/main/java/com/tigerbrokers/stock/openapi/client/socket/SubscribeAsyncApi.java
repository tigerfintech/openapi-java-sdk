package com.tigerbrokers.stock.openapi.client.socket;

import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.OptionTopTarget;
import com.tigerbrokers.stock.openapi.client.struct.enums.QuoteSubject;
import com.tigerbrokers.stock.openapi.client.struct.enums.StockTopTarget;
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
   * subscribe quote-data of the specified market
   * @param market Market
   * @param subject QuoteSubject
   * @return
   */
  public String subscribeMarketQuote(Market market, QuoteSubject subject);

  /**
   * cancel subscribe quote-data of the specified market
   * @param market Market
   * @param subject QuoteSubject
   * @return
   */
  public String cancelSubscribeMarketQuote(Market market, QuoteSubject subject);

  /**
   * subscribe quote-top-data of the specified market
   * @param market Market
   * @param targetNames quote top target names
   * @return
   */
  public String subscribeQuoteTop(Market market, Set<StockTopTarget> targetNames);

  /**
   * cancel subscribe quote-top-data of the specified market
   * @param market Market
   * @param targetNames quote top target names
   * @return
   */
  public String cancelSubscribeQuoteTop(Market market, Set<StockTopTarget> targetNames);

  /**
   * subscribe option-top-data of the specified market
   * @param market Market
   * @param targetNames option top target names
   * @return
   */
  public String subscribeOptionTop(Market market, Set<OptionTopTarget> targetNames);

  /**
   * cancel subscribe option-top-data of the specified market
   * @param market Market
   * @param targetNames option top target names
   * @return
   */
  public String cancelSubscribeOptionTop(Market market, Set<OptionTopTarget> targetNames);

  /**
   * query subscribed symbol list
   *
   * @return string id
   */
  String getSubscribedSymbols();
}
