package com.tigerbrokers.stock.openapi.client.struct.param;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.struct.TagValue;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.AttachType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderType;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeInForce;
import com.tigerbrokers.stock.openapi.client.struct.enums.TradeSession;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/05/16.
 */

public class OrderParameter implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 全局唯一的订单ID
   */
  private Long id;

  /**
   * 订单ID(用户账户内唯一)
   */
  @JSONField(name = "order_id")
  private Integer orderId;

  /**
   * 交易账户
   */
  private String account;

  @JSONField(name = "secret_key")
  private String secretKey;
  /**
   * 底层股票代码
   */
  private String symbol;
  /**
   * 合约类型 (STK 股票 OPT 美股期权 WAR 港股涡轮 IOPT 港股牛熊证)
   */
  @JSONField(name = "sec_type")
  private SecType secType;
  /**
   * 交易方向 BUY/SELL
   */
  private ActionType action;
  /**
   * 货币
   */
  private Currency currency;
  /**
   * 订单数量
   */
  @JSONField(name = "total_quantity")
  private Long totalQuantity;
  /**
   * 下单数量的偏移量
   */
  @JSONField(name = "total_quantity_scale")
  private Integer totalQuantityScale;
  /**
   * order cash amount
   */
  @JSONField(name = "cash_amount")
  private Double cashAmount;
  /**
   * 有效时间
   * DAY：当日有效
   * GTC：取消前有效
   * GTD：指定日期前有效
   * AUC：集合竞价（ 港股，订单类型为 MTL，需要填 limitPrice）
   */
  @JSONField(name = "time_in_force")
  private TimeInForce timeInForce;

  /**
   * GTD order's expire time
   */
  @JSONField(name = "expire_time")
  private Long expireTime;

  /**
   * 订单类型
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
   * 股票止损价。当 order_type 为STP,STP_LMT时该参数必需，当 order_type 为 TRAIL时,aux_price为跟踪额
   */
  @JSONField(name = "aux_price")
  private Double auxPrice;

  /**
   * 跟踪止损单 - 百分比。当 order_type 为 TRAIL时,aux_price和trailing_percent两者互斥
   */
  @JSONField(name = "trailing_percent")
  private Double trailingPercent;

  /**
   * true 允许盘前盘后交易(美股专属)
   */
  @JSONField(name = "outside_rth")
  private Boolean outsideRth;
  /**
   * set place overnight order in the US market. value: OverNight
   */
  @JSONField(name = "trading_session_type")
  private TradeSession tradingSessionType;
  /**
   * 市场
   */
  private String market;
  /**
   * 交易所
   */
  private String exchange;
  /**
   * 期权、涡轮、牛熊证字段
   */
  private String expiry;
  /**
   * 底层价格(期权、涡轮、牛熊证专属)
   */
  private String strike;
  /**
   * 期权方向 PUT/CALL(期权、涡轮、牛熊证专属)
   */
  private String right;
  /**
   * 1手单位(期权、涡轮、牛熊证专属)
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
  @JSONField(name = "user_mark")
  private String userMark;

  /**
   * 附加订单类型：
   * PROFIT
   * LOSS
   */
  @JSONField(name = "attach_type")
  private AttachType attachType;

  /**
   * 止盈订单
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
   * 止损订单
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

  @JSONField(name = "oca_orders")
  private List<OrderParameter> ocaOrders;

  private String lang;

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

  public Double getCashAmount() {
    return cashAmount;
  }

  public void setCashAmount(Double cashAmount) {
    this.cashAmount = cashAmount;
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

  public Boolean isOutsideRth() {
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

  public OrderType getOrderType() {
    return orderType;
  }

  public void setOrderType(OrderType orderType) {
    this.orderType = orderType;
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

  public void setAttachType(AttachType attachType) {
    this.attachType = attachType;
  }

  public void setProfitTakerOrderId(Integer profitTakerOrderId) {
    this.profitTakerOrderId = profitTakerOrderId;
  }

  public void setProfitTakerPrice(Double profitTakerPrice) {
    this.profitTakerPrice = profitTakerPrice;
  }

  public void setProfitTakerTif(TimeInForce profitTakerTif) {
    this.profitTakerTif = profitTakerTif;
  }

  public void setProfitTakerRth(Boolean profitTakerRth) {
    this.profitTakerRth = profitTakerRth;
  }

  public void setStopLossOrderId(Integer stopLossOrderId) {
    this.stopLossOrderId = stopLossOrderId;
  }

  public void setStopLossPrice(Double stopLossPrice) {
    this.stopLossPrice = stopLossPrice;
  }

  public void setStopLossTif(TimeInForce stopLossTif) {
    this.stopLossTif = stopLossTif;
  }

  public AttachType getAttachType() {
    return attachType;
  }

  public Integer getProfitTakerOrderId() {
    return profitTakerOrderId;
  }

  public Double getProfitTakerPrice() {
    return profitTakerPrice;
  }

  public TimeInForce getProfitTakerTif() {
    return profitTakerTif;
  }

  public Boolean isProfitTakerRth() {
    return profitTakerRth;
  }

  public Integer getStopLossOrderId() {
    return stopLossOrderId;
  }

  public Double getStopLossPrice() {
    return stopLossPrice;
  }

  public TimeInForce getStopLossTif() {
    return stopLossTif;
  }

  public OrderType getStopLossOrderType() {
    return stopLossOrderType;
  }

  public void setStopLossOrderType(OrderType stopLossOrderType) {
    this.stopLossOrderType = stopLossOrderType;
  }

  public Double getStopLossLimitPrice() {
    return stopLossLimitPrice;
  }

  public void setStopLossLimitPrice(Double stopLossLimitPrice) {
    this.stopLossLimitPrice = stopLossLimitPrice;
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

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public List<OrderParameter> getOcaOrders() {
    return ocaOrders;
  }

  public void setOcaOrders(List<OrderParameter> ocaOrders) {
    this.ocaOrders = ocaOrders;
  }

  public String getLang() {
    return lang;
  }

  public void setLang(String lang) {
    this.lang = lang;
  }

  @Override
  public String toString() {
    return "OrderParameter{" +
        "id=" + id +
        ", orderId=" + orderId +
        ", account='" + account + '\'' +
        ", secretKey=" + secretKey +
        ", symbol='" + symbol + '\'' +
        ", secType=" + secType +
        ", action=" + action +
        ", currency=" + currency +
        ", totalQuantity=" + totalQuantity +
        ", totalQuantityScale=" + totalQuantityScale +
        ", cashAmount=" + cashAmount +
        ", timeInForce=" + timeInForce +
        ", expireTime=" + expireTime +
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
        ", userMark=" + userMark +
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
        '}';
  }
}
