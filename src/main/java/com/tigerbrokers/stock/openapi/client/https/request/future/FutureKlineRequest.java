package com.tigerbrokers.stock.openapi.client.https.request.future;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureKlineModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureKlineResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.FutureKType;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/20.
 */
public class FutureKlineRequest extends TigerCommonRequest implements TigerRequest<FutureKlineResponse> {

  private static final long DEFAULT_TIME_RANGE = 7 * 24 * 3600 * 1000;
  private static final int DEFAULT_LIMIT = 200;

  public FutureKlineRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(ApiServiceType.FUTURE_KLINE);
  }

  public static FutureKlineRequest newRequest(List<String> contractCodes) {
    return newRequest(contractCodes, FutureKType.day, System.currentTimeMillis() - DEFAULT_TIME_RANGE,
        System.currentTimeMillis(), DEFAULT_LIMIT);
  }

  public static FutureKlineRequest newRequest(List<String> contractCodes, FutureKType kType) {
    return newRequest(contractCodes, kType, System.currentTimeMillis() - DEFAULT_TIME_RANGE, System.currentTimeMillis(),
        DEFAULT_LIMIT);
  }

  public static FutureKlineRequest newRequest(List<String> contractCodes, FutureKType kType, Integer limit) {
    return newRequest(contractCodes, kType, System.currentTimeMillis() - DEFAULT_TIME_RANGE, System.currentTimeMillis(),
        limit);
  }

  public static FutureKlineRequest newRequest(List<String> contractCodes, FutureKType kType, Long beginTime,
      Long endTime,
      Integer limit) {
    FutureKlineRequest request = new FutureKlineRequest();
    FutureKlineModel model =
        new FutureKlineModel(contractCodes, kType != null ? kType.getValue() : FutureKType.day.getValue(), beginTime,
            endTime, limit);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<FutureKlineResponse> getResponseClass() {
    return FutureKlineResponse.class;
  }
}
