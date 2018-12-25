package com.tigerbrokers.stock.openapi.client.https.request.future;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureTradingDateModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureTradingDateResponse;

/**
 * Description:
 * Created by lijiawen on 2018/12/20.
 */
public class FutureTradingDateRequest extends TigerCommonRequest implements TigerRequest<FutureTradingDateResponse> {

  public FutureTradingDateRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(ApiServiceType.FUTURE_TRADING_DATE);
  }

  public static FutureTradingDateRequest newRequest(String contractCode) {
    return newRequest(contractCode, null);
  }

  public static FutureTradingDateRequest newRequest(String contractCode, Long tradingDate) {
    FutureTradingDateRequest request = new FutureTradingDateRequest();
    FutureTradingDateModel model = new FutureTradingDateModel(contractCode, tradingDate);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<FutureTradingDateResponse> getResponseClass() {
    return FutureTradingDateResponse.class;
  }
}
