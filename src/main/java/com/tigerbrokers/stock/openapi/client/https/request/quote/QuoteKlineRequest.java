package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteKlineModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteKlineResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.KType;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.RightOption;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteKlineRequest extends TigerCommonRequest implements TigerRequest<QuoteKlineResponse> {

  public QuoteKlineRequest() {
    setApiVersion(V2_0);
    setApiMethodName(MethodName.KLINE);
  }

  public static QuoteKlineRequest newRequest(List<String> symbols, KType kType) {
    QuoteKlineRequest request = new QuoteKlineRequest();
    QuoteKlineModel model =
        new QuoteKlineModel(symbols, getKType(kType), -1L, -1L);
    request.setApiModel(model);
    return request;
  }

  public static QuoteKlineRequest newRequest(List<String> symbols, KType kType, Long beginTime, Long endTime) {
    QuoteKlineRequest request = new QuoteKlineRequest();
    QuoteKlineModel model = new QuoteKlineModel(symbols, getKType(kType), beginTime, endTime);
    request.setApiModel(model);
    return request;
  }

  public static QuoteKlineRequest newRequest(List<String> symbols, KType kType, String beginTime, String endTime) {
    return newRequest(symbols, kType, beginTime, endTime, ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone());
  }

  public static QuoteKlineRequest newRequest(List<String> symbols, KType kType, String beginTime, String endTime,
      TimeZoneId zoneId) {
    QuoteKlineRequest request = new QuoteKlineRequest();
    QuoteKlineModel model = new QuoteKlineModel(symbols, getKType(kType), beginTime, endTime, zoneId);
    request.setApiModel(model);
    return request;
  }

  private static String getKType(KType kType) {
    return kType != null ? kType.getValue() : KType.day.getValue();
  }

  public QuoteKlineModel getApiModel() {
    if (apiModel == null) {
      apiModel = new QuoteKlineModel();
    }
    return (QuoteKlineModel)apiModel;
  }

  public QuoteKlineRequest withLimit(int limit) {
    if (limit > 0) {
      getApiModel().setLimit(limit);
    }
    return this;
  }

  public QuoteKlineRequest withRight(RightOption rightOption) {
    if (rightOption != null) {
      getApiModel().setRight(rightOption);
    }
    return this;
  }

  public QuoteKlineRequest withRight(String rightOption) {
    if (rightOption != null) {
      getApiModel().setRight(rightOption);
    }
    return this;
  }

  public QuoteKlineRequest withRight(Boolean isHourTrading) {
    getApiModel().setHourTrading(isHourTrading);
    return this;
  }

  /**
   * set pageToken，only for single symbol
   * @param pageToken
   */
  public void withPageToken(String pageToken) {
    getApiModel().setPageToken(pageToken);
  }

  @Override
  public Class<QuoteKlineResponse> getResponseClass() {
    return QuoteKlineResponse.class;
  }
}
