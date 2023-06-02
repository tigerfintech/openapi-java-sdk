package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.constant.VAPOrderConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.ContractLeg;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.TradeOrderModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.TradeOrderResponse;
import com.tigerbrokers.stock.openapi.client.struct.TagValue;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.AttachType;
import com.tigerbrokers.stock.openapi.client.struct.enums.ComboType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderType;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeInForce;
import com.tigerbrokers.stock.openapi.client.util.AccountUtil;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import java.util.List;

public class TradeOrderRequest extends TigerCommonRequest implements TigerRequest<TradeOrderResponse> {

  public TradeOrderRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.PLACE_ORDER);
  }

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

  public static TradeOrderRequest buildLimitOrder(ContractItem contract,
      ActionType action, Integer quantity, Double limitPrice) {
    return buildLimitOrder(ClientConfig.DEFAULT_CONFIG.defaultAccount,
        contract, action, quantity, limitPrice);
  }

  public static TradeOrderRequest buildLimitOrder(String account, ContractItem contract,
      ActionType action, Integer quantity, Double limitPrice) {
    return buildLimitOrder(account, contract, action, quantity, limitPrice, 0D);
  }

  public static TradeOrderRequest buildLimitOrder(String account, ContractItem contract,
      ActionType action, Integer quantity, Double limitPrice, Double adjustLimit) {
    TradeOrderModel tradeOrderModel = buildTradeOrderModel(account, contract, action, quantity);
    tradeOrderModel.setOrderType(OrderType.LMT);
    tradeOrderModel.setLimitPrice(limitPrice);
    tradeOrderModel.setAdjustLimit(adjustLimit);
    return newRequest(tradeOrderModel);
  }

  public static TradeOrderRequest buildStopOrder(ContractItem contract,
      ActionType action, Integer quantity, Double auxPrice) {
    return buildStopOrder(ClientConfig.DEFAULT_CONFIG.defaultAccount,
        contract, action, quantity, auxPrice);
  }

  public static TradeOrderRequest buildStopOrder(String account, ContractItem contract,
      ActionType action, Integer quantity, Double auxPrice) {
    return buildStopOrder(account, contract, action, quantity, auxPrice, 0D);
  }

  public static TradeOrderRequest buildStopOrder(String account, ContractItem contract,
      ActionType action, Integer quantity, Double auxPrice, Double adjustLimit) {
    TradeOrderModel tradeOrderModel = buildTradeOrderModel(account, contract, action, quantity);
    tradeOrderModel.setOrderType(OrderType.STP);
    tradeOrderModel.setAuxPrice(auxPrice);
    tradeOrderModel.setAdjustLimit(adjustLimit);
    return newRequest(tradeOrderModel);
  }

  public static TradeOrderRequest buildStopLimitOrder(ContractItem contract,
      ActionType action, Integer quantity, Double limitPrice, Double auxPrice) {
    return buildStopLimitOrder(ClientConfig.DEFAULT_CONFIG.defaultAccount,
        contract, action, quantity, limitPrice, auxPrice);
  }

  public static TradeOrderRequest buildStopLimitOrder(String account, ContractItem contract,
      ActionType action, Integer quantity, Double limitPrice, Double auxPrice) {
    return buildStopLimitOrder(account, contract, action, quantity, limitPrice, auxPrice, 0D);
  }

  public static TradeOrderRequest buildStopLimitOrder(String account, ContractItem contract,
      ActionType action, Integer quantity, Double limitPrice, Double auxPrice, Double adjustLimit) {
    TradeOrderModel tradeOrderModel = buildTradeOrderModel(account, contract, action, quantity);
    tradeOrderModel.setOrderType(OrderType.STP_LMT);
    tradeOrderModel.setLimitPrice(limitPrice);
    tradeOrderModel.setAuxPrice(auxPrice);
    tradeOrderModel.setAdjustLimit(adjustLimit);
    return newRequest(tradeOrderModel);
  }

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
    TradeOrderModel model = new TradeOrderModel();
    model.setAccount(account);
    model.setAction(action);
    model.setTotalQuantity(quantity);
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

  public static TradeOrderRequest buildMultiLegOrder(String account,
      List<ContractLeg> contractLegs, ComboType comboType, ActionType action, Integer quantity,
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
    model.setContractLegs(contractLegs);

    model.setOrderType(orderType);
    model.setLimitPrice(limitPrice);
    model.setAuxPrice(auxPrice);
    model.setTrailingPercent(trailingPercent);
    model.setTimeInForce(TimeInForce.DAY);
    return newRequest(model);
  }

  public static TradeOrderRequest buildTWAPOrder(String account,
      String symbol, ActionType action, Integer quantity,
      Long startTime, Long endTime, Double limitPrice) {
    return buildWAPOrder(account, symbol, action, quantity, OrderType.TWAP,
        startTime, endTime, null, limitPrice);
  }

  public static TradeOrderRequest buildVWAPOrder(String account,
      String symbol, ActionType action, Integer quantity,
      Long startTime, Long endTime,
      Double participationRate, Double limitPrice) {
    return buildWAPOrder(account, symbol, action, quantity, OrderType.VWAP,
        startTime, endTime, participationRate, limitPrice);
  }

  public static TradeOrderRequest buildWAPOrder(String account,
      String symbol, ActionType action, Integer quantity,
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
    model.setSymbol(symbol);
    model.setOrderType(orderType);
    model.setLimitPrice(limitPrice);
    model.setTimeInForce(TimeInForce.DAY);

    model.setAlgoStrategy(orderType.name());
    model.addAlgoParam(TagValue.buildTagValue(VAPOrderConstants.START_TIME, startTime));
    model.addAlgoParam(TagValue.buildTagValue(VAPOrderConstants.END_TIME, endTime));
    //model.addAlgoParam(TagValue.buildTagValue(VAPOrderConstants.ALLOW_PAST_END_TIME, allowPastEndTime));
    if (OrderType.VWAP == orderType) {
      //model.addAlgoParam(TagValue.buildTagValue(VAPOrderConstants.NO_TAKE_LIQ, noTakeLiq));
      model.addAlgoParam(TagValue.buildTagValue(VAPOrderConstants.PARTICIPATION_RATE, participationRate));
    }
    return newRequest(model);
  }

  public static TradeOrderRequest newRequest(TradeOrderModel model) {
    TradeOrderRequest request = new TradeOrderRequest();
    request.setApiModel(model);
    return request;
  }

  public static void addProfitTakerOrder(TradeOrderRequest tradeOrderRequest,
      Double profitTakerPrice, TimeInForce profitTakerTif, Boolean profitTakerRth) {
    TradeOrderModel model = (TradeOrderModel) tradeOrderRequest.getApiModel();
    model.setAttachType(AttachType.PROFIT);
    model.setProfitTakerPrice(profitTakerPrice);
    model.setProfitTakerTif(profitTakerTif);
    model.setProfitTakerRth(profitTakerRth);
  }

  public static void addStopLossOrder(TradeOrderRequest tradeOrderRequest,
      Double stopLossPrice, TimeInForce stopLossTif) {
    TradeOrderModel model = (TradeOrderModel) tradeOrderRequest.getApiModel();
    model.setAttachType(AttachType.LOSS);
    model.setStopLossOrderType(OrderType.STP);
    model.setStopLossPrice(stopLossPrice);
    model.setStopLossTif(stopLossTif);
  }

  public static void addStopLossLimitOrder(TradeOrderRequest tradeOrderRequest,
      Double stopLossPrice, Double stopLossLimitPrice, TimeInForce stopLossTif) {
    TradeOrderModel model = (TradeOrderModel) tradeOrderRequest.getApiModel();
    model.setAttachType(AttachType.LOSS);
    model.setStopLossOrderType(OrderType.STP_LMT);
    model.setStopLossPrice(stopLossPrice);
    model.setStopLossLimitPrice(stopLossLimitPrice);
    model.setStopLossTif(stopLossTif);
  }

  public static void addStopLossTrailOrder(TradeOrderRequest tradeOrderRequest,
      Double stopLossTrailingPercent, Double stopLossTrailingAmount, TimeInForce stopLossTif) {
    TradeOrderModel model = (TradeOrderModel) tradeOrderRequest.getApiModel();
    model.setAttachType(AttachType.LOSS);
    model.setStopLossOrderType(OrderType.TRAIL);
    model.setStopLossTrailingPercent(stopLossTrailingPercent);
    model.setStopLossTrailingAmount(stopLossTrailingAmount);
    model.setStopLossTif(stopLossTif);
  }

  public static void addBracketsOrder(TradeOrderRequest tradeOrderRequest,
      Double profitTakerPrice, TimeInForce profitTakerTif, Boolean profitTakerRth,
      Double stopLossPrice, TimeInForce stopLossTif) {
    addBracketsOrder(tradeOrderRequest, profitTakerPrice, profitTakerTif, profitTakerRth,
        stopLossPrice, null, stopLossTif);
  }

  public static void addBracketsOrder(TradeOrderRequest tradeOrderRequest,
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

  public TradeOrderRequest setOrderId(Integer orderId) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setOrderId(orderId);
    return this;
  }

  public TradeOrderRequest setAccount(String account) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setAccount(account);
    return this;
  }

  public TradeOrderRequest setSecretKey(String secretKey) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setSecretKey(secretKey);
    return this;
  }

  public TradeOrderRequest setSymbol(String symbol) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setSymbol(symbol);
    return this;
  }

  public TradeOrderRequest setSecType(SecType secType) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setSecType(secType);
    return this;
  }

  public TradeOrderRequest setAction(ActionType action) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setAction(action);
    return this;
  }

  public TradeOrderRequest setCurrency(Currency currency) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setCurrency(currency);
    return this;
  }

  public TradeOrderRequest setTotalQuantity(Integer totalQuantity) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setTotalQuantity(totalQuantity);
    return this;
  }

  public TradeOrderRequest setOrderType(OrderType orderType) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setOrderType(orderType);
    return this;
  }

  public TradeOrderRequest setLimitPrice(Double limitPrice) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setLimitPrice(limitPrice);
    return this;
  }

  public TradeOrderRequest setAdjustLimit(Double adjustLimit) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setAdjustLimit(adjustLimit);
    return this;
  }

  public TradeOrderRequest setAuxPrice(Double auxPrice) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setAuxPrice(auxPrice);
    return this;
  }

  public TradeOrderRequest setTrailingPercent(Double trailingPercent) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setTrailingPercent(trailingPercent);
    return this;
  }

  public TradeOrderRequest setOutsideRth(Boolean outsideRth) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setOutsideRth(outsideRth);
    return this;
  }

  public TradeOrderRequest setMarket(String market) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setMarket(market);
    return this;
  }

  public TradeOrderRequest setExchange(String exchange) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setExchange(exchange);
    return this;
  }

  public TradeOrderRequest setExpiry(String expiry) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setExpiry(expiry);
    return this;
  }

  public TradeOrderRequest setStrike(String strike) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setStrike(strike);
    return this;
  }

  public TradeOrderRequest setRight(String right) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setRight(right);
    return this;
  }

  public TradeOrderRequest setMultiplier(Float multiplier) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setMultiplier(multiplier);
    return this;
  }

  public TradeOrderRequest setLocalSymbol(String localSymbol) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setLocalSymbol(localSymbol);
    return this;
  }

  public TradeOrderRequest setAllocAccounts(List<String> allocAccounts) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setAllocAccounts(allocAccounts);
    return this;
  }

  public TradeOrderRequest setAllocShares(List<Double> allocShares) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setAllocShares(allocShares);
    return this;
  }

  public TradeOrderRequest setAlgoStrategy(String algoStrategy) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setAlgoStrategy(algoStrategy);
    return this;
  }

  public TradeOrderRequest setAlgoParams(List<TagValue> algoParams) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setAlgoParams(algoParams);
    return this;
  }

  public TradeOrderRequest setAttachType(AttachType attachType) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setAttachType(attachType);
    return this;
  }

  public TradeOrderRequest setProfitTakerOrderId(Integer profitTakerOrderId) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setProfitTakerOrderId(profitTakerOrderId);
    return this;
  }

  public TradeOrderRequest setProfitTakerPrice(Double profitTakerPrice) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setProfitTakerPrice(profitTakerPrice);
    return this;
  }

  public TradeOrderRequest setProfitTakerTif(TimeInForce profitTakerTif) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setProfitTakerTif(profitTakerTif);
    return this;
  }

  public TradeOrderRequest setProfitTakerRth(Boolean profitTakerRth) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setProfitTakerRth(profitTakerRth);
    return this;
  }

  public TradeOrderRequest setStopLossOrderType(OrderType stopLossOrderType) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setStopLossOrderType(stopLossOrderType);
    return this;
  }

  public TradeOrderRequest setStopLossOrderId(Integer stopLossOrderId) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setStopLossOrderId(stopLossOrderId);
    return this;
  }

  public TradeOrderRequest setStopLossPrice(Double stopLossPrice) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setStopLossPrice(stopLossPrice);
    return this;
  }

  public TradeOrderRequest setStopLossLimitPrice(Double stopLossLimitPrice) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setStopLossLimitPrice(stopLossLimitPrice);
    return this;
  }

  public TradeOrderRequest setStopLossTif(TimeInForce stopLossTif) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setStopLossTif(stopLossTif);
    return this;
  }

  public TradeOrderRequest setStopLossTrailingPercent(Double stopLossTrailingPercent) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setStopLossTrailingPercent(stopLossTrailingPercent);
    return this;
  }

  public TradeOrderRequest setStopLossTrailingAmount(Double stopLossTrailingAmount) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setStopLossTrailingAmount(stopLossTrailingAmount);
    return this;
  }

  public TradeOrderRequest withUserMark(String userMark) {
    return setUserMark(userMark);
  }
  public TradeOrderRequest setUserMark(String userMark) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setUserMark(userMark);
    return this;
  }

  public TradeOrderRequest setTimeInForce(TimeInForce timeInForce) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setTimeInForce(timeInForce);
    return this;
  }

  public TradeOrderRequest setExpireTime(Long expireTime) {
    TradeOrderModel model = (TradeOrderModel) getApiModel();
    model.setExpireTime(expireTime);
    return this;
  }

  public TradeOrderRequest setLang(Language lang) {
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
  public TradeOrderRequest setAuctionOrder(OrderType orderType, TimeInForce timeInForce) {
    if (orderType == OrderType.AM || orderType == OrderType.AL) {
      TradeOrderModel model = (TradeOrderModel) getApiModel();
      model.setOrderType(orderType);
      model.setTimeInForce(timeInForce == TimeInForce.OPG ? timeInForce : TimeInForce.DAY);
    }
    return this;
  }

  @Override
  public Class<TradeOrderResponse> getResponseClass() {
    return TradeOrderResponse.class;
  }
}
