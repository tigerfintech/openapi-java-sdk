package com.tigerbrokers.stock.openapi.client.struct.param;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.struct.TagValue;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderType;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeInForce;
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
  private Integer totalQuantity;
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

  public Integer getTotalQuantity() {
    return totalQuantity;
  }

  public void setTotalQuantity(Integer totalQuantity) {
    this.totalQuantity = totalQuantity;
  }

  public TimeInForce getTimeInForce() {
    return timeInForce;
  }

  public void setTimeInForce(TimeInForce timeInForce) {
    this.timeInForce = timeInForce;
  }

  public Double getLimitPrice() {
    return limitPrice;
  }

  public void setLimitPrice(Double limitPrice) {
    this.limitPrice = limitPrice;
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

  @Override
  public String toString() {
    return "OrderParameter{" +
        "orderId=" + orderId +
        ", account='" + account + '\'' +
        ", symbol='" + symbol + '\'' +
        ", secType=" + secType +
        ", action=" + action +
        ", currency=" + currency +
        ", totalQuantity=" + totalQuantity +
        ", timeInForce=" + timeInForce +
        ", orderType=" + orderType +
        ", limitPrice=" + limitPrice +
        ", auxPrice=" + auxPrice +
        ", trailingPercent=" + trailingPercent +
        ", outsideRth=" + outsideRth +
        ", market='" + market + '\'' +
        ", exchange='" + exchange + '\'' +
        ", expiry='" + expiry + '\'' +
        ", strike='" + strike + '\'' +
        ", right='" + right + '\'' +
        ", multiplier=" + multiplier +
        ", allocAccounts=" + allocAccounts +
        ", allocShares=" + allocShares +
        ", algoStrategy='" + algoStrategy + '\'' +
        ", algoParams=" + algoParams +
        ", source='" + source + '\'' +
        '}';
  }
}
