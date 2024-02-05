package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteTimelineModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTimelineResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.*;
import com.tigerbrokers.stock.openapi.client.util.DateUtils;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteTimelineRequest extends TigerCommonRequest implements TigerRequest<QuoteTimelineResponse> {

  public QuoteTimelineRequest() {
    setApiVersion(V3_0);
    setApiMethodName(MethodName.TIMELINE);
  }

  public static QuoteTimelineRequest newRequest(List<String> symbols) {
    return newRequest(symbols, null);
  }

  public static QuoteTimelineRequest newRequest(List<String> symbols, Long beginTime) {
    return newRequest(symbols, beginTime, TradeSession.Regular, TimeLineType.day);
  }

  @Deprecated
  public static QuoteTimelineRequest newRequest(List<String> symbols, Long beginTime, boolean includeHourTrading) {
    return newRequest(symbols, beginTime, includeHourTrading ? TradeSession.All : TradeSession.Regular, TimeLineType.day,
        ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }
  public static QuoteTimelineRequest newRequest(List<String> symbols, Long beginTime, TradeSession tradeSession) {
    return newRequest(symbols, beginTime, tradeSession, TimeLineType.day,
        ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  @Deprecated
  public static QuoteTimelineRequest newRequest(List<String> symbols, Long beginTime, boolean includeHourTrading,
      Language lang) {
    return newRequest(symbols, beginTime, includeHourTrading ? TradeSession.All : TradeSession.Regular, TimeLineType.day, lang);
  }
  public static QuoteTimelineRequest newRequest(List<String> symbols, Long beginTime, TradeSession tradeSession,
                                                Language lang) {
    return newRequest(symbols, beginTime, tradeSession, TimeLineType.day, lang);
  }

  @Deprecated
  public static QuoteTimelineRequest newRequest(List<String> symbols, Long beginTime, TimeLineType timeLineType) {
    return newRequest(symbols, beginTime, TradeSession.All, timeLineType);
  }

  @Deprecated
  public static QuoteTimelineRequest newRequest(List<String> symbols, Long beginTime, boolean includeHourTrading,
      TimeLineType timeLineType) {
    return newRequest(symbols, beginTime, includeHourTrading ? TradeSession.All : TradeSession.Regular, timeLineType,
        ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }
  public static QuoteTimelineRequest newRequest(List<String> symbols, Long beginTime, TradeSession tradeSession,
                                                TimeLineType timeLineType) {
    return newRequest(symbols, beginTime, tradeSession, timeLineType,
        ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  @Deprecated
  public static QuoteTimelineRequest newRequest(List<String> symbols, Long beginTime, boolean includeHourTrading,
      TimeLineType timeLineType, Language lang) {
    QuoteTimelineRequest request = new QuoteTimelineRequest();
    QuoteTimelineModel model = new QuoteTimelineModel(symbols, beginTime,
        includeHourTrading ? TradeSession.All : TradeSession.Regular, timeLineType, lang);
    request.setApiModel(model);
    return request;
  }
  public static QuoteTimelineRequest newRequest(List<String> symbols, Long beginTime, TradeSession tradeSession,
                                                TimeLineType timeLineType, Language lang) {
    QuoteTimelineRequest request = new QuoteTimelineRequest();
    QuoteTimelineModel model = new QuoteTimelineModel(symbols, beginTime, tradeSession, timeLineType, lang);
    request.setApiModel(model);
    return request;
  }

  public static QuoteTimelineRequest newRequest(List<String> symbols, String beginTime,
      TimeLineType timeLineType) {
    return newRequest(symbols, beginTime, ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone(),
        TradeSession.Regular, timeLineType, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  @Deprecated
  public static QuoteTimelineRequest newRequest(List<String> symbols,
      String beginTime, boolean includeHourTrading, TimeLineType timeLineType) {
    return newRequest(symbols, beginTime, ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone(),
        includeHourTrading ? TradeSession.All : TradeSession.Regular, timeLineType, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }
  public static QuoteTimelineRequest newRequest(List<String> symbols, String beginTime,
                                                TradeSession tradeSession, TimeLineType timeLineType) {
    return newRequest(symbols, beginTime, ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone(),
        tradeSession, timeLineType, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public static QuoteTimelineRequest newRequest(List<String> symbols,
      String beginTime, TimeZoneId zoneId, TradeSession tradeSession,
      TimeLineType timeLineType, Language lang) {
    QuoteTimelineRequest request = new QuoteTimelineRequest();
    if (zoneId == null) {
      zoneId = ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone();
    }
    Date beginDate = DateUtils.getZoneDate(beginTime, zoneId);
    QuoteTimelineModel model = new QuoteTimelineModel(symbols, beginDate == null ? null : beginDate.getTime(),
        tradeSession, timeLineType, lang);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<QuoteTimelineResponse> getResponseClass() {
    return QuoteTimelineResponse.class;
  }
}
