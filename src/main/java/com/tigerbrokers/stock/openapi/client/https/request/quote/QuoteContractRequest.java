package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteContractModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteContractResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteContractRequest extends TigerCommonRequest implements TigerRequest<QuoteContractResponse> {

  public QuoteContractRequest() {
    setApiVersion(V2_0);
    setApiMethodName(ApiServiceType.QUOTE_CONTRACT);
  }

  public static QuoteContractRequest newRequest(List<String> symbols) {
    return newRequest(symbols, SecType.WAR);
  }

  public static QuoteContractRequest newRequest(List<String> symbols, SecType secType) {
    return newRequest(symbols, secType, Language.en_US);
  }

  public static QuoteContractRequest newRequest(List<String> symbols, SecType secType, Language lang) {
    QuoteContractRequest request = new QuoteContractRequest();
    QuoteContractModel model = new QuoteContractModel(symbols, secType, lang);
    request.setApiModel(model);
    return request;
  }

  public static QuoteContractRequest newRequest(List<String> symbols, SecType secType, String expiry) {
    QuoteContractRequest request = new QuoteContractRequest();
    QuoteContractModel model = new QuoteContractModel(symbols, secType, expiry);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<QuoteContractResponse> getResponseClass() {
    return QuoteContractResponse.class;
  }
}
