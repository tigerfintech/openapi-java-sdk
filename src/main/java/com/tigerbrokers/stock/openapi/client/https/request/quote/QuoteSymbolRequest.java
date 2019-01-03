package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteMarketModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteSymbolResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteSymbolRequest extends TigerCommonRequest implements TigerRequest<QuoteSymbolResponse> {

  public QuoteSymbolRequest() {
    setApiVersion(V2_0);
    setApiMethodName(ApiServiceType.ALL_SYMBOLS);
  }

  public static QuoteSymbolRequest newRequest(Market market) {
    return newRequest(market, Language.en_US);
  }

  public static QuoteSymbolRequest newRequest(Market market, Language lang) {
    QuoteSymbolRequest request = new QuoteSymbolRequest();
    QuoteMarketModel model = new QuoteMarketModel(market, lang);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<QuoteSymbolResponse> getResponseClass() {
    return QuoteSymbolResponse.class;
  }
}
