package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteMarketModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteSymbolNameResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteSymbolNameRequest extends TigerCommonRequest implements TigerRequest<QuoteSymbolNameResponse> {

  public QuoteSymbolNameRequest() {
    setApiVersion(V2_0);
    setApiMethodName(MethodName.ALL_SYMBOL_NAMES);
  }

  public static QuoteSymbolNameRequest newRequest(Market market) {
    return newRequest(market, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public static QuoteSymbolNameRequest newRequest(Market market, Language lang) {
    QuoteSymbolNameRequest request = new QuoteSymbolNameRequest();
    QuoteMarketModel model = new QuoteMarketModel(market, lang);
    request.setApiModel(model);
    return request;
  }

  public QuoteMarketModel getApiModel() {
    if (apiModel == null) {
      apiModel = new QuoteMarketModel();
    }
    return (QuoteMarketModel)apiModel;
  }

  public QuoteSymbolNameRequest market(Market market) {
    getApiModel().setMarket(market);
    return this;
  }

  public QuoteSymbolNameRequest includeOTC(Boolean includeOTC) {
    getApiModel().setIncludeOTC(includeOTC);
    return this;
  }

  @Override
  public Class<QuoteSymbolNameResponse> getResponseClass() {
    return QuoteSymbolNameResponse.class;
  }
}
