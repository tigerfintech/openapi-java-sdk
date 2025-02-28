package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteSymbolModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteOvernightResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

import java.util.List;

/**
 * Description:
 * Created by bean on 2025/02/28.
 */
public class QuoteOvernightRequest extends TigerCommonRequest implements TigerRequest<QuoteOvernightResponse> {

  public QuoteOvernightRequest() {
    setApiMethodName(MethodName.QUOTE_OVERNIGHT);
  }

  public static QuoteOvernightRequest newRequest(List<String> symbols) {
    return newRequest(symbols, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public static QuoteOvernightRequest newRequest(List<String> symbols, Language lang) {
    QuoteOvernightRequest request = new QuoteOvernightRequest();
    QuoteSymbolModel model = new QuoteSymbolModel(symbols, lang);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<QuoteOvernightResponse> getResponseClass() {
    return QuoteOvernightResponse.class;
  }
}
