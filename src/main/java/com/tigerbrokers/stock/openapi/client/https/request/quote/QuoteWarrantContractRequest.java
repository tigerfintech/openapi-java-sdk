package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteWarrantContractModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteWarrantContractResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteWarrantContractRequest extends TigerCommonRequest
    implements TigerRequest<QuoteWarrantContractResponse> {

  public QuoteWarrantContractRequest() {
    setApiVersion(V2_0);
    setApiMethodName(ApiServiceType.WAR_CONTRACT);
  }

  public static QuoteWarrantContractRequest newRequest(List<String> symbols) {
    return newRequest(symbols, SecType.WAR);
  }

  public static QuoteWarrantContractRequest newRequest(List<String> symbols, SecType secType) {
    return newRequest(symbols, secType, Language.en_US);
  }

  public static QuoteWarrantContractRequest newRequest(List<String> symbols, SecType secType, Language lang) {
    QuoteWarrantContractRequest request = new QuoteWarrantContractRequest();
    QuoteWarrantContractModel model = new QuoteWarrantContractModel(symbols, secType, lang);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<QuoteWarrantContractResponse> getResponseClass() {
    return QuoteWarrantContractResponse.class;
  }
}
