package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteSymbolModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteDelayResponse;
import java.util.List;

/**
 * Description: 延迟行情
 * Created by sk on 2021/11/18.
 */
public class QuoteDelayRequest extends TigerCommonRequest implements TigerRequest<QuoteDelayResponse> {

  public QuoteDelayRequest() {
    setApiVersion(V2_0);
    setApiMethodName(ApiServiceType.QUOTE_DELAY);
  }

  public static QuoteDelayRequest newRequest(List<String> symbols) {
    QuoteDelayRequest request = new QuoteDelayRequest();
    QuoteSymbolModel quoteSymbolModel = new QuoteSymbolModel(symbols);
    request.setApiModel(quoteSymbolModel);
    return request;
  }

  @Override
  public Class<QuoteDelayResponse> getResponseClass() {
    return QuoteDelayResponse.class;
  }
}
