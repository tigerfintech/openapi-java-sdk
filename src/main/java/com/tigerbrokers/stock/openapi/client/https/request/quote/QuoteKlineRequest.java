package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteKlineModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteKlineResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.KType;
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
    setApiMethodName(ApiServiceType.KLINE);
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
    return newRequest(symbols, kType, beginTime, endTime, TimeZoneId.NewYork);
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

  public QuoteKlineRequest withLimit(int limit) {
    if (limit > 0) {
      if (apiModel instanceof QuoteKlineModel) {
        QuoteKlineModel klineModel = (QuoteKlineModel) apiModel;
        klineModel.setLimit(limit);
      }
    }
    return this;
  }

  public QuoteKlineRequest withRight(RightOption rightOption) {
    if (rightOption != null) {
      if (apiModel instanceof QuoteKlineModel) {
        QuoteKlineModel klineModel = (QuoteKlineModel) apiModel;
        klineModel.setRight(rightOption);
      }
    }
    return this;
  }

  /**
   * set pageToken，only for single symbol
   * @param pageToken
   */
  public void withPageToken(String pageToken) {
    if (apiModel != null && apiModel instanceof QuoteKlineModel) {
      QuoteKlineModel klineModel = (QuoteKlineModel) apiModel;
      klineModel.setPageToken(pageToken);
    }
  }

  @Override
  public Class<QuoteKlineResponse> getResponseClass() {
    return QuoteKlineResponse.class;
  }
}
