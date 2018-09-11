package com.tigerbrokers.stock.openapi.client.util.builder;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import com.tigerbrokers.stock.openapi.client.util.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by lijiawen on 2018/07/24.
 */
public class AccountParamBuilder {

  private Map<String, Object> paramMap = new HashMap<>();

  private AccountParamBuilder() {
  }

  public static AccountParamBuilder instance() {
    return new AccountParamBuilder();
  }

  public AccountParamBuilder conid(String conid) {
    paramMap.put("conid", conid);
    return this;
  }

  public AccountParamBuilder account(String account) {
    paramMap.put("account", account);
    return this;
  }

  public AccountParamBuilder subAccounts(List<String> subAccounts) {
    paramMap.put("sub_accounts", subAccounts);
    return this;
  }

  public AccountParamBuilder symbol(String symbol) {
    paramMap.put("symbol", symbol);
    return this;
  }

  public AccountParamBuilder currency(Currency currency) {
    paramMap.put("currency", currency.name());
    return this;
  }

  public AccountParamBuilder exchange(String exchange) {
    paramMap.put("exchange", exchange);
    return this;
  }

  public AccountParamBuilder market(Market market) {
    paramMap.put("market", market.name());
    return this;
  }

  public AccountParamBuilder segment(boolean segment) {
    paramMap.put("segment", segment);
    return this;
  }

  public AccountParamBuilder marketValue(boolean marketValue) {
    paramMap.put("market_value", marketValue);
    return this;
  }

  public AccountParamBuilder startDate(String startDate) {
    return setTime("start_date", startDate, TimeZoneId.Shanghai);
  }

  public AccountParamBuilder startDate(String startDate, TimeZoneId zoneId) {
    return setTime("start_date", startDate, zoneId);
  }

  public AccountParamBuilder endDate(String endDate) {
    return setTime("end_date", endDate, TimeZoneId.Shanghai);
  }

  public AccountParamBuilder endDate(String endDate, TimeZoneId zoneId) {
    return setTime("end_date", endDate, zoneId);
  }

  public AccountParamBuilder setTime(String key, String time, TimeZoneId zoneId) {
    Date date = DateUtils.setZoneDate(time, zoneId);
    if (date != null) {
      paramMap.put(key, date.getTime());
    }
    return this;
  }

  public AccountParamBuilder limit(int limit) {
    paramMap.put("limit", limit);
    return this;
  }

  public AccountParamBuilder states(List<Integer> statusList) {
    paramMap.put("states", statusList);
    return this;
  }

  public AccountParamBuilder secType(SecType secType) {
    paramMap.put("sec_type", secType.name());
    return this;
  }

  public AccountParamBuilder orderId(int orderId) {
    paramMap.put("order_id", orderId);
    return this;
  }

  public AccountParamBuilder isBrief(boolean isBrief) {
    paramMap.put("is_brief", isBrief);
    return this;
  }

  public String buildJson() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.putAll(paramMap);
    return jsonObject.toJSONString();
  }
}
