package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;

/**
 * Created on 2025/4/15
 *
 * @author: sukai
 */
public class QuoteBrokerHoldModel extends ApiModel {

  private Market market;

  private Integer page;

  private Integer limit;

  @JSONField(name = "order_by")
  private String orderBy;

  private String direction;

  public QuoteBrokerHoldModel(Market market, Integer limit, Integer page, String orderBy, String direction, Language lang) {
    this.market = market;
    this.page = page;
    this.limit = limit;
    this.orderBy = orderBy;
    this.direction = direction;
    this.lang = lang;
  }

  public QuoteBrokerHoldModel(Market market, Integer limit, Integer page, String orderBy, String direction) {
    this(market, limit, page, orderBy, direction, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public QuoteBrokerHoldModel(Market market, Integer limit) {
    this(market, limit, 0, "marketValue", "DESC", ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public QuoteBrokerHoldModel(Market market, Integer limit, Integer page) {
    this(market, limit, page, "marketValue", "DESC", ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public Market getMarket() {
    return market;
  }

  public void setMarket(Market market) {
    this.market = market;
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public String getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(String orderBy) {
    this.orderBy = orderBy;
  }

  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
    this.direction = direction;
  }
}
