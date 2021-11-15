package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.TradeOrderModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.TradeOrderResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.AccountType;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.AttachType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderType;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeInForce;
import com.tigerbrokers.stock.openapi.client.util.AccountUtil;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;

public class TradeOrderRequest extends TigerCommonRequest implements TigerRequest<TradeOrderResponse> {

  public TradeOrderRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(ApiServiceType.PLACE_ORDER);
  }

  /**
   * use ClientConfig.DEFAULT_CONFIG.defaultAccount
   */
  public static TradeOrderRequest buildMarketOrder(ContractItem contract,
      ActionType action, Integer quantity) {
    return buildMarketOrder(ClientConfig.DEFAULT_CONFIG.defaultAccount,
        contract, action, quantity);
  }

  public static TradeOrderRequest buildMarketOrder(String account, ContractItem contract,
      ActionType action, Integer quantity) {
    TradeOrderModel tradeOrderModel = buildTradeOrderModel(account, contract, action, quantity);
    tradeOrderModel.setOrderType(OrderType.MKT);
    return newRequest(tradeOrderModel);
  }

  /**
   * use ClientConfig.DEFAULT_CONFIG.defaultAccount
   */
  public static TradeOrderRequest buildLimitOrder(ContractItem contract,
      ActionType action, Integer quantity, Double limitPrice) {
    return buildLimitOrder(ClientConfig.DEFAULT_CONFIG.defaultAccount,
        contract, action, quantity, limitPrice);
  }

  public static TradeOrderRequest buildLimitOrder(String account, ContractItem contract,
      ActionType action, Integer quantity, Double limitPrice) {
    TradeOrderModel tradeOrderModel = buildTradeOrderModel(account, contract, action, quantity);
    tradeOrderModel.setOrderType(OrderType.LMT);
    tradeOrderModel.setLimitPrice(limitPrice);
    return newRequest(tradeOrderModel);
  }

  /**
   * use ClientConfig.DEFAULT_CONFIG.defaultAccount
   */
  public static TradeOrderRequest buildStopOrder(ContractItem contract,
      ActionType action, Integer quantity, Double auxPrice) {
    return buildStopOrder(ClientConfig.DEFAULT_CONFIG.defaultAccount,
        contract, action, quantity, auxPrice);
  }

  public static TradeOrderRequest buildStopOrder(String account, ContractItem contract,
      ActionType action, Integer quantity, Double auxPrice) {
    TradeOrderModel tradeOrderModel = buildTradeOrderModel(account, contract, action, quantity);
    tradeOrderModel.setOrderType(OrderType.STP);
    tradeOrderModel.setAuxPrice(auxPrice);
    return newRequest(tradeOrderModel);
  }

  /**
   * use ClientConfig.DEFAULT_CONFIG.defaultAccount
   */
  public static TradeOrderRequest buildStopLimitOrder(ContractItem contract,
      ActionType action, Integer quantity, Double limitPrice, Double auxPrice) {
    return buildStopLimitOrder(ClientConfig.DEFAULT_CONFIG.defaultAccount,
        contract, action, quantity, limitPrice, auxPrice);
  }

  public static TradeOrderRequest buildStopLimitOrder(String account, ContractItem contract,
      ActionType action, Integer quantity, Double limitPrice, Double auxPrice) {
    TradeOrderModel tradeOrderModel = buildTradeOrderModel(account, contract, action, quantity);
    tradeOrderModel.setOrderType(OrderType.STP_LMT);
    tradeOrderModel.setLimitPrice(limitPrice);
    tradeOrderModel.setAuxPrice(auxPrice);
    return newRequest(tradeOrderModel);
  }

  /**
   * use ClientConfig.DEFAULT_CONFIG.defaultAccount
   */
  public static TradeOrderRequest buildTrailOrder(ContractItem contract,
      ActionType action, Integer quantity, Double trailingPercent, Double auxPrice) {
    return buildTrailOrder(ClientConfig.DEFAULT_CONFIG.defaultAccount,
        contract, action, quantity, trailingPercent, auxPrice);
  }

  public static TradeOrderRequest buildTrailOrder(String account, ContractItem contract,
      ActionType action, Integer quantity, Double trailingPercent, Double auxPrice) {
    TradeOrderModel tradeOrderModel = buildTradeOrderModel(account, contract, action, quantity);
    tradeOrderModel.setOrderType(OrderType.TRAIL);
    tradeOrderModel.setTrailingPercent(trailingPercent);
    tradeOrderModel.setAuxPrice(auxPrice);
    return newRequest(tradeOrderModel);
  }

  public static TradeOrderModel buildTradeOrderModel(String account, ContractItem contract,
      ActionType action, Integer quantity) {
    if (contract == null) {
      throw new IllegalArgumentException("parameter 'contract' is null");
    }
    AccountType accountType = AccountUtil.getAccountType(account);
    TradeOrderModel model = TradeOrderModel.builder().account(account)
        .action(action).totalQuantity(quantity)
        .symbol(contract.getSymbol())
        .currency(contract.getCurrency() == null ? null : Currency.valueOf(contract.getCurrency()))
        .secType(contract.getSecType() == null ? null : SecType.valueOf(contract.getSecType()))
        .exchange(contract.getExchange())
        .market(contract.getMarket())
        .localSymbol(contract.getLocalSymbol())
        .expiry(contract.getExpiry())
        .strike(contract.getStrike() == null ? null : contract.getStrike().toString())
        .right(contract.getRight())
        .multiplier(contract.getMultiplier() == null ? null : contract.getMultiplier().floatValue())
        .build();
    if (model.getSecType() == SecType.FUT) {
      if (accountType == AccountType.GLOBAL) {
        if (!StringUtils.isEmpty(contract.getType())) {
          model.setSymbol(contract.getType());
        }
        if (StringUtils.isEmpty(model.getExpiry()) && !StringUtils.isEmpty(contract.getLastTradingDate())) {
          model.setExpiry(contract.getLastTradingDate());
        }
      } else {
        model.setExpiry(null);
      }
    }
    return model;
  }

  public static TradeOrderRequest newRequest(TradeOrderModel model) {
    TradeOrderRequest request = new TradeOrderRequest();
    request.setApiModel(model);
    return request;
  }

  public static void addProfitTakerOrder(TradeOrderRequest tradeOrderRequest,
      double profitTakerPrice, TimeInForce profitTakerTif, Boolean profitTakerRth) {
    TradeOrderModel model = (TradeOrderModel) tradeOrderRequest.getApiModel();
    model.setAttachType(AttachType.PROFIT);
    model.setProfitTakerPrice(profitTakerPrice);
    model.setProfitTakerTif(profitTakerTif);
    model.setProfitTakerRth(profitTakerRth);
  }

  public static void addStopLossOrder(TradeOrderRequest tradeOrderRequest,
      double stopLossPrice, TimeInForce stopLossTif) {
    TradeOrderModel model = (TradeOrderModel) tradeOrderRequest.getApiModel();
    model.setAttachType(AttachType.LOSS);
    model.setStopLossPrice(stopLossPrice);
    model.setStopLossTif(stopLossTif);
  }

  public static void addBracketsOrder(TradeOrderRequest tradeOrderRequest,
      double profitTakerPrice, TimeInForce profitTakerTif, Boolean profitTakerRth,
      double stopLossPrice, TimeInForce stopLossTif) {
    TradeOrderModel model = (TradeOrderModel) tradeOrderRequest.getApiModel();
    model.setAttachType(AttachType.BRACKETS);
    model.setProfitTakerPrice(profitTakerPrice);
    model.setProfitTakerTif(profitTakerTif);
    model.setProfitTakerRth(profitTakerRth);
    model.setStopLossPrice(stopLossPrice);
    model.setStopLossTif(stopLossTif);
  }

  @Override
  public Class<TradeOrderResponse> getResponseClass() {
    return TradeOrderResponse.class;
  }
}
