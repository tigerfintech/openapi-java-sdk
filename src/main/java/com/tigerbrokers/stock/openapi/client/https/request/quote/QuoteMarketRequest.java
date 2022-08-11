package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteMarketModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteMarketResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteMarketRequest extends TigerCommonRequest implements TigerRequest<QuoteMarketResponse> {

  public QuoteMarketRequest() {
    setApiVersion(V2_0);
    setApiMethodName(ApiServiceType.MARKET_STATE);
  }

  public static QuoteMarketRequest newRequest(Market market) {
    return newRequest(market, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public static QuoteMarketRequest newRequest(Market market, Language lang) {
    QuoteMarketRequest request = new QuoteMarketRequest();
    QuoteMarketModel model = new QuoteMarketModel(market, lang);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<QuoteMarketResponse> getResponseClass() {
    return QuoteMarketResponse.class;
  }
}
