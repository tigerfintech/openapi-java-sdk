package com.tigerbrokers.stock.openapi.client.https.request.financial;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.model.FinancialDailyModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.financial.FinancialDailyResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import com.tigerbrokers.stock.openapi.client.util.DateUtils;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2019/01/18.
 */
public class FinancialDailyRequest extends TigerCommonRequest implements TigerRequest<FinancialDailyResponse> {

  public FinancialDailyRequest() {
    setApiMethodName(MethodName.FINANCIAL_DAILY);
    setApiVersion(V2_0);
  }

  public static FinancialDailyRequest newRequest(List<String> symbols, List<String> fields, Date beginDate,
      Date endDate) {
    return newRequest(symbols, Market.US, fields, beginDate, endDate);
  }

  public static FinancialDailyRequest newRequest(List<String> symbols, List<String> fields, String beginDate,
      String endDate) {
    return newRequest(symbols, fields, beginDate, endDate, ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone());
  }

  public static FinancialDailyRequest newRequest(List<String> symbols, List<String> fields, String beginDate,
      String endDate, TimeZoneId zoneId) {
    return newRequest(symbols, Market.US, fields, DateUtils.getZoneDate(beginDate, zoneId),
        DateUtils.getZoneDate(endDate, zoneId));
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
