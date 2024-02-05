package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteCapitalModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteCapitalDistributionResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

/**
 * Description:
 * Created by bean on 2022/11/14.
 */
public class QuoteCapitalDistributionRequest extends TigerCommonRequest implements TigerRequest<QuoteCapitalDistributionResponse> {

  public QuoteCapitalDistributionRequest() {
    setApiMethodName(MethodName.CAPITAL_DISTRIBUTION);
  }

  public static QuoteCapitalDistributionRequest newRequest(String symbol, Market market) {
    return newRequest(symbol, market, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public static QuoteCapitalDistributionRequest newRequest(String symbol,
      Market market, Language lang) {
    QuoteCapitalDistributionRequest request = new QuoteCapitalDistributionRequest();

    QuoteCapitalModel model = new QuoteCapitalModel(symbol, market.name(), lang);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<QuoteCapitalDistributionResponse> getResponseClass() {
    return QuoteCapitalDistributionResponse.class;
  }
}
