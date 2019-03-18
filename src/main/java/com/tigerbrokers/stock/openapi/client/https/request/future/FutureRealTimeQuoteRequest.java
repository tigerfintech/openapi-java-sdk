package com.tigerbrokers.stock.openapi.client.https.request.future;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureRealTimeQuoteModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureRealTimeQuoteResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/20.
 */
public class FutureRealTimeQuoteRequest extends TigerCommonRequest
    implements TigerRequest<FutureRealTimeQuoteResponse> {

  public FutureRealTimeQuoteRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(ApiServiceType.FUTURE_REAL_TIME_QUOTE);
  }

  public static FutureRealTimeQuoteRequest newRequest(List<String> contractCodes) {
    FutureRealTimeQuoteRequest request = new FutureRealTimeQuoteRequest();
    FutureRealTimeQuoteModel model = new FutureRealTimeQuoteModel(contractCodes);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<FutureRealTimeQuoteResponse> getResponseClass() {
    return FutureRealTimeQuoteResponse.class;
  }
}
