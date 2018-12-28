package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteTimelineModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTimelineResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
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

  public static QuoteTimelineRequest newRequest(List<String> symbols, Long beginTime) {
    return newRequest(symbols, beginTime, false);
  }

  public static QuoteTimelineRequest newRequest(List<String> symbols, Long beginTime, boolean includeHourTrading) {
    return newRequest(symbols, beginTime, includeHourTrading, Language.en_US);
  }

  public static QuoteTimelineRequest newRequest(List<String> symbols, Long beginTime, boolean includeHourTrading,
      Language lang) {
    QuoteTimelineRequest request = new QuoteTimelineRequest();
    QuoteTimelineModel model = new QuoteTimelineModel(symbols, beginTime, includeHourTrading, lang);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<QuoteTimelineResponse> getResponseClass() {
    return QuoteTimelineResponse.class;
  }
}
