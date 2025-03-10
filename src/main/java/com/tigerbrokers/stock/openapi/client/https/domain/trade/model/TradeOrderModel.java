package com.tigerbrokers.stock.openapi.client.https.domain.trade.model;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.ContractLeg;
import com.tigerbrokers.stock.openapi.client.struct.TagValue;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.AttachType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderType;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeInForce;
import com.tigerbrokers.stock.openapi.client.struct.enums.TradeSession;

import java.util.ArrayList;
import java.util.List;

public class TradeOrderModel extends ApiModel {

  /**
   * order ID（generated by the server, unique）
   */
  private Long id;

  /**
   * order ID(the incremental value of the corresponding account)
   */
  @JSONField(name = "order_id")
  private Integer orderId;

  /**
   * trading account id
   */
  private String account;

  @JSONField(name = "secret_key")
  private String secretKey;
  /**
   * symbol
   */
  private String symbol;
  /**
   * contract type (STK OPT WAR IOPT FUT)
   */
  @JSONField(name = "sec_type")
  private SecType secType;
  /**
   * trading direction BUY/SELL
   */
  private ActionType action;
  /**
   * currency
   */
  private Currency currency;
  /**
   * total quantity
   */
  @JSONField(name = "total_quantity")
  private Long totalQuantity;
  /**
   * total quantity scale
   */
  @JSONField(name = "total_quantity_scale")
  private Integer totalQuantityScale;
  /**
   * order validity time range
   * DAY：valid for the day
   * GTC：valid until cancelled
   * GTD：valid until the specified date
   * AUC：call auction（ HK stock，order type is 'MTL'，required limitPrice）
   */
  @JSONField(name = "time_in_force")
  private TimeInForce timeInForce;
  /**
   * GTD order's expire time
   */
  @JSONField(name = "expire_time")
  private Long expireTime;

  /**
   * order type
   */
  @JSONField(name = "order_type")
  private OrderType orderType;
  /**
   * 价格元素：
   * 无 : 市价单
   * limitPrice : 限价单价格
   * auxPrice : 止损单价格
   * limitPrice，auxPrice : 止损限价单
   * auxPrice : 跟踪止损单
   */
  @JSONField(name = "limit_price")
  private Double limitPrice;
  /**
   * 价格微调幅度（默认为0表示不调整，正数为向上调整，负数向下调整），对传入价格自动调整到合法价位上
   * 例如：0.001 代表向上调整且幅度不超过 0.1%；-0.001 代表向下调整且幅度不超过 0.1%。默认 0 表示不调整
   */
  @JSONField(name = "adjust_limit")
  private Double adjustLimit;
  /**
   * stop loss price. This parameter is required when order_type is STP and STP_LMT,
   * when order_type is TRAIL, aux_price is the stop loss trailing amount
   */
  @JSONField(name = "aux_price")
  private Double auxPrice;

  /**
   * Trailing Stop Order - trailing percentage. When order_type is TRAIL,
   * trailing_percent is preferred when both aux_price and trailing_percent have values
   */
  @JSONField(name = "trailing_percent")
  private Double trailingPercent;

  /**
   * Allow pre-market and after-hours trading. default is true
   */
  @JSONField(name = "outside_rth")
  private Boolean outsideRth = Boolean.TRUE;
  /**
   * set place overnight order in the US market. value: OverNight
   */
  @JSONField(name = "trading_session_type")
  private TradeSession tradingSessionType;
  /**
   * market
   */
  private String market;
  /**
   * exchange
   */
  private String exchange;
  /**
   * expiry(for options, warran, cbbc)
   */
  private String expiry;
  /**
   * strike price (for options, warran, cbbc)
   */
  private String strike;
  /**
   * PUT/CALL (for options, warran, cbbc)
   */
  private String right;
  /**
   * lot size(for futures, options, warran, cbbc)
   */
  private Float multiplier;

  @JSONField(name = "local_symbol")
  private String localSymbol;

  @JSONField(name = "alloc_accounts")
  private List<String> allocAccounts;
  @JSONField(name = "alloc_shares")
  private List<Double> allocShares;

