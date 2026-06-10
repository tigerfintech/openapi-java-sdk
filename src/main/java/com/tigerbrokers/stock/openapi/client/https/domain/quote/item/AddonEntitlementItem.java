package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import java.util.List;

/**
 * 附加套餐权益
 */
public class AddonEntitlementItem {

  private String userLevel;
  private ActivePlan activePlan;
  private List<AddonInfo> addons;
  private Entitlement effectiveEntitlement;

  public String getUserLevel() {
    return userLevel;
  }

  public void setUserLevel(String userLevel) {
    this.userLevel = userLevel;
  }

  public ActivePlan getActivePlan() {
    return activePlan;
  }

  public void setActivePlan(ActivePlan activePlan) {
    this.activePlan = activePlan;
  }

  public List<AddonInfo> getAddons() {
    return addons;
  }

  public void setAddons(List<AddonInfo> addons) {
    this.addons = addons;
  }

  public Entitlement getEffectiveEntitlement() {
    return effectiveEntitlement;
  }

  public void setEffectiveEntitlement(Entitlement effectiveEntitlement) {
    this.effectiveEntitlement = effectiveEntitlement;
  }

  public static class ActivePlan {
    private String planType;
    private Long expireTime;

    public String getPlanType() {
      return planType;
    }

    public void setPlanType(String planType) {
      this.planType = planType;
    }

    public Long getExpireTime() {
      return expireTime;
    }

    public void setExpireTime(Long expireTime) {
      this.expireTime = expireTime;
    }
  }

  public static class AddonInfo {
    private String planType;
    private boolean active;
    private Long startTime;
    private Long expireTime;

    public String getPlanType() {
      return planType;
    }

    public void setPlanType(String planType) {
      this.planType = planType;
    }

    public boolean isActive() {
      return active;
    }

    public void setActive(boolean active) {
      this.active = active;
    }

    public Long getStartTime() {
      return startTime;
    }

    public void setStartTime(Long startTime) {
      this.startTime = startTime;
    }

    public Long getExpireTime() {
      return expireTime;
    }

    public void setExpireTime(Long expireTime) {
      this.expireTime = expireTime;
    }
  }

  public static class Entitlement {
    private Integer historyStockLimit;
    private Integer historyStockRemaining;
    private Integer historyFutureLimit;
    private Integer historyFutureRemaining;
    private Integer historyOptionLimit;
    private Integer historyOptionRemaining;
    private Integer subscribeLimit;
    private Integer subscribeRemaining;
    private Integer subscribeDepthLimit;
    private Integer subscribeDepthRemaining;
    private Integer highFreqLimit;
    private Integer midFreqLimit;
    private Integer lowFreqLimit;
    private Integer rateMultiple;

    public Integer getHistoryStockLimit() { return historyStockLimit; }
    public void setHistoryStockLimit(Integer historyStockLimit) { this.historyStockLimit = historyStockLimit; }
    public Integer getHistoryStockRemaining() { return historyStockRemaining; }
    public void setHistoryStockRemaining(Integer historyStockRemaining) { this.historyStockRemaining = historyStockRemaining; }
    public Integer getHistoryFutureLimit() { return historyFutureLimit; }
    public void setHistoryFutureLimit(Integer historyFutureLimit) { this.historyFutureLimit = historyFutureLimit; }
    public Integer getHistoryFutureRemaining() { return historyFutureRemaining; }
    public void setHistoryFutureRemaining(Integer historyFutureRemaining) { this.historyFutureRemaining = historyFutureRemaining; }
    public Integer getHistoryOptionLimit() { return historyOptionLimit; }
    public void setHistoryOptionLimit(Integer historyOptionLimit) { this.historyOptionLimit = historyOptionLimit; }
    public Integer getHistoryOptionRemaining() { return historyOptionRemaining; }
    public void setHistoryOptionRemaining(Integer historyOptionRemaining) { this.historyOptionRemaining = historyOptionRemaining; }
    public Integer getSubscribeLimit() { return subscribeLimit; }
    public void setSubscribeLimit(Integer subscribeLimit) { this.subscribeLimit = subscribeLimit; }
    public Integer getSubscribeRemaining() { return subscribeRemaining; }
    public void setSubscribeRemaining(Integer subscribeRemaining) { this.subscribeRemaining = subscribeRemaining; }
    public Integer getSubscribeDepthLimit() { return subscribeDepthLimit; }
    public void setSubscribeDepthLimit(Integer subscribeDepthLimit) { this.subscribeDepthLimit = subscribeDepthLimit; }
    public Integer getSubscribeDepthRemaining() { return subscribeDepthRemaining; }
    public void setSubscribeDepthRemaining(Integer subscribeDepthRemaining) { this.subscribeDepthRemaining = subscribeDepthRemaining; }
    public Integer getHighFreqLimit() { return highFreqLimit; }
    public void setHighFreqLimit(Integer highFreqLimit) { this.highFreqLimit = highFreqLimit; }
    public Integer getMidFreqLimit() { return midFreqLimit; }
    public void setMidFreqLimit(Integer midFreqLimit) { this.midFreqLimit = midFreqLimit; }
    public Integer getLowFreqLimit() { return lowFreqLimit; }
    public void setLowFreqLimit(Integer lowFreqLimit) { this.lowFreqLimit = lowFreqLimit; }
    public Integer getRateMultiple() { return rateMultiple; }
    public void setRateMultiple(Integer rateMultiple) { this.rateMultiple = rateMultiple; }
  }
}
