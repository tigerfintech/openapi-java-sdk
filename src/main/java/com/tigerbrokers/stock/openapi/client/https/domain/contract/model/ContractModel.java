package com.tigerbrokers.stock.openapi.client.https.domain.contract.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import lombok.Data;

/**
 * 作者：ltc
 * 时间：2019/05/29
 */
@Data
public class ContractModel extends ApiModel {

  private String account;
  private String symbol;
  @JSONField(name = "sec_type")
  private String secType;
  private String currency;
  private String expiry;
  private Double strike;
  private String right;
  private String exchange;

  public ContractModel() {

  }

  public ContractModel(String account, String symbol) {
    this.symbol = symbol;
    this.account = account;
  }

  public ContractModel(String account, String symbol, String secType) {
    this(account, symbol);
    this.secType = secType;
  }

  public ContractModel(String account, String symbol, String secType, String expiry, Double strike, String right) {
    this(account, symbol, secType);
    this.strike = strike;
    this.expiry = expiry;
    this.right = right;
  }
}
