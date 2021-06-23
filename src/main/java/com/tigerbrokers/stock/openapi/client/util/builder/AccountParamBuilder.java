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
    if (conid != null) {
      paramMap.put("conid", conid);
    }
    return this;
  }

  public AccountParamBuilder account(String account) {
    if (account != null) {
      paramMap.put("account", account);
    }
    return this;
  }

  public AccountParamBuilder subAccounts(List<String> subAccounts) {
    if (subAccounts != null) {
      paramMap.put("sub_accounts", subAccounts);
    }
    return this;
  }

  public AccountParamBuilder symbol(String symbol) {
    if (symbol != null) {
      paramMap.put("symbol", symbol);
    }
    return this;
  }

  public AccountParamBuilder symbols(List<String> symbols) {
    if (symbols != null) {
      paramMap.put("symbols", symbols);
    }
    return this;
  }

  public AccountParamBuilder continuous(Boolean continuous) {
    if (continuous != null) {
      paramMap.put("continuous", continuous);
    }
    return this;
  }

  public AccountParamBuilder currency(Currency currency) {
    if (currency != null) {
      paramMap.put("currency", currency.name());
    }
    return this;
  }

  public AccountParamBuilder exchange(String exchange) {
    if (exchange != null) {
      paramMap.put("exchange", exchange);
    }
    return this;
  }

  public AccountParamBuilder market(Market market) {
    if (market != null) {
      paramMap.put("market", market.name());
    }
    return this;
  }

  public AccountParamBuilder segment(Boolean segment) {
    if (segment != null) {
      paramMap.put("segment", segment);
    }
    return this;
  }

  public AccountParamBuilder marketValue(Boolean marketValue) {
    if (marketValue != null) {
      paramMap.put("market_value", marketValue);
    }
    return this;
  }

  public AccountParamBuilder strike(Double strike) {
    if (strike != null) {
      paramMap.put("strike", strike);
    }
    return this;
  }

  public AccountParamBuilder expiry(String expiry) {
    if (expiry != null) {
      paramMap.put("expiry", expiry);
    }
    return this;
  }

  public AccountParamBuilder right(String right) {
    if (right != null) {
      paramMap.put("right", right);
    }
    return this;
  }

  public AccountParamBuilder startDate(Long timestamp) {
    if (timestamp != null && timestamp > 0) {
      paramMap.put("start_date", timestamp);
    }
    return this;
  }

  public AccountParamBuilder startDate(String startDate) {
    return setTime("start_date", startDate, TimeZoneId.Shanghai);
  }

  public AccountParamBuilder startDate(String startDate, TimeZoneId zoneId) {
    return setTime("start_date", startDate, zoneId);
  }

  public AccountParamBuilder endDate(Long timestamp) {
    if (timestamp != null && timestamp > 0) {
      paramMap.put("end_date", timestamp);
    }
    return this;
  }

  public AccountParamBuilder endDate(String endDate) {
    return setTime("end_date", endDate, TimeZoneId.Shanghai);
  }

  public AccountParamBuilder endDate(String endDate, TimeZoneId zoneId) {
    return setTime("end_date", endDate, zoneId);
  }

  public AccountParamBuilder setTime(String key, String time, TimeZoneId zoneId) {
    Date date = DateUtils.getZoneDate(time, zoneId);
    if (date != null) {
      paramMap.put(key, date.getTime());
    }
    return this;
  }

  public AccountParamBuilder limit(Integer limit) {
    if (limit != null) {
      paramMap.put("limit", limit);
    }
    return this;
  }

  public AccountParamBuilder states(List<String> statusList) {
    if (statusList != null) {
      paramMap.put("states", statusList);
    }
    return this;
  }

  public AccountParamBuilder secType(SecType secType) {
    if (secType != null) {
      paramMap.put("sec_type", secType.name());
    }
    return this;
  }

  public AccountParamBuilder id(Long id) {
    if (id != null) {
      paramMap.put("id", id);
    }
    return this;
  }

  public AccountParamBuilder orderId(Integer orderId) {
    if (orderId != null) {
      paramMap.put("order_id", orderId);
    }
    return this;
  }

  public AccountParamBuilder isBrief(Boolean isBrief) {
    if (isBrief != null) {
      paramMap.put("is_brief", isBrief);
    }
    return this;
  }

  public AccountParamBuilder parentId(Integer parentId) {
    if (parentId != null) {
      paramMap.put("parent_id", parentId);
    }
    return this;
  }

  public AccountParamBuilder secretKey(String secretKey) {
    if (secretKey != null) {
      paramMap.put("secret_key", secretKey);
    }
    return this;
  }

  public String buildJson() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.putAll(paramMap);
    return jsonObject.toJSONString();
  }
}
