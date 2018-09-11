package com.tigerbrokers.stock.openapi.client.struct.param;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/05/31.
 */
public class AssetParameter {

  private String account;
  private boolean segment = false;
  @JSONField(name = "market_value")
  private boolean marketValue = false;
  @JSONField(name = "sub_accounts")
  private List<String> subAccounts;

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public boolean isSegment() {
    return segment;
  }

  public void setSegment(boolean segment) {
    this.segment = segment;
  }

  public boolean isMarketValue() {
    return marketValue;
  }

  public void setMarketValue(boolean marketValue) {
    this.marketValue = marketValue;
  }

  public List<String> getSubAccounts() {
    return subAccounts;
  }

  public void setSubAccounts(List<String> subAccounts) {
    this.subAccounts = subAccounts;
  }
}
