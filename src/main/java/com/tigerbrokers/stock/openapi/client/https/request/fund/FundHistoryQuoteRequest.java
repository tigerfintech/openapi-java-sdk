package com.tigerbrokers.stock.openapi.client.https.request.fund;

import com.tigerbrokers.stock.openapi.client.https.domain.fund.model.FundQuoteHistoryModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.fund.FundHistoryQuoteResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import java.util.List;

/**
 * Description:
 * Created by bean on 2023/07/25.
 */
public class FundHistoryQuoteRequest extends TigerCommonRequest implements TigerRequest<FundHistoryQuoteResponse> {

  public FundHistoryQuoteRequest() {
    setApiMethodName(MethodName.FUND_HISTORY_QUOTE);
  }

  public static FundHistoryQuoteRequest newRequest(List<String> symbols) {
    return newRequest(symbols, null, null);
  }

  public static FundHistoryQuoteRequest newRequest(List<String> symbols, Long beginTime, Long endTime) {
    FundHistoryQuoteRequest request = new FundHistoryQuoteRequest();
    FundQuoteHistoryModel model = new FundQuoteHistoryModel(symbols, beginTime, endTime);
    request.setApiModel(model);
    return request;
  }

  public static FundHistoryQuoteRequest newRequest(List<String> symbols, String beginTime, String endTime,
      TimeZoneId zoneId) {
    FundHistoryQuoteRequest request = new FundHistoryQuoteRequest();
    FundQuoteHistoryModel model = new FundQuoteHistoryModel(symbols, beginTime, endTime, zoneId);
    request.setApiModel(model);
    return request;
  }

  public FundQuoteHistoryModel getApiModel() {
    if (apiModel == null) {
      apiModel = new FundQuoteHistoryModel(null);
    }
    return (FundQuoteHistoryModel)apiModel;
  }

  public FundHistoryQuoteRequest symbols(List<String> symbols) {
    getApiModel().setSymbols(symbols);
    return this;
  }

  public FundHistoryQuoteRequest beginTime(long beginTime) {
    getApiModel().setBeginTime(beginTime);
    return this;
  }

  public FundHistoryQuoteRequest endTime(long endTime) {
    getApiModel().setEndTime(endTime);
    return this;
  }

  public FundHistoryQuoteRequest limit(int limit) {
    getApiModel().setLimit(limit);
    return this;
  }

  public FundHistoryQuoteRequest lang(Language lang) {
    getApiModel().setLang(lang);
    return this;
  }

  @Override
  public Class<FundHistoryQuoteResponse> getResponseClass() {
    return FundHistoryQuoteResponse.class;
  }
}
