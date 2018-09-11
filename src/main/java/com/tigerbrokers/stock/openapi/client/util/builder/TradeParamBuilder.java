package com.tigerbrokers.stock.openapi.client.util.builder;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderType;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.struct.param.OrderParameter;
import com.tigerbrokers.stock.openapi.client.struct.TagValue;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeInForce;
import com.tigerbrokers.stock.openapi.client.util.FastJsonPropertyFilter;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/05/31.
 */
public class TradeParamBuilder {

  private OrderParameter orderParameter;

  private TradeParamBuilder() {
    orderParameter = new OrderParameter();
  }

  public static TradeParamBuilder instance() {
    return new TradeParamBuilder();
  }

  public TradeParamBuilder orderId(int orderId) {
    this.orderParameter.setOrderId(orderId);
    return this;
  }

  public TradeParamBuilder symbol(String symbol) {
    this.orderParameter.setSymbol(symbol);
    return this;
  }

  public TradeParamBuilder orderType(OrderType orderType) {
    this.orderParameter.setOrderType(orderType);
    return this;
  }

  public TradeParamBuilder limitPrice(Double limitPrice) {
    this.orderParameter.setLimitPrice(limitPrice);
    return this;
  }

  public TradeParamBuilder auxPrice(Double auxPrice) {
    this.orderParameter.setAuxPrice(auxPrice);
    return this;
  }

  public TradeParamBuilder trailingPercent(Double trailingPercent) {
    this.orderParameter.setTrailingPercent(trailingPercent);
    return this;
  }

  public TradeParamBuilder secType(SecType secType) {
    this.orderParameter.setSecType(secType);
    return this;
  }

  public TradeParamBuilder outsideRth(Boolean outsideRth) {
    this.orderParameter.setOutsideRth(outsideRth);
    return this;
  }

  public TradeParamBuilder totalQuantity(Integer totalQuantity) {
    this.orderParameter.setTotalQuantity(totalQuantity);
    return this;
  }

  public TradeParamBuilder action(ActionType action) {
    this.orderParameter.setAction(action);
    return this;
  }

  public TradeParamBuilder currency(Currency currency) {
    this.orderParameter.setCurrency(currency);
    return this;
  }

  public TradeParamBuilder timeInForce(TimeInForce timeInForce) {
    this.orderParameter.setTimeInForce(timeInForce);
    return this;
  }

  public TradeParamBuilder account(String account) {
    this.orderParameter.setAccount(account);
    return this;
  }

  public TradeParamBuilder market(Market market) {
    this.orderParameter.setMarket(market.name());
    return this;
  }

  public TradeParamBuilder exchange(String exchange) {
    this.orderParameter.setExchange(exchange);
    return this;
  }

  public TradeParamBuilder expiry(String expiry) {
    this.orderParameter.setExpiry(expiry);
    return this;
  }

  public TradeParamBuilder strike(String strike) {
    this.orderParameter.setStrike(strike);
    return this;
  }

  public TradeParamBuilder right(String right) {
    this.orderParameter.setRight(right);
    return this;
  }

  public TradeParamBuilder multiplier(Float multiplier) {
    this.orderParameter.setMultiplier(multiplier);
    return this;
  }

  public TradeParamBuilder localSymbol(String localSymbol) {
    this.orderParameter.setLocalSymbol(localSymbol);
    return this;
  }

  public TradeParamBuilder allocAccounts(List<String> allocAccounts) {
    this.orderParameter.setAllocAccounts(allocAccounts);
    return this;
  }

  public TradeParamBuilder allocShares(List<Double> allocShares) {
    this.orderParameter.setAllocShares(allocShares);
    return this;
  }

  public TradeParamBuilder algoStrategy(String algoStrategy) {
    this.orderParameter.setAlgoStrategy(algoStrategy);
    return this;
  }

  public TradeParamBuilder algoParams(List<TagValue> algoParams) {
    this.orderParameter.setAlgoParams(algoParams);
    return this;
  }

  public TradeParamBuilder source(String source) {
    this.orderParameter.setSource(source);
    return this;
  }

  public OrderParameter build() {
    return this.orderParameter;
  }

  public String buildJson() {
    return JSONObject.toJSONString(orderParameter, FastJsonPropertyFilter.getPropertyFilter());
  }
}
