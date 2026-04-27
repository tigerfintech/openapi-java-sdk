package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

import java.io.Serializable;

/**
 * Created on 2025/4/15
 *
 * @author: sukai
 */
public class BrokerHoldItem implements Serializable {
  private String orgId;
  private String orgName;
  private String date;
  private Long sharesHold;
  private Double marketValue;
  private Long buyAmount;
  private Long buyAmount5;
  private Long buyAmount20;
  private Long buyAmount60;
  private String market;

  public String getOrgId() {
    return orgId;
  }

  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }

  public String getOrgName() {
    return orgName;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public Long getSharesHold() {
    return sharesHold;
  }

  public void setSharesHold(Long sharesHold) {
    this.sharesHold = sharesHold;
  }

  public Double getMarketValue() {
    return marketValue;
  }

  public void setMarketValue(Double marketValue) {
    this.marketValue = marketValue;
  }

  public Long getBuyAmount() {
    return buyAmount;
  }

  public void setBuyAmount(Long buyAmount) {
    this.buyAmount = buyAmount;
  }

  public Long getBuyAmount5() {
    return buyAmount5;
  }

  public void setBuyAmount5(Long buyAmount5) {
    this.buyAmount5 = buyAmount5;
  }

  public Long getBuyAmount20() {
    return buyAmount20;
  }

  public void setBuyAmount20(Long buyAmount20) {
    this.buyAmount20 = buyAmount20;
  }

  public Long getBuyAmount60() {
    return buyAmount60;
  }

  public void setBuyAmount60(Long buyAmount60) {
    this.buyAmount60 = buyAmount60;
  }

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }
}
