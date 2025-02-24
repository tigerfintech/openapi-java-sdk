package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

import com.tigerbrokers.stock.openapi.client.struct.TagValue;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderStatus;
import java.io.Serializable;
import java.util.List;

/**
 * @author bean
 * @date 2023/3/3 2:03 PM
 */
public class TradeOrder implements Serializable {
  private static final long serialVersionUID = 1L;

  private String symbol;
  private String market;
  private String secType;
  private String currency;
  private String expiry;
  private String strike;
  private String right;
  private Float multiplier;
  private String identifier;

  private Long id;
  private Integer orderId;
  private String externalId;
  private Long parentId;
  private String account;
  private String action;
  private String orderType;
  private Double limitPrice;
  private Double auxPrice;
  private Double trailingPercent;

  private Long totalQuantity;
  private Integer totalQuantityScale;
  private Long filledQuantity;
  private Integer filledQuantityScale;
  private Double cashQuantity;
  private Double totalCashAmount;
  private Double filledCashAmount;
  private Double refundCashAmount;
  private Double lastFillPrice;
  private Double avgFillPrice;
  private String timeInForce;
  private Long expireTime;
  private String goodTillDate;
  private Boolean outsideRth;
  private String tradingSessionType;

  private Double commission;
  /** Goods and Services Tax (TBSG only) */
  private Double gst;
  private Double realizedPnl;
  private String remark;
  private String triggerStatus;
  /** charge details */
  private List<Charge> charges;
  /** commission discount amount. only for single order query */
  private Double commissionDiscountAmount;
  private Integer orderDiscount;
  /** order discount amount. only for single order query */
  private Double orderDiscountAmount;

  private Boolean liquidation;
  private Long openTime;
  private Long updateTime;
  private Long latestTime;
  private String name;
  private Double latestPrice;

  private String attrDesc;
  private String userMark;
  private List<String> attrList;

  private Integer ocaGroupId;
  private String comboLegs;
  private List<String> allocAccounts;
  private List<Double> allocShares;

  private String algoStrategy;
  private List<TagValue> algoParameters;

  private OrderStatus status;

  private String source;
  private Double discount;

  private Boolean canModify;
  private Boolean canCancel;
  /** order replace status(NONE, RECEIVED, REPLACED, FAILED) */
  private String replaceStatus;
  /** order cancel status(NONE, RECEIVED, FAILED) */
  private String cancelStatus;
  private Boolean isOpen;

  private String comboType;
  private String comboTypeDesc;
  /** order's multi leg info */
  private List<OrderLeg> legs;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public String getSecType() {
    return secType;
  }

