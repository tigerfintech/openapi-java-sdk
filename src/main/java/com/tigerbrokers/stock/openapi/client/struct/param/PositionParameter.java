package com.tigerbrokers.stock.openapi.client.struct.param;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/05/31.
 */
public class PositionParameter {

  private String account;
  @JSONField(name = "sec_type")
  private SecType secType;
  private String symbol;
  private String currency;
  private String market;
  @JSONField(name = "sub_accounts")
  private List<String> subAccounts;

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public SecType getSecType() {
    return secType;
  }

  public void setSecType(SecType secType) {
    this.secType = secType;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public List<String> getSubAccounts() {
    return subAccounts;
  }

  public void setSubAccounts(List<String> subAccounts) {
    this.subAccounts = subAccounts;
  }
}