  @JSONField(name = "algo_strategy")
  private String algoStrategy;
  @JSONField(name = "algo_params")
  private List<TagValue> algoParams;

  private String source;
  /**
   * user remark info
   */
  @JSONField(name = "user_mark")
  private String userMark;

  /**
   * attached order type：PROFIT/LOSS/BRACKETS
   */
  @JSONField(name = "attach_type")
  private AttachType attachType;

  /**
   * attached profit taker order
   */
  @JSONField(name = "profit_taker_orderId")
  private Integer profitTakerOrderId;
  @JSONField(name = "profit_taker_price")
  private Double profitTakerPrice;
  @JSONField(name = "profit_taker_tif")
  private TimeInForce profitTakerTif;
  @JSONField(name = "profit_taker_rth")
  private Boolean profitTakerRth;

  /**
   * attached stop loss order
   */
  @JSONField(name = "stop_loss_order_type")
  private OrderType stopLossOrderType;
  @JSONField(name = "stop_loss_orderId")
  private Integer stopLossOrderId;
  @JSONField(name = "stop_loss_price")
  private Double stopLossPrice;
  @JSONField(name = "stop_loss_limit_price")
  private Double stopLossLimitPrice;
  @JSONField(name = "stop_loss_tif")
  private TimeInForce stopLossTif;
  /**
   * attached trailing stop loss order's trailing percent
   */
  @JSONField(name = "stop_loss_trailing_percent")
  private Double stopLossTrailingPercent;
  /**
   * attached trailing stop loss order's trailing amount
   */
  @JSONField(name = "stop_loss_trailing_amount")
  private Double stopLossTrailingAmount;

  /**
   * Multi Order's type: COVERED,PROTECTIVE,VERTICAL,STRADDLE,STRANGLE,CALENDAR,DIAGONAL,SYNTHETIC,CUSTOM
   */
  @JSONField(name = "combo_type")
  private String comboType;

  @JSONField(name = "contract_legs")
  private List<ContractLeg> contractLegs;

  @JSONField(name = "oca_orders")
  private List<TradeOrderModel> ocaOrders;

  /** Order by amount (such as general fund subscription) */
  @JSONField(name = "cash_amount")
  private Double cashAmount;

  public TradeOrderModel() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getOrderId() {
    return orderId;
  }