  public void setSecType(String secType) {
    this.secType = secType;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
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

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
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

  public String getExternalId() {
    return externalId;
  }

  public void setExternalId(String externalId) {
    this.externalId = externalId;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getOrderType() {
    return orderType;
  }

  public void setOrderType(String orderType) {
    this.orderType = orderType;
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

  public Long getFilledQuantity() {
    return filledQuantity;
  }

  public void setFilledQuantity(Long filledQuantity) {
    this.filledQuantity = filledQuantity;
  }

  public Double getCashQuantity() {
    return cashQuantity;
  }

  public void setCashQuantity(Double cashQuantity) {
    this.cashQuantity = cashQuantity;
  }

  public Double getLastFillPrice() {
    return lastFillPrice;
  }

  public void setLastFillPrice(Double lastFillPrice) {
    this.lastFillPrice = lastFillPrice;
  }

  public Double getAvgFillPrice() {
    return avgFillPrice;
  }

  public void setAvgFillPrice(Double avgFillPrice) {
    this.avgFillPrice = avgFillPrice;
  }

  public String getTimeInForce() {
    return timeInForce;
  }

  public void setTimeInForce(String timeInForce) {
    this.timeInForce = timeInForce;
  }

  public Long getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(Long expireTime) {
    this.expireTime = expireTime;
  }

  public String getGoodTillDate() {
    return goodTillDate;
  }

  public void setGoodTillDate(String goodTillDate) {
    this.goodTillDate = goodTillDate;
  }

  public Boolean getOutsideRth() {
    return outsideRth;
  }

  public void setOutsideRth(Boolean outsideRth) {
    this.outsideRth = outsideRth;
  }

  public String getTradingSessionType() {
    return tradingSessionType;
  }

  public void setTradingSessionType(String tradingSessionType) {
    this.tradingSessionType = tradingSessionType;
  }

  public Double getCommission() {
    return commission;
  }

  public void setCommission(Double commission) {
    this.commission = commission;
  }

  public Double getGst() {
    return gst;
  }

  public void setGst(Double gst) {
    this.gst = gst;
  }

  public Double getRealizedPnl() {
    return realizedPnl;
  }

  public void setRealizedPnl(Double realizedPnl) {
    this.realizedPnl = realizedPnl;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Boolean getLiquidation() {
    return liquidation;
  }

  public void setLiquidation(Boolean liquidation) {
    this.liquidation = liquidation;
  }

  public Long getOpenTime() {
    return openTime;
  }

  public void setOpenTime(Long openTime) {
    this.openTime = openTime;
  }

  public Long getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Long updateTime) {
    this.updateTime = updateTime;
  }

  public Long getLatestTime() {
    return latestTime;
  }

  public void setLatestTime(Long latestTime) {
    this.latestTime = latestTime;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getLatestPrice() {
    return latestPrice;
  }

  public void setLatestPrice(Double latestPrice) {
    this.latestPrice = latestPrice;
  }

  public String getAttrDesc() {
    return attrDesc;
  }

  public void setAttrDesc(String attrDesc) {
    this.attrDesc = attrDesc;
  }

  public String getUserMark() {
    return userMark;
  }

  public void setUserMark(String userMark) {
    this.userMark = userMark;
  }

  public Integer getOcaGroupId() {
    return ocaGroupId;
  }

  public void setOcaGroupId(Integer ocaGroupId) {
    this.ocaGroupId = ocaGroupId;
  }

  public String getComboLegs() {
    return comboLegs;
  }

  public void setComboLegs(String comboLegs) {
    this.comboLegs = comboLegs;
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

  public List<TagValue> getAlgoParameters() {
    return algoParameters;
  }

  public void setAlgoParameters(List<TagValue> algoParameters) {
    this.algoParameters = algoParameters;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public Double getDiscount() {
    return discount;
  }

  public void setDiscount(Double discount) {
    this.discount = discount;
  }

  public Boolean getCanModify() {
    return canModify;
  }

  public void setCanModify(Boolean canModify) {
    this.canModify = canModify;
  }

  public Boolean getCanCancel() {
    return canCancel;
  }

  public void setCanCancel(Boolean canCancel) {
    this.canCancel = canCancel;
  }

  public String getReplaceStatus() {
    return replaceStatus;
  }

  public void setReplaceStatus(String replaceStatus) {
    this.replaceStatus = replaceStatus;
  }

  public String getCancelStatus() {
    return cancelStatus;
  }

  public void setCancelStatus(String cancelStatus) {
    this.cancelStatus = cancelStatus;
  }

  public Boolean getIsOpen() {
    return isOpen;
  }

  public void setIsOpen(Boolean isOpen) {
    this.isOpen = isOpen;
  }

  public String getComboType() {
    return comboType;
  }

  public void setComboType(String comboType) {
    this.comboType = comboType;
  }

  public String getComboTypeDesc() {
    return comboTypeDesc;
  }

  public void setComboTypeDesc(String comboTypeDesc) {
    this.comboTypeDesc = comboTypeDesc;
  }

  public List<OrderLeg> getLegs() {
    return legs;
  }

  public void setLegs(List<OrderLeg> legs) {
    this.legs = legs;
  }

  public Integer getFilledQuantityScale() {
    return filledQuantityScale;
  }

  public void setFilledQuantityScale(Integer filledQuantityScale) {
    this.filledQuantityScale = filledQuantityScale;
  }

  public Double getTotalCashAmount() {
    return totalCashAmount;
  }

  public void setTotalCashAmount(Double totalCashAmount) {
    this.totalCashAmount = totalCashAmount;
  }

  public Double getFilledCashAmount() {
    return filledCashAmount;
  }

  public void setFilledCashAmount(Double filledCashAmount) {
    this.filledCashAmount = filledCashAmount;
  }

  public Double getRefundCashAmount() {
    return refundCashAmount;
  }

  public void setRefundCashAmount(Double refundCashAmount) {
    this.refundCashAmount = refundCashAmount;
  }

  public String getTriggerStatus() {
    return triggerStatus;
  }

  public void setTriggerStatus(String triggerStatus) {
    this.triggerStatus = triggerStatus;
  }

  public List<String> getAttrList() {
    return attrList;
  }

  public void setAttrList(List<String> attrList) {
    this.attrList = attrList;
  }

  public List<Charge> getCharges() {
    return charges;
  }

  public void setCharges(List<Charge> charges) {
    this.charges = charges;
  }

  public Double getCommissionDiscountAmount() {
    return commissionDiscountAmount;
  }

  public void setCommissionDiscountAmount(Double commissionDiscountAmount) {
    this.commissionDiscountAmount = commissionDiscountAmount;
  }

  public Integer getOrderDiscount() {
    return orderDiscount;
  }

  public void setOrderDiscount(Integer orderDiscount) {
    this.orderDiscount = orderDiscount;
  }

  public Double getOrderDiscountAmount() {
    return orderDiscountAmount;
  }

  public void setOrderDiscountAmount(Double orderDiscountAmount) {
    this.orderDiscountAmount = orderDiscountAmount;
  }
}
