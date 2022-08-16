package com.tigerbrokers.stock.openapi.client.https.request.future;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureTickModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureTickResponse;

/**
 * Description:
 * Created by lijiawen on 2018/12/20.
 */
public class FutureTickRequest extends TigerCommonRequest implements TigerRequest<FutureTickResponse> {

  public FutureTickRequest() {
    setApiVersion(V3_0);
    setApiMethodName(ApiServiceType.FUTURE_TICK);
  }

  public static FutureTickRequest newRequest(String contractCode) {
    return newRequest(contractCode, -1L, -1L);
  }

  public static FutureTickRequest newRequest(String contractCode, long beginIndex, long endIndex) {
    FutureTickRequest request = new FutureTickRequest();
    FutureTickModel model = new FutureTickModel(contractCode, beginIndex, endIndex);
    request.setApiModel(model);
    return request;
  }

  public static FutureTickRequest newRequest(String contractCode, long beginIndex, long endIndex, int limit) {
    FutureTickRequest request = new FutureTickRequest();
    FutureTickModel model = new FutureTickModel(contractCode, beginIndex, endIndex, limit);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<FutureTickResponse> getResponseClass() {
    return FutureTickResponse.class;
  }
}
