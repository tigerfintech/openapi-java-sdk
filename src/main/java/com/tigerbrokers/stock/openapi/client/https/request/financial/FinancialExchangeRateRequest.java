package com.tigerbrokers.stock.openapi.client.https.request.financial;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.model.FinancialExchangeRateModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.financial.FinancialExchangeRateResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import com.tigerbrokers.stock.openapi.client.util.DateUtils;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * Created by bean on 2023/08/10.
 */
public class FinancialExchangeRateRequest extends TigerCommonRequest implements TigerRequest<FinancialExchangeRateResponse> {

  public FinancialExchangeRateRequest() {
    setApiMethodName(MethodName.FINANCIAL_EXCHANGE_RATE);
  }

  public static FinancialExchangeRateRequest newRequest(List<String> currencyList,
      String beginDate, String endDate) {
    return newRequest(currencyList, beginDate, endDate, ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone());
  }

  public static FinancialExchangeRateRequest newRequest(List<String> currencyList,
      String beginDate, String endDate, TimeZoneId zoneId) {
    return newRequest(currencyList, DateUtils.getZoneDate(beginDate, zoneId),
        DateUtils.getZoneDate(endDate, zoneId));
  }

  public static FinancialExchangeRateRequest newRequest(List<String> currencyList,
      Date beginDate, Date endDate) {

    FinancialExchangeRateRequest request = new FinancialExchangeRateRequest();
    FinancialExchangeRateModel model = new FinancialExchangeRateModel();
    model.setCurrencyList(currencyList);
    model.setBeginDate(beginDate);
    model.setEndDate(endDate);
    request.setApiModel(model);

    return request;
  }

  public FinancialExchangeRateModel getApiModel() {
    if (apiModel == null) {
      apiModel = new FinancialExchangeRateModel();
    }
    return (FinancialExchangeRateModel)apiModel;
  }

  public FinancialExchangeRateRequest currencyList(List<String> currencyList) {
    getApiModel().setCurrencyList(currencyList);
    return this;
  }

  public FinancialExchangeRateRequest beginDate(Date beginDate) {
    getApiModel().setBeginDate(beginDate);
    return this;
  }
  public FinancialExchangeRateRequest beginDate(String beginDate, TimeZoneId zoneId) {
    getApiModel().setBeginDate(DateUtils.getZoneDate(beginDate, zoneId));
    return this;
  }

  public FinancialExchangeRateRequest endDate(Date endDate) {
    getApiModel().setEndDate(endDate);
    return this;
  }
  public FinancialExchangeRateRequest endDate(String endDate, TimeZoneId zoneId) {
    getApiModel().setEndDate(DateUtils.getZoneDate(endDate, zoneId));
    return this;
  }

  @Override
  public Class<FinancialExchangeRateResponse> getResponseClass() {
    return FinancialExchangeRateResponse.class;
  }
}
