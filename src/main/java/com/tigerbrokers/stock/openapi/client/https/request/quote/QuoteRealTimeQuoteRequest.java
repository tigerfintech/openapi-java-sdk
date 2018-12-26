package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteSymbolModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteRealTimeQuoteResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteRealTimeQuoteRequest extends TigerCommonRequest implements TigerRequest<QuoteRealTimeQuoteResponse> {

  public QuoteRealTimeQuoteRequest() {
    setApiVersion(V2_0);
    setApiMethodName(ApiServiceType.REAL_TIME_QUOTE);
  }

  public static QuoteRealTimeQuoteRequest newRequest(List<String> symbols) {
    return newRequest(symbols, null);
  }

  public static QuoteRealTimeQuoteRequest newRequest(List<String> symbols, Language lang) {
    QuoteRealTimeQuoteRequest request = new QuoteRealTimeQuoteRequest();
    QuoteSymbolModel model = new QuoteSymbolModel(symbols, lang);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<QuoteRealTimeQuoteResponse> getResponseClass() {
    return QuoteRealTimeQuoteResponse.class;
  }
}
