package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteMarketModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteSymbolNameResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteSymbolNameRequest extends TigerCommonRequest implements TigerRequest<QuoteSymbolNameResponse> {

  public QuoteSymbolNameRequest() {
    setApiVersion(V2_0);
    setApiMethodName(ApiServiceType.ALL_SYMBOL_NAMES);
  }

  public static QuoteSymbolNameRequest newRequest(Market market) {
    return newRequest(market, Language.en_US);
  }

  public static QuoteSymbolNameRequest newRequest(Market market, Language lang) {
    QuoteSymbolNameRequest request = new QuoteSymbolNameRequest();
    QuoteMarketModel model = new QuoteMarketModel(market, lang);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<QuoteSymbolNameResponse> getResponseClass() {
    return QuoteSymbolNameResponse.class;
  }
}
