package com.tigerbrokers.stock.openapi.client.util.builder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.struct.enums.KType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeLineType;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import com.tigerbrokers.stock.openapi.client.struct.param.QuoteParameter;
import com.tigerbrokers.stock.openapi.client.util.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by lijiawen on 2018/07/24.
 */
public class QuoteParamBuilder {

  private Map<String, Object> paramMap = new HashMap<>();

  private QuoteParamBuilder() {
  }

  public static QuoteParamBuilder instance() {
    return new QuoteParamBuilder();
  }

  public QuoteParamBuilder period(KType period) {
    if (period != null) {
      if (period.name().startsWith("min")) {
        String ktypeName = "";
        switch (period) {
          case min1:
            ktypeName = "1min";
            break;
          case min5:
            ktypeName = "5min";
            break;
          case min15:
            ktypeName = "15min";
            break;
          case min30:
            ktypeName = "30min";
            break;
          case min60:
            ktypeName = "60min";
            break;
        }
        paramMap.put("period", ktypeName);
      } else {
        paramMap.put("period", period.name());
      }
    }
    return this;
  }

  public QuoteParamBuilder period(TimeLineType period) {
    if (period != null) {
      switch (period) {
        case day:
          paramMap.put("period", "day");
          break;
        case day5:
          paramMap.put("period", "5day");
          break;
      }
    }
    return this;
  }

  public QuoteParamBuilder symbol(String symbol) {
    if (symbol != null) {
      paramMap.put("symbol", symbol);
    }
    return this;
  }

  public QuoteParamBuilder symbols(List<String> symbols) {
    if (symbols != null) {
      paramMap.put("symbols", symbols);
    }
    return this;
  }

  public QuoteParamBuilder beginTime(String beginTime) {
    return beginTime(beginTime, TimeZoneId.Shanghai);
  }

  public QuoteParamBuilder beginTime(String beginTime, TimeZoneId zoneId) {
    return setTime("begin_time", beginTime, zoneId);
  }

  public QuoteParamBuilder endTime(String endTime) {
    return endTime(endTime, TimeZoneId.Shanghai);
  }

  public QuoteParamBuilder endTime(String endTime, TimeZoneId zoneId) {
    return setTime("end_time", endTime, zoneId);
  }

  public QuoteParamBuilder setTime(String key, String time, TimeZoneId zoneId) {
    Date date = DateUtils.getZoneDate(time, zoneId);
    if (date != null) {
      paramMap.put(key, date.getTime());
    }
    return this;
  }

  public QuoteParamBuilder limit(Integer limit) {
    if (limit != null) {
      paramMap.put("limit", limit);
    }
    return this;
  }

  public QuoteParamBuilder right(String right) {
    if (right != null) {
      paramMap.put("right", right);
    }
    return this;
  }

  public QuoteParamBuilder hourTrading(Boolean hourTrading) {
    if (hourTrading != null) {
      paramMap.put("include_hour_trading", hourTrading);
    }
    return this;
  }

  public QuoteParamBuilder askBid(Boolean askBid) {
    if (askBid != null) {
      paramMap.put("include_ask_bid", askBid);
    }
    return this;
  }

  public QuoteParamBuilder market(Market market) {
    if (market != null) {
      paramMap.put("market", market);
    }
    return this;
  }

  public QuoteParamBuilder beginIndex(Integer beginIndex) {
    if (beginIndex != null) {
      paramMap.put("begin_index", beginIndex);
    }
    return this;
  }

  public QuoteParamBuilder endIndex(Integer endIndex) {
    if (endIndex != null) {
      paramMap.put("end_index", endIndex);
    }
    return this;
  }

  public QuoteParamBuilder language(Language language) {
    if (language != null) {
      paramMap.put("lang", language.name());
    }
    return this;
  }

  public QuoteParameter build() {
    return JSONObject.parseObject(JSON.toJSONString(paramMap), QuoteParameter.class);
  }

  public String buildJson() {
    return JSONObject.toJSONString(paramMap);
  }
}
