package com.tigerbrokers.stock.openapi.client.https.request.financial;

import com.tigerbrokers.stock.openapi.client.https.domain.financial.model.FinancialCurrencyModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.financial.FinancialCurrencyResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import java.util.List;

/**
 * Description:
 * Created by bean on 2023/08/10.
 */
public class FinancialCurrencyRequest extends TigerCommonRequest implements TigerRequest<FinancialCurrencyResponse> {

  public FinancialCurrencyRequest() {
    setApiMethodName(MethodName.FINANCIAL_CURRENCY);
  }

  public static FinancialCurrencyRequest newRequest(List<String> symbols, Market market) {
    FinancialCurrencyRequest request = new FinancialCurrencyRequest();
    FinancialCurrencyModel model = new FinancialCurrencyModel();
    model.setSymbols(symbols);
    model.setMarket(market);
    request.setApiModel(model);

    return request;
  }

  public FinancialCurrencyModel getApiModel() {
    if (apiModel == null) {
      apiModel = new FinancialCurrencyModel();
    }
    return (FinancialCurrencyModel)apiModel;
  }

  public FinancialCurrencyRequest symbols(List<String> symbols) {
    getApiModel().setSymbols(symbols);
    return this;
  }

  public FinancialCurrencyRequest market(Market market) {
    getApiModel().setMarket(market);
    return this;
  }

  @Override
  public Class<FinancialCurrencyResponse> getResponseClass() {
    return FinancialCurrencyResponse.class;
  }
}
