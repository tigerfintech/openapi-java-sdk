package com.tigerbrokers.stock.openapi.client.https.request.financial;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.model.FinancialReportModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.financial.FinancialReportResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.FinancialPeriodType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2019/01/18.
 */
public class FinancialReportRequest extends TigerCommonRequest implements TigerRequest<FinancialReportResponse> {

  public FinancialReportRequest() {
    setApiMethodName(ApiServiceType.FINANCIAL_REPORT);
    setApiVersion(V2_0);
  }

  public static FinancialReportRequest newRequest(List<String> symbols, List<String> fields) {
    return newRequest(symbols, Market.US, fields, FinancialPeriodType.Quarterly);
  }

  public static FinancialReportRequest newRequest(List<String> symbols, List<String> fields,
      FinancialPeriodType periodType) {
    return newRequest(symbols, Market.US, fields, periodType);
  }

  public static FinancialReportRequest newRequest(List<String> symbols, Market market, List<String> fields,
      FinancialPeriodType periodType) {
    FinancialReportRequest request = new FinancialReportRequest();

    FinancialReportModel model = new FinancialReportModel();
    model.setSymbols(symbols);
    model.setMarket(market);
    model.setFields(fields);
    model.setPeriodType(periodType);
    request.setApiModel(model);

    return request;
  }

  @Override
  public Class<FinancialReportResponse> getResponseClass() {
    return FinancialReportResponse.class;
  }
}
