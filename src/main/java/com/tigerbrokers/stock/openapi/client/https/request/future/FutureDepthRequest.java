package com.tigerbrokers.stock.openapi.client.https.request.future;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureDepthModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureDepthResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import java.util.List;

public class FutureDepthRequest extends TigerCommonRequest
    implements TigerRequest<FutureDepthResponse> {

  public FutureDepthRequest() {
    setApiMethodName(MethodName.FUTURE_DEPTH);
  }

  public static FutureDepthRequest newRequest(List<String> contractCodes) {
    return newRequest(contractCodes, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public static FutureDepthRequest newRequest(List<String> contractCodes, Language lang) {
    FutureDepthRequest request = new FutureDepthRequest();
    FutureDepthModel model = new FutureDepthModel(contractCodes, lang);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<FutureDepthResponse> getResponseClass() {
    return FutureDepthResponse.class;
  }
}
