package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteTimelineModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTimelineResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeLineType;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteTimelineRequest extends TigerCommonRequest implements TigerRequest<QuoteTimelineResponse> {

  public QuoteTimelineRequest() {
    setApiVersion(V2_0);
    setApiMethodName(ApiServiceType.TIMELINE);
  }

  public static QuoteTimelineRequest newRequest(List<String> symbols) {
    return newRequest(symbols, null);
  }

  public static QuoteTimelineRequest newRequest(List<String> symbols, Long beginTime) {
    return newRequest(symbols, beginTime, false, TimeLineType.day);
  }

  public static QuoteTimelineRequest newRequest(List<String> symbols, Long beginTime, boolean includeHourTrading) {
    return newRequest(symbols, beginTime, includeHourTrading, TimeLineType.day, Language.en_US);
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
    return newRequest(symbols, beginTime, includeHourTrading, timeLineType, Language.en_US);
  }

  public static QuoteTimelineRequest newRequest(List<String> symbols, Long beginTime, boolean includeHourTrading,
      TimeLineType timeLineType, Language lang) {
    QuoteTimelineRequest request = new QuoteTimelineRequest();
    QuoteTimelineModel model = new QuoteTimelineModel(symbols, beginTime, includeHourTrading, timeLineType, lang);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<QuoteTimelineResponse> getResponseClass() {
    return QuoteTimelineResponse.class;
  }
}
