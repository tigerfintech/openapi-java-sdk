package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.constant.TradeConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.ContractLeg;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.TradeOrderModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.TradeOrderPreviewResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.TradeOrderResponse;
import com.tigerbrokers.stock.openapi.client.struct.TagValue;
import com.tigerbrokers.stock.openapi.client.struct.enums.*;
import com.tigerbrokers.stock.openapi.client.util.AccountUtil;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class TradeOrderPreviewRequest extends TigerCommonRequest implements TigerRequest<TradeOrderPreviewResponse> {

  public TradeOrderPreviewRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.PREVIEW_ORDER);
  }

  public static TradeOrderPreviewRequest buildMarketOrder(ContractItem contract,
                                                          ActionType action, Integer quantity) {
    return buildMarketOrder(ClientConfig.DEFAULT_CONFIG.defaultAccount,
        contract, action, quantity);
  }

  public static TradeOrderPreviewRequest buildMarketOrder(String account, ContractItem contract,
                                                          ActionType action, Integer quantity) {
    TradeOrderModel tradeOrderModel = buildTradeOrderModel(account, contract, action, convertToLong(quantity), null);
    tradeOrderModel.setOrderType(OrderType.MKT);
    return newRequest(tradeOrderModel);
  }

  public static TradeOrderPreviewRequest buildMarketOrder(ContractItem contract,
                                                          ActionType action, Long quantity, Integer quantityScale) {
    return buildMarketOrder(ClientConfig.DEFAULT_CONFIG.defaultAccount,
        contract, action, quantity, quantityScale);
  }

  public static TradeOrderPreviewRequest buildMarketOrder(String account, ContractItem contract,
                                                          ActionType action, Long quantity, Integer quantityScale) {
    TradeOrderModel tradeOrderModel = buildTradeOrderModel(account, contract, action, quantity, quantityScale);
    tradeOrderModel.setOrderType(OrderType.MKT);
    return newRequest(tradeOrderModel);
  }

  public static TradeOrderPreviewRequest buildLimitOrder(ContractItem contract,
                                                         ActionType action, Integer quantity, Double limitPrice) {
    return buildLimitOrder(ClientConfig.DEFAULT_CONFIG.defaultAccount,
        contract, action, quantity, limitPrice);
  }

  public static TradeOrderPreviewRequest buildLimitOrder(String account, ContractItem contract,
                                                         ActionType action, Integer quantity, Double limitPrice) {
    return buildLimitOrder(account, contract, action, quantity, limitPrice, 0D);
  }

  public static TradeOrderPreviewRequest buildLimitOrder(String account, ContractItem contract,
                                                         ActionType action, Integer quantity, Double limitPrice, Double adjustLimit) {
    TradeOrderModel tradeOrderModel = buildTradeOrderModel(account, contract, action, convertToLong(quantity), null);
    tradeOrderModel.setOrderType(OrderType.LMT);
    tradeOrderModel.setLimitPrice(limitPrice);
    tradeOrderModel.setAdjustLimit(adjustLimit);
    return newRequest(tradeOrderModel);
  }

  public static TradeOrderPreviewRequest buildLimitOrder(ContractItem contract,
                                                         ActionType action, Long quantity, Integer quantityScale, Double limitPrice) {
    return buildLimitOrder(ClientConfig.DEFAULT_CONFIG.defaultAccount,
        contract, action, quantity, quantityScale, limitPrice);
  }

  public static TradeOrderPreviewRequest buildLimitOrder(String account, ContractItem contract,
                                                         ActionType action, Long quantity, Integer quantityScale, Double limitPrice) {
    return buildLimitOrder(account, contract, action, quantity, quantityScale, limitPrice, 0D);
  }

  public static TradeOrderPreviewRequest buildLimitOrder(String account, ContractItem contract,
                                                         ActionType action, Long quantity, Integer quantityScale, Double limitPrice, Double adjustLimit) {
    TradeOrderModel tradeOrderModel = buildTradeOrderModel(account, contract, action, quantity, quantityScale);
    tradeOrderModel.setOrderType(OrderType.LMT);
    tradeOrderModel.setLimitPrice(limitPrice);
    tradeOrderModel.setAdjustLimit(adjustLimit);
    return newRequest(tradeOrderModel);
  }

  public static TradeOrderPreviewRequest buildStopOrder(ContractItem contract,
                                                        ActionType action, Integer quantity, Double auxPrice) {
    return buildStopOrder(ClientConfig.DEFAULT_CONFIG.defaultAccount,
        contract, action, quantity, auxPrice);
  }

  public static TradeOrderPreviewRequest buildStopOrder(String account, ContractItem contract,
                                                        ActionType action, Integer quantity, Double auxPrice) {
    return buildStopOrder(account, contract, action, quantity, auxPrice, 0D);
  }

  public static TradeOrderPreviewRequest buildStopOrder(String account, ContractItem contract,
                                                        ActionType action, Integer quantity, Double auxPrice, Double adjustLimit) {
    TradeOrderModel tradeOrderModel = buildTradeOrderModel(account, contract, action, convertToLong(quantity), null);
    tradeOrderModel.setOrderType(OrderType.STP);
    tradeOrderModel.setAuxPrice(auxPrice);
    tradeOrderModel.setAdjustLimit(adjustLimit);
    return newRequest(tradeOrderModel);
  }

  public static TradeOrderPreviewRequest buildStopOrder(ContractItem contract,
                                                        ActionType action, Long quantity, Integer quantityScale, Double auxPrice) {
    return buildStopOrder(ClientConfig.DEFAULT_CONFIG.defaultAccount,
        contract, action, quantity, quantityScale, auxPrice);
  }

  public static TradeOrderPreviewRequest buildStopOrder(String account, ContractItem contract,
                                                        ActionType action, Long quantity, Integer quantityScale, Double auxPrice) {
    return buildStopOrder(account, contract, action, quantity, quantityScale, auxPrice, 0D);
  }

  public static TradeOrderPreviewRequest buildStopOrder(String account, ContractItem contract,
                                                        ActionType action, Long quantity, Integer quantityScale, Double auxPrice, Double adjustLimit) {
    TradeOrderModel tradeOrderModel = buildTradeOrderModel(account, contract, action, quantity, quantityScale);
    tradeOrderModel.setOrderType(OrderType.STP);
    tradeOrderModel.setAuxPrice(auxPrice);
    tradeOrderModel.setAdjustLimit(adjustLimit);
    return newRequest(tradeOrderModel);
  }

  public static TradeOrderPreviewRequest buildStopLimitOrder(ContractItem contract,
                                                             ActionType action, Integer quantity, Double limitPrice, Double auxPrice) {
    return buildStopLimitOrder(ClientConfig.DEFAULT_CONFIG.defaultAccount,
        contract, action, quantity, limitPrice, auxPrice);
  }

  public static TradeOrderPreviewRequest buildStopLimitOrder(String account, ContractItem contract,
                                                             ActionType action, Integer quantity, Double limitPrice, Double auxPrice) {
    return buildStopLimitOrder(account, contract, action, quantity, limitPrice, auxPrice, 0D);
  }

  public static TradeOrderPreviewRequest buildStopLimitOrder(String account, ContractItem contract,
                                                             ActionType action, Integer quantity, Double limitPrice, Double auxPrice, Double adjustLimit) {
    TradeOrderModel tradeOrderModel = buildTradeOrderModel(account, contract, action, convertToLong(quantity), null);
    tradeOrderModel.setOrderType(OrderType.STP_LMT);
    tradeOrderModel.setLimitPrice(limitPrice);
    tradeOrderModel.setAuxPrice(auxPrice);
    tradeOrderModel.setAdjustLimit(adjustLimit);
    return newRequest(tradeOrderModel);
  }

  public static TradeOrderPreviewRequest buildStopLimitOrder(ContractItem contract,
                                                             ActionType action, Long quantity, Integer quantityScale, Double limitPrice, Double auxPrice) {
    return buildStopLimitOrder(ClientConfig.DEFAULT_CONFIG.defaultAccount,
        contract, action, quantity, quantityScale, limitPrice, auxPrice);
  }

  public static TradeOrderPreviewRequest buildStopLimitOrder(String account, ContractItem contract,
                                                             ActionType action, Long quantity, Integer quantityScale, Double limitPrice, Double auxPrice) {
    return buildStopLimitOrder(account, contract, action, quantity, quantityScale, limitPrice, auxPrice, 0D);
  }

  public static TradeOrderPreviewRequest buildStopLimitOrder(String account, ContractItem contract,
                                                             ActionType action, Long quantity, Integer quantityScale, Double limitPrice, Double auxPrice, Double adjustLimit) {
    TradeOrderModel tradeOrderModel = buildTradeOrderModel(account, contract, action, quantity, quantityScale);
    tradeOrderModel.setOrderType(OrderType.STP_LMT);
    tradeOrderModel.setLimitPrice(limitPrice);
    tradeOrderModel.setAuxPrice(auxPrice);
    tradeOrderModel.setAdjustLimit(adjustLimit);
    return newRequest(tradeOrderModel);
  }

  public static TradeOrderPreviewRequest buildTrailOrder(ContractItem contract,
                                                         ActionType action, Integer quantity, Double trailingPercent, Double auxPrice) {
    return buildTrailOrder(ClientConfig.DEFAULT_CONFIG.defaultAccount,
        contract, action, quantity, trailingPercent, auxPrice);
  }

  public static TradeOrderPreviewRequest buildTrailOrder(String account, ContractItem contract,
                                                         ActionType action, Integer quantity, Double trailingPercent, Double auxPrice) {
    TradeOrderModel tradeOrderModel = buildTradeOrderModel(account, contract, action, convertToLong(quantity), null);
    tradeOrderModel.setOrderType(OrderType.TRAIL);
    tradeOrderModel.setTrailingPercent(trailingPercent);
    tradeOrderModel.setAuxPrice(auxPrice);
    return newRequest(tradeOrderModel);
  }

  public static TradeOrderPreviewRequest buildTrailOrder(ContractItem contract,
                                                         ActionType action, Long quantity, Integer quantityScale, Double trailingPercent, Double auxPrice) {
    return buildTrailOrder(ClientConfig.DEFAULT_CONFIG.defaultAccount,
        contract, action, quantity, quantityScale, trailingPercent, auxPrice);
  }

  public static TradeOrderPreviewRequest buildTrailOrder(String account, ContractItem contract,
                                                         ActionType action, Long quantity, Integer quantityScale, Double trailingPercent, Double auxPrice) {
    TradeOrderModel tradeOrderModel = buildTradeOrderModel(account, contract, action, quantity, quantityScale);
    tradeOrderModel.setOrderType(OrderType.TRAIL);
    tradeOrderModel.setTrailingPercent(trailingPercent);
    tradeOrderModel.setAuxPrice(auxPrice);
    return newRequest(tradeOrderModel);
  }

  public static TradeOrderPreviewRequest buildAmountOrder(String account, ContractItem contract,
                                                          ActionType action, Double cashAmount) {
    TradeOrderModel tradeOrderModel = buildTradeOrderModel(account, contract, action, null, null);
    tradeOrderModel.setOrderType(OrderType.MKT);
    tradeOrderModel.setCashAmount(cashAmount);
    return newRequest(tradeOrderModel);
  }

  public static TradeOrderModel buildTradeOrderModel(String account, ContractItem contract,
      ActionType action, Long quantity, Integer quantityScale) {
    if (contract == null) {
      throw new IllegalArgumentException("parameter 'contract' is null");
    }
    TradeOrderModel model = new TradeOrderModel();
    model.setAccount(account);
    model.setAction(action);
    model.setTotalQuantity(quantity);
    model.setTotalQuantityScale(quantityScale);
    model.setSymbol(contract.getSymbol());
    model.setCurrency(contract.getCurrency() == null ? null : Currency.valueOf(contract.getCurrency()));
    model.setSecType(contract.getSecType() == null ? null : SecType.valueOf(contract.getSecType()));
    model.setExchange(contract.getExchange());
    model.setMarket(contract.getMarket());
    model.setLocalSymbol(contract.getLocalSymbol());
    model.setExpiry(contract.getExpiry());
    model.setStrike(contract.getStrike() == null ? null : contract.getStrike().toString());
    model.setRight(contract.getRight());
    model.setMultiplier(contract.getMultiplier() == null ? null : contract.getMultiplier().floatValue());
    if (model.getSecType() == SecType.FUT) {
      if (AccountUtil.isGlobalAccount(account)) {
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

  public static TradeOrderPreviewRequest buildMultiLegOrder(String account,
                                                            List<ContractLeg> contractLegs, ComboType comboType, ActionType action, Integer quantity,
                                                            OrderType orderType, Double limitPrice, Double auxPrice, Double trailingPercent) {
    return buildMultiLegOrder(account, contractLegs, comboType, action, convertToLong(quantity),
        null, orderType, limitPrice, auxPrice, trailingPercent);
  }

  public static TradeOrderPreviewRequest buildMultiLegOrder(String account,
                                                            List<ContractLeg> contractLegs, ComboType comboType, ActionType action, Long quantity, Integer quantityScale,
                                                            OrderType orderType, Double limitPrice, Double auxPrice, Double trailingPercent) {
    if (contractLegs == null) {
      throw new IllegalArgumentException("parameter 'contractLegs' is null");
    }
    if (orderType == null) {
      throw new IllegalArgumentException("parameter 'orderType' is null");
    }
    TradeOrderModel model = new TradeOrderModel();
    model.setSecType(SecType.MLEG);
    model.setComboType(comboType.name());
    model.setAccount(StringUtils.isEmpty(account) ? ClientConfig.DEFAULT_CONFIG.defaultAccount : account);
    model.setAction(action);
    model.setTotalQuantity(quantity);
    model.setTotalQuantityScale(quantityScale);
    model.setContractLegs(contractLegs);

    model.setOrderType(orderType);
    model.setLimitPrice(limitPrice);
    model.setAuxPrice(auxPrice);
    model.setTrailingPercent(trailingPercent);
    model.setTimeInForce(TimeInForce.DAY);
    return newRequest(model);
  }

  public static TradeOrderPreviewRequest buildTWAPOrder(String account,
                                                        String symbol, ActionType action, Integer quantity,
                                                        Long startTime, Long endTime, Double limitPrice) {
    return buildWAPOrder(account, symbol, action, quantity, OrderType.TWAP,
        startTime, endTime, null, limitPrice);
  }

  public static TradeOrderPreviewRequest buildVWAPOrder(String account,
                                                        String symbol, ActionType action, Integer quantity,
                                                        Long startTime, Long endTime,
                                                        Double participationRate, Double limitPrice) {
    return buildWAPOrder(account, symbol, action, quantity, OrderType.VWAP,
        startTime, endTime, participationRate, limitPrice);
  }

  public static TradeOrderPreviewRequest buildWAPOrder(String account,
                                                       String symbol, ActionType action, Integer quantity,
                                                       OrderType orderType, Long startTime, Long endTime,
                                                       Double participationRate,
                                                       Double limitPrice) {
    return buildWAPOrder(account, symbol, action, convertToLong(quantity), null,
        orderType, startTime, endTime, participationRate, limitPrice);
  }

  public static TradeOrderPreviewRequest buildWAPOrder(String account,
                                                       String symbol, ActionType action, Long quantity, Integer quantityScale,
                                                       OrderType orderType, Long startTime, Long endTime,
                                                       //Boolean allowPastEndTime, Boolean noTakeLiq,
                                                       Double participationRate,
                                                       Double limitPrice) {
    if (OrderType.TWAP != orderType && OrderType.VWAP != orderType) {
      throw new IllegalArgumentException("parameter 'orderType' must be ['TWAP', 'VWAP']");
    }

    TradeOrderModel model = new TradeOrderModel();
    model.setOutsideRth(Boolean.FALSE);
    model.setSecType(SecType.STK);
    model.setAccount(StringUtils.isEmpty(account) ? ClientConfig.DEFAULT_CONFIG.defaultAccount : account);
    model.setAction(action);
    model.setTotalQuantity(quantity);
    model.setTotalQuantityScale(quantityScale);
    model.setSymbol(symbol);
    model.setOrderType(orderType);
    model.setLimitPrice(limitPrice);
    model.setTimeInForce(TimeInForce.DAY);

    model.setAlgoStrategy(orderType.name());
    model.addAlgoParam(TagValue.buildTagValue(TradeConstants.START_TIME, startTime));
    model.addAlgoParam(TagValue.buildTagValue(TradeConstants.END_TIME, endTime));
    //model.addAlgoParam(TagValue.buildTagValue(WAPOrderConstants.ALLOW_PAST_END_TIME, allowPastEndTime));
    if (OrderType.VWAP == orderType) {
      //model.addAlgoParam(TagValue.buildTagValue(WAPOrderConstants.NO_TAKE_LIQ, noTakeLiq));
      model.addAlgoParam(TagValue.buildTagValue(TradeConstants.PARTICIPATION_RATE, participationRate));
    }
    return newRequest(model);
  }

  public static TradeOrderPreviewRequest buildOCABracketsOrder(
          ContractItem contract, ActionType action, Integer quantity,
          Double profitTakerPrice, TimeInForce profitTakerTif, Boolean profitTakerRth,
          Double stopLossPrice, Double stopLossLimitPrice, TimeInForce stopLossTif, Boolean stopLossRth) {
    return buildOCABracketsOrder(ClientConfig.DEFAULT_CONFIG.defaultAccount, contract, action, convertToLong(quantity), null,
            profitTakerPrice, profitTakerTif, profitTakerRth,
            stopLossPrice, stopLossLimitPrice, stopLossTif, stopLossRth);
  }

  public static TradeOrderPreviewRequest buildOCABracketsOrder(
          String account, ContractItem contract, ActionType action, Long quantity, Integer quantityScale,
          Double profitTakerPrice, TimeInForce profitTakerTif, Boolean profitTakerRth,
          Double stopLossPrice, Double stopLossLimitPrice, TimeInForce stopLossTif, Boolean stopLossRth) {
    TradeOrderModel profitTakerOrder = buildTradeOrderModel(account, contract, action, quantity, quantityScale);
    profitTakerOrder.setOrderType(OrderType.LMT);
    profitTakerOrder.setLimitPrice(profitTakerPrice);
    profitTakerOrder.setTimeInForce(profitTakerTif);
    profitTakerOrder.setOutsideRth(profitTakerRth);

    TradeOrderModel stopLossOrder = buildTradeOrderModel(account, contract, action, quantity, quantityScale);
    if (stopLossLimitPrice == null) {
      stopLossOrder.setOrderType(OrderType.STP);
    } else {
      stopLossOrder.setOrderType(OrderType.STP_LMT);
      stopLossOrder.setLimitPrice(stopLossLimitPrice);
    }
    stopLossOrder.setAuxPrice(stopLossPrice);
    stopLossOrder.setTimeInForce(stopLossTif);
    stopLossOrder.setOutsideRth(stopLossRth);

    List<TradeOrderModel> ocaOrders = new ArrayList<>();
    ocaOrders.add(profitTakerOrder);
    ocaOrders.add(stopLossOrder);

    TradeOrderModel tradeOrderModel = new TradeOrderModel();
    tradeOrderModel.setOcaOrders(ocaOrders);
    tradeOrderModel.setAccount(account);
    return newRequest(tradeOrderModel);
  }

  public static TradeOrderPreviewRequest newRequest(TradeOrderModel model) {
    TradeOrderPreviewRequest request = new TradeOrderPreviewRequest();
    request.setApiModel(model);
    return request;
  }

  public static void addProfitTakerOrder(TradeOrderPreviewRequest tradeOrderRequest,
                                         Double profitTakerPrice, TimeInForce profitTakerTif, Boolean profitTakerRth) {
    TradeOrderModel model = (TradeOrderModel) tradeOrderRequest.getApiModel();
    model.setAttachType(AttachType.PROFIT);
    model.setProfitTakerPrice(profitTakerPrice);
    model.setProfitTakerTif(profitTakerTif);
    model.setProfitTakerRth(profitTakerRth);
  }

  public static void addStopLossOrder(TradeOrderPreviewRequest tradeOrderRequest,
                                      Double stopLossPrice, TimeInForce stopLossTif) {
    TradeOrderModel model = (TradeOrderModel) tradeOrderRequest.getApiModel();
    model.setAttachType(AttachType.LOSS);
    model.setStopLossOrderType(OrderType.STP);
    model.setStopLossPrice(stopLossPrice);
    model.setStopLossTif(stopLossTif);
  }

  public static void addStopLossLimitOrder(TradeOrderPreviewRequest tradeOrderRequest,
                                           Double stopLossPrice, Double stopLossLimitPrice, TimeInForce stopLossTif) {
    TradeOrderModel model = (TradeOrderModel) tradeOrderRequest.getApiModel();
    model.setAttachType(AttachType.LOSS);
    model.setStopLossOrderType(OrderType.STP_LMT);
    model.setStopLossPrice(stopLossPrice);
    model.setStopLossLimitPrice(stopLossLimitPrice);
    model.setStopLossTif(stopLossTif);
  }

  public static void addStopLossTrailOrder(TradeOrderPreviewRequest tradeOrderRequest,
                                           Double stopLossTrailingPercent, Double stopLossTrailingAmount, TimeInForce stopLossTif) {
    TradeOrderModel model = (TradeOrderModel) tradeOrderRequest.getApiModel();
    model.setAttachType(AttachType.LOSS);
    model.setStopLossOrderType(OrderType.TRAIL);
    model.setStopLossTrailingPercent(stopLossTrailingPercent);
    model.setStopLossTrailingAmount(stopLossTrailingAmount);
    model.setStopLossTif(stopLossTif);
  }

  public static void addBracketsOrder(TradeOrderPreviewRequest tradeOrderRequest,
                                      Double profitTakerPrice, TimeInForce profitTakerTif, Boolean profitTakerRth,
                                      Double stopLossPrice, TimeInForce stopLossTif) {
    addBracketsOrder(tradeOrderRequest, profitTakerPrice, profitTakerTif, profitTakerRth,
        stopLossPrice, null, stopLossTif);
  }

  public static void addBracketsOrder(TradeOrderPreviewRequest tradeOrderRequest,
                                      Double profitTakerPrice, TimeInForce profitTakerTif, Boolean profitTakerRth,
                                      Double stopLossPrice, Double stopLossLimitPrice, TimeInForce stopLossTif) {
    TradeOrderModel model = (TradeOrderModel) tradeOrderRequest.getApiModel();
    model.setAttachType(AttachType.BRACKETS);
    model.setProfitTakerPrice(profitTakerPrice);
    model.setProfitTakerTif(profitTakerTif);
    model.setProfitTakerRth(profitTakerRth);
    model.setStopLossPrice(stopLossPrice);
    model.setStopLossLimitPrice(stopLossLimitPrice);
    model.setStopLossTif(stopLossTif);
  }

  public TradeOrderPreviewRequest setOrderId(Integer orderId) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setOrderId(orderId);
    return this;
  }

  public TradeOrderPreviewRequest setAccount(String account) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setAccount(account);
    return this;
  }

  public TradeOrderPreviewRequest setSecretKey(String secretKey) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setSecretKey(secretKey);
    return this;
  }

  public TradeOrderPreviewRequest setSymbol(String symbol) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setSymbol(symbol);
    return this;
  }

  public TradeOrderPreviewRequest setSecType(SecType secType) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setSecType(secType);
    return this;
  }

  public TradeOrderPreviewRequest setAction(ActionType action) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setAction(action);
    return this;
  }

  public TradeOrderPreviewRequest setCurrency(Currency currency) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setCurrency(currency);
    return this;
  }

  public TradeOrderPreviewRequest setTotalQuantity(Long totalQuantity) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setTotalQuantity(totalQuantity);
    return this;
  }

  public TradeOrderPreviewRequest setTotalQuantityScale(Integer totalQuantityScale) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setTotalQuantityScale(totalQuantityScale);
    return this;
  }

  public TradeOrderPreviewRequest setCashAmount(Double cashAmount) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setCashAmount(cashAmount);
    return this;
  }

  public TradeOrderPreviewRequest setOrderType(OrderType orderType) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setOrderType(orderType);
    return this;
  }

  public TradeOrderPreviewRequest setLimitPrice(Double limitPrice) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setLimitPrice(limitPrice);
    return this;
  }

  public TradeOrderPreviewRequest setAdjustLimit(Double adjustLimit) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setAdjustLimit(adjustLimit);
    return this;
  }

  public TradeOrderPreviewRequest setAuxPrice(Double auxPrice) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setAuxPrice(auxPrice);
    return this;
  }

  public TradeOrderPreviewRequest setTrailingPercent(Double trailingPercent) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setTrailingPercent(trailingPercent);
    return this;
  }

  public TradeOrderPreviewRequest setOutsideRth(Boolean outsideRth) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setOutsideRth(outsideRth);
    return this;
  }

  public TradeOrderPreviewRequest setMarket(String market) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setMarket(market);
    return this;
  }

  public TradeOrderPreviewRequest setExchange(String exchange) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setExchange(exchange);
    return this;
  }

  public TradeOrderPreviewRequest setExpiry(String expiry) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setExpiry(expiry);
    return this;
  }

  public TradeOrderPreviewRequest setStrike(String strike) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setStrike(strike);
    return this;
  }

  public TradeOrderPreviewRequest setRight(String right) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setRight(right);
    return this;
  }

  public TradeOrderPreviewRequest setMultiplier(Float multiplier) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setMultiplier(multiplier);
    return this;
  }

  public TradeOrderPreviewRequest setLocalSymbol(String localSymbol) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setLocalSymbol(localSymbol);
    return this;
  }

  public TradeOrderPreviewRequest setAllocAccounts(List<String> allocAccounts) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setAllocAccounts(allocAccounts);
    return this;
  }

  public TradeOrderPreviewRequest setAllocShares(List<Double> allocShares) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setAllocShares(allocShares);
    return this;
  }

  public TradeOrderPreviewRequest setAlgoStrategy(String algoStrategy) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setAlgoStrategy(algoStrategy);
    return this;
  }

  public TradeOrderPreviewRequest setAlgoParams(List<TagValue> algoParams) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setAlgoParams(algoParams);
    return this;
  }

  public TradeOrderPreviewRequest setAttachType(AttachType attachType) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setAttachType(attachType);
    return this;
  }

  public TradeOrderPreviewRequest setProfitTakerOrderId(Integer profitTakerOrderId) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setProfitTakerOrderId(profitTakerOrderId);
    return this;
  }

  public TradeOrderPreviewRequest setProfitTakerPrice(Double profitTakerPrice) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setProfitTakerPrice(profitTakerPrice);
    return this;
  }

  public TradeOrderPreviewRequest setProfitTakerTif(TimeInForce profitTakerTif) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setProfitTakerTif(profitTakerTif);
    return this;
  }

  public TradeOrderPreviewRequest setProfitTakerRth(Boolean profitTakerRth) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setProfitTakerRth(profitTakerRth);
    return this;
  }

  public TradeOrderPreviewRequest setStopLossOrderType(OrderType stopLossOrderType) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setStopLossOrderType(stopLossOrderType);
    return this;
  }

  public TradeOrderPreviewRequest setStopLossOrderId(Integer stopLossOrderId) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setStopLossOrderId(stopLossOrderId);
    return this;
  }

  public TradeOrderPreviewRequest setStopLossPrice(Double stopLossPrice) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setStopLossPrice(stopLossPrice);
    return this;
  }

  public TradeOrderPreviewRequest setStopLossLimitPrice(Double stopLossLimitPrice) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setStopLossLimitPrice(stopLossLimitPrice);
    return this;
  }

  public TradeOrderPreviewRequest setStopLossTif(TimeInForce stopLossTif) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setStopLossTif(stopLossTif);
    return this;
  }

  public TradeOrderPreviewRequest setStopLossTrailingPercent(Double stopLossTrailingPercent) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setStopLossTrailingPercent(stopLossTrailingPercent);
    return this;
  }

  public TradeOrderPreviewRequest setStopLossTrailingAmount(Double stopLossTrailingAmount) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setStopLossTrailingAmount(stopLossTrailingAmount);
    return this;
  }

  public TradeOrderPreviewRequest withUserMark(String userMark) {
    return setUserMark(userMark);
  }
  public TradeOrderPreviewRequest setUserMark(String userMark) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setUserMark(userMark);
    return this;
  }

  public TradeOrderPreviewRequest setTimeInForce(TimeInForce timeInForce) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setTimeInForce(timeInForce);
    return this;
  }

  public TradeOrderPreviewRequest setExpireTime(Long expireTime) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setExpireTime(expireTime);
    return this;
  }

  // compatible, please use setTradingSessionType(TradingSessionType tradingSessionType)
  @Deprecated
  public TradeOrderPreviewRequest setTradingSessionType(TradeSession tradingSessionType) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    if (TradeSession.OverNight == tradingSessionType) {
      model.setTradingSessionType(TradingSessionType.OVERNIGHT);
    }
    return this;
  }

  public TradeOrderPreviewRequest setTradingSessionType(TradingSessionType tradingSessionType) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setTradingSessionType(tradingSessionType);
    return this;
  }

  public TradeOrderPreviewRequest setLang(Language lang) {
    ApiModel model = getApiModel();
    model.setLang(lang);
    return this;
  }

  /**
   * set action order in the Hong Kong Stock Exchange
   * @param orderType AM(Auction Market Order) or AL(Auction Limit Order)
   * @param timeInForce DAY/OPG,
   *                   OPG:Participate in the pre-market auction;
   *                   DAY:Participate in the after-hours auction
   * @return
   */
  public TradeOrderPreviewRequest setAuctionOrder(OrderType orderType, TimeInForce timeInForce) {
    if (orderType == OrderType.AM || orderType == OrderType.AL) {
      TradeOrderModel model = (TradeOrderModel) getApiModel();
      model.setOrderType(orderType);
      model.setTimeInForce(timeInForce == TimeInForce.OPG ? timeInForce : TimeInForce.DAY);
    }
    return this;
  }

  private static Long convertToLong(Integer arg) {
    return null == arg ? null : Long.valueOf(arg.longValue());
  }

  @Override
  public Class<TradeOrderPreviewResponse> getResponseClass() {
    return TradeOrderPreviewResponse.class;
  }
}
