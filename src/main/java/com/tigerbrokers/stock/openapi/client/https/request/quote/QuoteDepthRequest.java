package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteDepthModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteDepthResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.TradeSession;
import java.util.List;

/**
 * author：ltc
 * date：2019/08/13
 */
public class QuoteDepthRequest extends TigerCommonRequest implements TigerRequest<QuoteDepthResponse> {

  public QuoteDepthRequest() {
    setApiVersion(V2_0);
    setApiMethodName(MethodName.QUOTE_DEPTH);
  }

  public static QuoteDepthRequest newRequest(List<String> symbols, String market) {
    return newRequest(symbols, market, null);
  }

  public static QuoteDepthRequest newRequest(List<String> symbols, String market,
      TradeSession tradeSession) {
    QuoteDepthRequest request = new QuoteDepthRequest();
    QuoteDepthModel quoteDepthModel = new QuoteDepthModel(symbols, market, tradeSession);
    request.setApiModel(quoteDepthModel);
    return request;
  }

  @Override
  public Class<QuoteDepthResponse> getResponseClass() {
    return QuoteDepthResponse.class;
  }
}
