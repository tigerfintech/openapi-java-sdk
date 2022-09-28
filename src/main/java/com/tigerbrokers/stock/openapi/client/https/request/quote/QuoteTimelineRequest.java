package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteTimelineModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTimelineResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeLineType;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import com.tigerbrokers.stock.openapi.client.util.DateUtils;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteTimelineRequest extends TigerCommonRequest implements TigerRequest<QuoteTimelineResponse> {

  public QuoteTimelineRequest() {
    setApiVersion(V2_0);
    setApiMethodName(MethodName.TIMELINE);
  }

  public static QuoteTimelineRequest newRequest(List<String> symbols) {
    return newRequest(symbols, null);
  }

  public static QuoteTimelineRequest newRequest(List<String> symbols, Long beginTime) {
    return newRequest(symbols, beginTime, false, TimeLineType.day);
  }

  public static QuoteTimelineRequest newRequest(List<String> symbols, Long beginTime, boolean includeHourTrading) {
    return newRequest(symbols, beginTime, includeHourTrading, TimeLineType.day,
        ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public static QuoteTimelineRequest newRequest(List<String> symbols, Long beginTime, boolean includeHourTrading,
      Language lang) {
    return newRequest(symbols, beginTime, includeHourTrading, TimeLineType.day, lang);
  }

  public static QuoteTimelineRequest newRequest(List<String> symbols, Long beginTime, TimeLineType timeLineType) {
    return newRequest(symbols, beginTime, true, timeLineType);
  }

  public static QuoteTimelineRequest newRequest(List<String> symbols, Long beginTime, boolean includeHourTrading,
      TimeLineType timeLineType) {
    return newRequest(symbols, beginTime, includeHourTrading, timeLineType,
        ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public static QuoteTimelineRequest newRequest(List<String> symbols, Long beginTime, boolean includeHourTrading,
      TimeLineType timeLineType, Language lang) {
    QuoteTimelineRequest request = new QuoteTimelineRequest();
    QuoteTimelineModel model = new QuoteTimelineModel(symbols, beginTime, includeHourTrading, timeLineType, lang);
    request.setApiModel(model);
    return request;
  }

  public static QuoteTimelineRequest newRequest(List<String> symbols, String beginTime,
      TimeLineType timeLineType) {
    return newRequest(symbols, beginTime, ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone(),
        false, timeLineType, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public static QuoteTimelineRequest newRequest(List<String> symbols,
      String beginTime, boolean includeHourTrading, TimeLineType timeLineType) {
    return newRequest(symbols, beginTime, ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone(),
        includeHourTrading, timeLineType, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public static QuoteTimelineRequest newRequest(List<String> symbols,
      String beginTime, TimeZoneId zoneId, boolean includeHourTrading,
      TimeLineType timeLineType, Language lang) {
    QuoteTimelineRequest request = new QuoteTimelineRequest();
    if (zoneId == null) {
      zoneId = ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone();
    }
    Date beginDate = DateUtils.getZoneDate(beginTime, zoneId);
    QuoteTimelineModel model = new QuoteTimelineModel(symbols, beginDate == null ? null : beginDate.getTime(),
        includeHourTrading, timeLineType, lang);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<QuoteTimelineResponse> getResponseClass() {
    return QuoteTimelineResponse.class;
  }
}
