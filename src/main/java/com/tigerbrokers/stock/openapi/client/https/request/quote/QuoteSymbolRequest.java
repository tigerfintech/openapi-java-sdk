package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteMarketModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteSymbolResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.PackageName;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteSymbolRequest extends TigerCommonRequest implements TigerRequest<QuoteSymbolResponse> {

  public QuoteSymbolRequest() {
    setApiVersion(V2_0);
    setApiMethodName(MethodName.ALL_SYMBOLS);
  }

  public static QuoteSymbolRequest newRequest(Market market) {
    return newRequest(market, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public static QuoteSymbolRequest newRequest(Market market, Language lang) {
    QuoteSymbolRequest request = new QuoteSymbolRequest();
    QuoteMarketModel model = new QuoteMarketModel(market, lang);
    request.setApiModel(model);
    return request;
  }

  public static QuoteSymbolRequest newRequest(PackageName packageName) {
    QuoteSymbolRequest request = new QuoteSymbolRequest();
    QuoteMarketModel model = new QuoteMarketModel(packageName);
    request.setApiModel(model);
    return request;
  }

  public QuoteMarketModel getApiModel() {
    if (apiModel == null) {
      apiModel = new QuoteMarketModel();
    }
    return (QuoteMarketModel)apiModel;
  }

  public QuoteSymbolRequest packageName(PackageName packageName) {
    getApiModel().setPackageName(packageName);
    return this;
  }

  public QuoteSymbolRequest market(Market market) {
    getApiModel().setMarket(market);
    return this;
  }

  public QuoteSymbolRequest includeOTC(Boolean includeOTC) {
    getApiModel().setIncludeOTC(includeOTC);
    return this;
  }

  @Override
  public Class<QuoteSymbolResponse> getResponseClass() {
    return QuoteSymbolResponse.class;
  }
}
