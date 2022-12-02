package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteStockBrokerModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteStockBrokerResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

/**
 * Description:
 * Created by bean on 2022/11/14.
 */
public class QuoteStockBrokerRequest extends TigerCommonRequest implements TigerRequest<QuoteStockBrokerResponse> {

  public QuoteStockBrokerRequest() {
    setApiMethodName(MethodName.STOCK_BROKER);
  }

  public static QuoteStockBrokerRequest newRequest(String symbol) {
    return newRequest(symbol, null, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public static QuoteStockBrokerRequest newRequest(String symbol, Language lang) {
    return newRequest(symbol, null, lang);
  }

  public static QuoteStockBrokerRequest newRequest(String symbol, Integer limit) {
    return newRequest(symbol, limit, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public static QuoteStockBrokerRequest newRequest(String symbol,
      Integer limit, Language lang) {
    QuoteStockBrokerRequest request = new QuoteStockBrokerRequest();

    QuoteStockBrokerModel model = new QuoteStockBrokerModel(symbol, limit, lang);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<QuoteStockBrokerResponse> getResponseClass() {
    return QuoteStockBrokerResponse.class;
  }
}
