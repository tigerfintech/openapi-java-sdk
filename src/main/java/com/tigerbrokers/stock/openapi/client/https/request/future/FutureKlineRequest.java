package com.tigerbrokers.stock.openapi.client.https.request.future;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureKlineModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureKlineResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.FutureKType;

/**
 * Description:
 * Created by lijiawen on 2018/12/20.
 */
public class FutureKlineRequest extends TigerCommonRequest implements TigerRequest<FutureKlineResponse> {

  public FutureKlineRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(ApiServiceType.FUTURE_KLINE);
  }

  public static FutureKlineRequest newRequest(String contractCode) {
    return newRequest(contractCode, FutureKType.day, null, null);
  }

  public static FutureKlineRequest newRequest(String contractCode, FutureKType kType) {
    return newRequest(contractCode, kType, null, null);
  }

  public static FutureKlineRequest newRequest(String contractCode, FutureKType kType, Long beginTime, Long endTime) {
    FutureKlineRequest request = new FutureKlineRequest();
    FutureKlineModel model = new FutureKlineModel(contractCode, kType, beginTime, endTime);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<FutureKlineResponse> getResponseClass() {
    return FutureKlineResponse.class;
  }
}
