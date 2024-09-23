package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteContractModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteContractResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteContractRequest extends TigerCommonRequest implements TigerRequest<QuoteContractResponse> {

  public QuoteContractRequest() {
    setApiVersion(V2_0);
    setApiMethodName(MethodName.QUOTE_CONTRACT);
  }

  public static QuoteContractRequest newRequest(String symbol) {
    return newRequest(symbol, SecType.WAR);
  }

  public static QuoteContractRequest newRequest(String symbol, SecType secType) {
    return newRequest(symbol, secType, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public static QuoteContractRequest newRequest(String symbol, SecType secType, Language lang) {
    QuoteContractRequest request = new QuoteContractRequest();
    QuoteContractModel model = new QuoteContractModel(symbol, secType, lang);
    request.setApiModel(model);
    return request;
  }

  public static QuoteContractRequest newRequest(String symbol, SecType secType, String expiry) {
    QuoteContractRequest request = new QuoteContractRequest();
    QuoteContractModel model = new QuoteContractModel(symbol, secType, expiry);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<QuoteContractResponse> getResponseClass() {
    return QuoteContractResponse.class;
  }
}
