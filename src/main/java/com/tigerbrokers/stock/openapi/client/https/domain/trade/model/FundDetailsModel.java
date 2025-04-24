package com.tigerbrokers.stock.openapi.client.https.domain.trade.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

import java.util.List;

/**
 * Created on 2025/4/24
 *
 * @author: sukai
 */
public class FundDetailsModel extends ApiModel {

  private String account;
  @JSONField(name = "secret_key")
  private String secretKey;
  @JSONField(name = "seg_types")
  private List<String> segTypes;

  @JSONField(name = "fund_type")
  private String fundType;

  private String currency;

  @JSONField(name = "start_date")
  private String startDate;
  @JSONField(name = "end_date")
  private String endDate;

  private Long start;
  private Long limit;

  public FundDetailsModel(String account) {
    this.account = account;
  }

  public FundDetailsModel(String account, List<String> segTypes) {
    this.account = account;
    this.segTypes = segTypes;
  }

  public FundDetailsModel(String account, List<String> segTypes, String secretKey) {
    this.account = account;
    this.secretKey = secretKey;
    this.segTypes = segTypes;
  }

  public FundDetailsModel(String account, List<String> segTypes, String fundType, String secretKey) {
    this.account = account;
    this.secretKey = secretKey;
    this.segTypes = segTypes;
    this.fundType = fundType;
  }

  public FundDetailsModel(String account, List<String> segTypes, Long start, Long limit) {
    this.account = account;
    this.segTypes = segTypes;
    this.start = start;
    this.limit = limit;
  }

  public FundDetailsModel(String account, List<String> segTypes, Long start, Long limit, String secretKey) {
    this.account = account;
    this.segTypes = segTypes;
    this.start = start;
    this.limit = limit;
    this.secretKey = secretKey;
  }


  @Override
  public String getAccount() {
    return account;
  }

  @Override
  public void setAccount(String account) {
    this.account = account;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public List<String> getSegTypes() {
    return segTypes;
  }

  public void setSegTypes(List<String> segTypes) {
    this.segTypes = segTypes;
  }

  public String getFundType() {
    return fundType;
  }

  public void setFundType(String fundType) {
    this.fundType = fundType;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public Long getStart() {
    return start;
  }

  public void setStart(Long start) {
    this.start = start;
  }

  public Long getLimit() {
    return limit;
  }

  public void setLimit(Long limit) {
    this.limit = limit;
  }
}
