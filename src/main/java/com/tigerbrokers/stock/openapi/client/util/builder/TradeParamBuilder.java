package com.tigerbrokers.stock.openapi.client.util.builder;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.struct.TagValue;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.AttachType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderType;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeInForce;
import com.tigerbrokers.stock.openapi.client.struct.param.OrderParameter;
import com.tigerbrokers.stock.openapi.client.util.FastJsonPropertyFilter;

import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import java.util.ArrayList;
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

  public TradeParamBuilder id(Long id) {
    if (id != null) {
      this.orderParameter.setId(id);
    }
    return this;
  }

  public TradeParamBuilder orderId(Integer orderId) {
    if (orderId != null) {
      this.orderParameter.setOrderId(orderId);
    }
    return this;
  }

  public TradeParamBuilder symbol(String symbol) {
    if (symbol != null) {
      this.orderParameter.setSymbol(symbol);
    }
    return this;
  }

  public TradeParamBuilder orderType(OrderType orderType) {
    if (orderType != null) {
      this.orderParameter.setOrderType(orderType);
    }
    return this;
  }

  public TradeParamBuilder limitPrice(Double limitPrice) {
    if (limitPrice != null) {
      this.orderParameter.setLimitPrice(limitPrice);
    }
    return this;
  }

  public TradeParamBuilder adjustLimit(Double adjustLimit) {
    if (adjustLimit != null) {
      this.orderParameter.setAdjustLimit(adjustLimit);
    }
    return this;
  }

  public TradeParamBuilder auxPrice(Double auxPrice) {
    if (auxPrice != null) {
      this.orderParameter.setAuxPrice(auxPrice);
    }
    return this;
  }

  public TradeParamBuilder trailingPercent(Double trailingPercent) {
    if (trailingPercent != null) {
      this.orderParameter.setTrailingPercent(trailingPercent);
    }
    return this;
  }

  public TradeParamBuilder secType(SecType secType) {
    if (secType != null) {
      this.orderParameter.setSecType(secType);
    }
    return this;
  }

  public TradeParamBuilder outsideRth(Boolean outsideRth) {
    if (outsideRth != null) {
      this.orderParameter.setOutsideRth(outsideRth);
    }
    return this;
  }

  public TradeParamBuilder totalQuantity(Integer totalQuantity) {
    if (totalQuantity != null) {
      this.orderParameter.setTotalQuantity(totalQuantity);
    }
    return this;
  }

  public TradeParamBuilder action(ActionType action) {
    if (action != null) {
      this.orderParameter.setAction(action);
    }
    return this;
  }

  public TradeParamBuilder currency(Currency currency) {
    if (currency != null) {
      this.orderParameter.setCurrency(currency);
    }
    return this;
  }

  public TradeParamBuilder timeInForce(TimeInForce timeInForce) {
    if (timeInForce != null) {
      this.orderParameter.setTimeInForce(timeInForce);
    }
    return this;
  }

  public TradeParamBuilder expireTime(Long expireTime) {
    if (expireTime != null) {
      this.orderParameter.setExpireTime(expireTime);
    }
    return this;
  }

  public TradeParamBuilder account(String account) {
    if (account != null) {
      this.orderParameter.setAccount(account);
    }
    return this;
  }

  public TradeParamBuilder secretKey(String secretKey) {
    if (secretKey != null) {
      this.orderParameter.setSecretKey(secretKey);
    }
    return this;
  }


  public TradeParamBuilder market(Market market) {
    if (market != null) {
      this.orderParameter.setMarket(market.name());
    }
    return this;
  }

  public TradeParamBuilder exchange(String exchange) {
    if (exchange != null) {
      this.orderParameter.setExchange(exchange);
    }
    return this;
  }

  public TradeParamBuilder expiry(String expiry) {
    if (expiry != null) {
      this.orderParameter.setExpiry(expiry);
    }
    return this;
  }

  public TradeParamBuilder strike(String strike) {
    if (strike != null) {
      this.orderParameter.setStrike(strike);
    }
    return this;
  }

  public TradeParamBuilder right(String right) {
    if (right != null) {
      this.orderParameter.setRight(right);
    }
    return this;
  }

  public TradeParamBuilder multiplier(Float multiplier) {
    if (multiplier != null) {
      this.orderParameter.setMultiplier(multiplier);
    }
    return this;
  }

  public TradeParamBuilder localSymbol(String localSymbol) {
    if (localSymbol != null) {
      this.orderParameter.setLocalSymbol(localSymbol);
    }
    return this;
  }

  public TradeParamBuilder allocAccounts(List<String> allocAccounts) {
    if (allocAccounts != null) {
      this.orderParameter.setAllocAccounts(allocAccounts);
    }
    return this;
  }

  public TradeParamBuilder allocShares(List<Double> allocShares) {
    if (allocShares != null) {
      this.orderParameter.setAllocShares(allocShares);
    }
    return this;
  }

  public TradeParamBuilder algoStrategy(String algoStrategy) {
    if (algoStrategy != null) {
      this.orderParameter.setAlgoStrategy(algoStrategy);
    }
    return this;
  }

  public TradeParamBuilder algoParams(List<TagValue> algoParams) {
    if (algoParams != null) {
      this.orderParameter.setAlgoParams(algoParams);
    }
    return this;
  }

  public TradeParamBuilder source(String source) {
    if (source != null) {
      this.orderParameter.setSource(source);
    }
    return this;
  }

  public TradeParamBuilder userMark(String userMark) {
    if (userMark != null) {
      this.orderParameter.setUserMark(userMark);
    }
    return this;
  }

  public TradeParamBuilder contract(ContractItem contract) {
    symbol(contract.getSymbol())
        .right(contract.getRight())
        .expiry(contract.getExpiry())
        .localSymbol(contract.getLocalSymbol())
        .exchange(contract.getExchange());
    if (contract.getStrike() != null) {
      this.orderParameter.setStrike(contract.getStrike().toString());
    }

    if (contract.getSecType() != null) {
      this.orderParameter.setSecType(SecType.valueOf(contract.getSecType()));
    }

    if (contract.getMarket() != null) {
      this.orderParameter.setMarket(contract.getMarket());
    }

    if (contract.getCurrency() != null) {
      this.orderParameter.setCurrency(Currency.valueOf(contract.getCurrency()));
    }

    if (contract.getMultiplier() != null) {
      this.orderParameter.setMultiplier(contract.getMultiplier().floatValue());
    }

    return this;
  }

  public TradeParamBuilder attachType(AttachType attachType) {
    if (attachType != null) {
      this.orderParameter.setAttachType(attachType);
    }
    return this;
  }

  public TradeParamBuilder profitTakerOrderId(Integer profitTakerOrderId) {
    if (profitTakerOrderId != null) {
      this.orderParameter.setProfitTakerOrderId(profitTakerOrderId);
    }
    return this;
  }

  public TradeParamBuilder profitTakerPrice(Double profitTakerPrice) {
    if (profitTakerPrice != null) {
      this.orderParameter.setProfitTakerPrice(profitTakerPrice);
    }
    return this;
  }

  public TradeParamBuilder profitTakerTif(TimeInForce profitTakerTif) {
    if (profitTakerTif != null) {
      this.orderParameter.setProfitTakerTif(profitTakerTif);
    }
    return this;
  }

  public TradeParamBuilder profitTakerRth(Boolean profitTakerRth) {
    if (profitTakerRth) {
      this.orderParameter.setProfitTakerRth(profitTakerRth);
    }
    return this;
  }

  public TradeParamBuilder setStopLossOrderType(OrderType stopLossOrderType) {
    if (stopLossOrderType != null) {
      this.orderParameter.setStopLossOrderType(stopLossOrderType);
    }
    return this;
  }

  public TradeParamBuilder stopLossOrderId(Integer stopLossOrderId) {
    if (stopLossOrderId != null) {
      this.orderParameter.setStopLossOrderId(stopLossOrderId);
    }
    return this;
  }

  public TradeParamBuilder stopLossPrice(Double stopLossPrice) {
    if (stopLossPrice != null) {
      this.orderParameter.setStopLossPrice(stopLossPrice);
    }
    return this;
  }

  public TradeParamBuilder setStopLossLimitPrice(Double stopLossLimitPrice) {
    if (stopLossLimitPrice != null) {
      this.orderParameter.setStopLossLimitPrice(stopLossLimitPrice);
    }
    return this;
  }

  public TradeParamBuilder stopLossTif(TimeInForce stopLossTif) {
    if (stopLossTif != null) {
      this.orderParameter.setStopLossTif(stopLossTif);
    }
    return this;
  }

  public TradeParamBuilder setStopLossTrailingPercent(Double stopLossTrailingPercent) {
    if (stopLossTrailingPercent != null) {
      this.orderParameter.setStopLossTrailingPercent(stopLossTrailingPercent);
    }
    return this;
  }

  public TradeParamBuilder setStopLossTrailingAmount(Double stopLossTrailingAmount) {
    if (stopLossTrailingAmount != null) {
      this.orderParameter.setStopLossTrailingAmount(stopLossTrailingAmount);
    }
    return this;
  }

  public TradeParamBuilder appendOcaOrders(OrderParameter orderParameter) {
    if (this.orderParameter.getOcaOrders() == null) {
      this.orderParameter.setOcaOrders(new ArrayList<>());
    }
    this.orderParameter.getOcaOrders().add(orderParameter);
    return this;
  }

  public TradeParamBuilder lang(Language lang) {
    if (lang != null) {
      this.orderParameter.setLang(lang.name());
    }
    return this;
  }

  public OrderParameter build() {
    if (StringUtils.isEmpty(this.orderParameter.getAccount())) {
      this.orderParameter.setAccount(ClientConfig.DEFAULT_CONFIG.defaultAccount);
    }
    if (StringUtils.isEmpty(this.orderParameter.getLang())) {
      this.orderParameter.setLang(ClientConfig.DEFAULT_CONFIG.getDefaultLanguage().name());
    }
    return this.orderParameter;
  }

  public String buildJson() {
    return JSONObject.toJSONString(build(), FastJsonPropertyFilter.getPropertyFilter());
  }
}
