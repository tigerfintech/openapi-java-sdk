package com.tigerbrokers.stock.openapi.client.https.request.financial;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.model.FinancialDailyModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.financial.FinancialDailyResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2019/01/18.
 */
public class FinancialDailyRequest extends TigerCommonRequest implements TigerRequest<FinancialDailyResponse> {

  public FinancialDailyRequest() {
    setApiMethodName(ApiServiceType.FINANCIAL_DAILY);
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
  }

  public static FinancialDailyRequest newRequest(List<String> symbols, List<String> fields, Date beginDate,
      Date endDate) {
    return newRequest(symbols, Market.US, fields, beginDate, endDate);
  }

  public static FinancialDailyRequest newRequest(List<String> symbols, Market market, List<String> fields,
      Date beginDate, Date endDate) {

    FinancialDailyRequest request = new FinancialDailyRequest();
    FinancialDailyModel model = new FinancialDailyModel();
    model.setSymbols(symbols);
    model.setMarket(market);
    model.setFields(fields);
    model.setBeginDate(beginDate);
    model.setEndDate(endDate);
    request.setApiModel(model);

    return request;
  }

  @Override
  public Class<FinancialDailyResponse> getResponseClass() {
    return FinancialDailyResponse.class;
  }
}
