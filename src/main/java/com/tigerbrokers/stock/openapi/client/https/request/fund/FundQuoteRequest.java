package com.tigerbrokers.stock.openapi.client.https.request.fund;

import com.tigerbrokers.stock.openapi.client.https.domain.fund.model.FundSymbolModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.fund.FundQuoteResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import java.util.List;

/**
 * Description:
 * Created by bean on 2023/07/25.
 */
public class FundQuoteRequest extends TigerCommonRequest implements TigerRequest<FundQuoteResponse> {

  public FundQuoteRequest() {
    setApiMethodName(MethodName.FUND_QUOTE);
  }

  public static FundQuoteRequest newRequest(List<String> symbols) {
    return newRequest(symbols, null);
  }

  public static FundQuoteRequest newRequest(List<String> symbols, Language lang) {
    FundQuoteRequest request = new FundQuoteRequest();
    FundSymbolModel model = new FundSymbolModel(symbols, lang);
    request.setApiModel(model);
    return request;
  }

  public FundSymbolModel getApiModel() {
    if (apiModel == null) {
      apiModel = new FundSymbolModel();
    }
    return (FundSymbolModel)apiModel;
  }

  public FundQuoteRequest symbols(List<String> symbols) {
    getApiModel().setSymbols(symbols);
    return this;
  }

  public FundQuoteRequest lang(Language lang) {
    getApiModel().setLang(lang);
    return this;
  }

  @Override
  public Class<FundQuoteResponse> getResponseClass() {
    return FundQuoteResponse.class;
  }
}
