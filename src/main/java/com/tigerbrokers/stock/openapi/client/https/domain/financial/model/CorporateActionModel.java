package com.tigerbrokers.stock.openapi.client.https.domain.financial.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.CorporateActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2019/02/28.
 */
public class CorporateActionModel extends ApiModel {

  private List<String> symbols;
  private Market market;
  @JSONField(name = "action_type")
  private CorporateActionType actionType;
  @JSONField(name = "begin_date")
  private Date beginDate;
  @JSONField(name = "end_date")
  private Date endDate;

  public List<String> getSymbols() {
    return symbols;
  }

  public void setSymbols(List<String> symbols) {
    this.symbols = symbols;
  }

  public Market getMarket() {
    return market;
  }

  public void setMarket(Market market) {
    this.market = market;
  }

  public CorporateActionType getActionType() {
    return actionType;
  }

  public void setActionType(CorporateActionType actionType) {
    this.actionType = actionType;
  }

  public Date getBeginDate() {
    return beginDate;
  }

  public void setBeginDate(Date beginDate) {
    this.beginDate = beginDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  @Override
  public String toString() {
    return "CorporateActionModel{" +
        "symbols=" + symbols +
        ", market=" + market +
        ", actionType=" + actionType +
        ", beginDate=" + beginDate +
        ", endDate=" + endDate +
        '}';
  }
}
