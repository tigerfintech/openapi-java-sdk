package com.tigerbrokers.stock.openapi.client.https.request.fund;

import com.tigerbrokers.stock.openapi.client.https.domain.fund.model.FundSymbolModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.fund.FundContractsResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import java.util.List;

/**
 * Description:
 * Created by bean on 2023/07/25.
 */
public class FundContractsRequest extends TigerCommonRequest implements TigerRequest<FundContractsResponse> {

  public FundContractsRequest() {
    setApiMethodName(MethodName.FUND_CONTRACTS);
  }

  public static FundContractsRequest newRequest(List<String> symbols) {
    return newRequest(symbols, null);
  }

  public static FundContractsRequest newRequest(List<String> symbols, Language lang) {
    FundContractsRequest request = new FundContractsRequest();
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

  public FundContractsRequest symbols(List<String> symbols) {
    getApiModel().setSymbols(symbols);
    return this;
  }

  public FundContractsRequest lang(Language lang) {
    getApiModel().setLang(lang);
    return this;
  }

  @Override
  public Class<FundContractsResponse> getResponseClass() {
    return FundContractsResponse.class;
  }
}