  public void setOrderId(Integer orderId) {
    this.orderId = orderId;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public SecType getSecType() {
    return secType;
  }

  public void setSecType(SecType secType) {
    this.secType = secType;
  }

  public ActionType getAction() {
    return action;
  }

  public void setAction(ActionType action) {
    this.action = action;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public Long getTotalQuantity() {
    return totalQuantity;
  }

  public void setTotalQuantity(Long totalQuantity) {
    this.totalQuantity = totalQuantity;
  }

  public Integer getTotalQuantityScale() {
    return totalQuantityScale;
  }

  public void setTotalQuantityScale(Integer totalQuantityScale) {
    this.totalQuantityScale = totalQuantityScale;
  }

  public TimeInForce getTimeInForce() {
    return timeInForce;
  }

  public void setTimeInForce(TimeInForce timeInForce) {
    this.timeInForce = timeInForce;
  }

  public Long getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(Long expireTime) {
    this.expireTime = expireTime;
  }

  public OrderType getOrderType() {
    return orderType;
  }

  public void setOrderType(OrderType orderType) {
    this.orderType = orderType;
  }

  public Double getLimitPrice() {
    return limitPrice;
  }

  public void setLimitPrice(Double limitPrice) {
    this.limitPrice = limitPrice;
  }

  public Double getAdjustLimit() {
    return adjustLimit;
  }

  public void setAdjustLimit(Double adjustLimit) {
    this.adjustLimit = adjustLimit;
  }

  public Double getAuxPrice() {
    return auxPrice;
  }

  public void setAuxPrice(Double auxPrice) {
    this.auxPrice = auxPrice;
  }

  public Double getTrailingPercent() {
    return trailingPercent;
  }

  public void setTrailingPercent(Double trailingPercent) {
    this.trailingPercent = trailingPercent;
  }

  public Boolean getOutsideRth() {
    return outsideRth;
  }

  public void setOutsideRth(Boolean outsideRth) {
    this.outsideRth = outsideRth;
  }

  public TradeSession getTradingSessionType() {
    return tradingSessionType;
  }

  public void setTradingSessionType(TradeSession tradingSessionType) {
    this.tradingSessionType = tradingSessionType;
  }

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public String getExchange() {
    return exchange;
  }

  public void setExchange(String exchange) {
    this.exchange = exchange;
  }

  public String getExpiry() {
    return expiry;
  }

  public void setExpiry(String expiry) {
    this.expiry = expiry;
  }

  public String getStrike() {
    return strike;
  }

  public void setStrike(String strike) {
    this.strike = strike;
  }

  public String getRight() {
    return right;
  }

  public void setRight(String right) {
    this.right = right;
  }

  public Float getMultiplier() {
    return multiplier;
  }

  public void setMultiplier(Float multiplier) {
    this.multiplier = multiplier;
  }

  public String getLocalSymbol() {
    return localSymbol;
  }

  public void setLocalSymbol(String localSymbol) {
    this.localSymbol = localSymbol;
  }

  public List<String> getAllocAccounts() {
    return allocAccounts;
  }

  public void setAllocAccounts(List<String> allocAccounts) {
    this.allocAccounts = allocAccounts;
  }

  public List<Double> getAllocShares() {
    return allocShares;
  }

  public void setAllocShares(List<Double> allocShares) {
    this.allocShares = allocShares;
  }

  public String getAlgoStrategy() {
    return algoStrategy;
  }

  public void setAlgoStrategy(String algoStrategy) {
    this.algoStrategy = algoStrategy;
  }

  public List<TagValue> getAlgoParams() {
    return algoParams;
  }

  public void setAlgoParams(List<TagValue> algoParams) {
    this.algoParams = algoParams;
  }

  public void addAlgoParam(TagValue algoParam) {
    if (algoParam == null) {
      return;
    }
    if (this.algoParams == null) {
      this.algoParams = new ArrayList<>();
    }
    this.algoParams.add(algoParam);
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getUserMark() {
    return userMark;
  }

  public void setUserMark(String userMark) {
    this.userMark = userMark;
  }

  public AttachType getAttachType() {
    return attachType;
  }

  public void setAttachType(AttachType attachType) {
    this.attachType = attachType;
  }

  public Integer getProfitTakerOrderId() {
    return profitTakerOrderId;
  }

  public void setProfitTakerOrderId(Integer profitTakerOrderId) {
    this.profitTakerOrderId = profitTakerOrderId;
  }

  public Double getProfitTakerPrice() {
    return profitTakerPrice;
  }

  public void setProfitTakerPrice(Double profitTakerPrice) {
    this.profitTakerPrice = profitTakerPrice;
  }

  public TimeInForce getProfitTakerTif() {
    return profitTakerTif;
  }

  public void setProfitTakerTif(TimeInForce profitTakerTif) {
    this.profitTakerTif = profitTakerTif;
  }

  public Boolean getProfitTakerRth() {
    return profitTakerRth;
  }

  public void setProfitTakerRth(Boolean profitTakerRth) {
    this.profitTakerRth = profitTakerRth;
  }

  public OrderType getStopLossOrderType() {
    return stopLossOrderType;
  }

  public void setStopLossOrderType(OrderType stopLossOrderType) {
    this.stopLossOrderType = stopLossOrderType;
  }

  public Integer getStopLossOrderId() {
    return stopLossOrderId;
  }

  public void setStopLossOrderId(Integer stopLossOrderId) {
    this.stopLossOrderId = stopLossOrderId;
  }

  public Double getStopLossPrice() {
    return stopLossPrice;
  }

  public void setStopLossPrice(Double stopLossPrice) {
    this.stopLossPrice = stopLossPrice;
  }

  public Double getStopLossLimitPrice() {
    return stopLossLimitPrice;
  }

  public void setStopLossLimitPrice(Double stopLossLimitPrice) {
    this.stopLossLimitPrice = stopLossLimitPrice;
  }

  public TimeInForce getStopLossTif() {
    return stopLossTif;
  }

  public void setStopLossTif(TimeInForce stopLossTif) {
    this.stopLossTif = stopLossTif;
  }

  public Double getStopLossTrailingPercent() {
    return stopLossTrailingPercent;
  }

  public void setStopLossTrailingPercent(Double stopLossTrailingPercent) {
    this.stopLossTrailingPercent = stopLossTrailingPercent;
  }

  public Double getStopLossTrailingAmount() {
    return stopLossTrailingAmount;
  }

  public void setStopLossTrailingAmount(Double stopLossTrailingAmount) {
    this.stopLossTrailingAmount = stopLossTrailingAmount;
  }

  public String getComboType() {
    return comboType;
  }

  public void setComboType(String comboType) {
    this.comboType = comboType;
  }

  public List<ContractLeg> getContractLegs() {
    return contractLegs;
  }

  public void setContractLegs(
      List<ContractLeg> contractLegs) {
    this.contractLegs = contractLegs;
  }

  public List<TradeOrderModel> getOcaOrders() {
    return ocaOrders;
  }

  public void setOcaOrders(List<TradeOrderModel> ocaOrders) {
    this.ocaOrders = ocaOrders;
  }

  public Double getCashAmount() {
    return cashAmount;
  }

  public void setCashAmount(Double cashAmount) {
    this.cashAmount = cashAmount;
  }

  @Override
  public String toString() {
    return "TradeOrderModel{" +
        "id=" + id +
        ", orderId=" + orderId +
        ", account='" + account + '\'' +
        ", secretKey='" + secretKey + '\'' +
        ", symbol='" + symbol + '\'' +
        ", secType=" + secType +
        ", action=" + action +
        ", currency=" + currency +
        ", totalQuantity=" + totalQuantity +
        ", totalQuantityScale=" + totalQuantityScale +
        ", timeInForce=" + timeInForce +
        ", orderType=" + orderType +
        ", limitPrice=" + limitPrice +
        ", auxPrice=" + auxPrice +
        ", adjustLimit=" + adjustLimit +
        ", trailingPercent=" + trailingPercent +
        ", outsideRth=" + outsideRth +
        ", tradingSessionType=" + tradingSessionType +
        ", market='" + market + '\'' +
        ", exchange='" + exchange + '\'' +
        ", expiry='" + expiry + '\'' +
        ", strike='" + strike + '\'' +
        ", right='" + right + '\'' +
        ", multiplier=" + multiplier +
        ", localSymbol='" + localSymbol + '\'' +
        ", allocAccounts=" + allocAccounts +
        ", allocShares=" + allocShares +
        ", algoStrategy='" + algoStrategy + '\'' +
        ", algoParams=" + algoParams +
        ", source='" + source + '\'' +
        ", attachType=" + attachType +
        ", profitTakerOrderId=" + profitTakerOrderId +
        ", profitTakerPrice=" + profitTakerPrice +
        ", profitTakerTif=" + profitTakerTif +
        ", profitTakerRth=" + profitTakerRth +
        ", stopLossOrderType=" + stopLossOrderType +
        ", stopLossOrderId=" + stopLossOrderId +
        ", stopLossPrice=" + stopLossPrice +
        ", stopLossLimitPrice=" + stopLossLimitPrice +
        ", stopLossTif=" + stopLossTif +
        ", stopLossTrailingPercent=" + stopLossTrailingPercent +
        ", stopLossTrailingAmount=" + stopLossTrailingAmount +
        ", comboType=" + comboType +
        ", cashAmount=" + cashAmount +
        ", ocaOrders=" + ocaOrders == null ? "" : JSONObject.toJSONString(ocaOrders, SerializerFeature.WriteEnumUsingToString) +
        '}';
  }
}
