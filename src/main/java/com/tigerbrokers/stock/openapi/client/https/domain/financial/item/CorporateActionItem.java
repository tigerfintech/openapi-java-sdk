package com.tigerbrokers.stock.openapi.client.https.domain.financial.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.CorporateActionType;
import java.time.LocalDate;

/**
 * Description:
 * Created by lijiawen on 2019/02/28.
 */
public class CorporateActionItem extends ApiModel {

  private Long id;
  private String symbol;
  private String market;
  private String exchange;
  private LocalDate executeDate;
  private CorporateActionType actionType;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public String getExchange() {
    return exchange;
  }

  public void setExchange(String exchange) {
    this.exchange = exchange;
  }

  public LocalDate getExecuteDate() {
    return executeDate;
  }

  public void setExecuteDate(LocalDate executeDate) {
    this.executeDate = executeDate;
  }

  public CorporateActionType getActionType() {
    return actionType;
  }

  public void setActionType(CorporateActionType actionType) {
    this.actionType = actionType;
  }

  @Override
  public String toString() {
    return "CorporateActionItem{" +
        "id=" + id +
        ", symbol='" + symbol + '\'' +
        ", market='" + market + '\'' +
        ", exchange='" + exchange + '\'' +
        ", executeDate=" + executeDate +
        ", actionType=" + actionType +
        '}';
  }
}
