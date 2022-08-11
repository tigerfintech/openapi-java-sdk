package com.tigerbrokers.stock.openapi.client.https.request.future;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureContinuousContractModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureContractResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;

/**
 * Description:
 * Created by lijiawen on 2018/12/20.
 */
public class FutureContinuousContractRequest extends TigerCommonRequest
    implements TigerRequest<FutureContractResponse> {

  public FutureContinuousContractRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(ApiServiceType.FUTURE_CONTINUOUS_CONTRACTS);
  }

  public static FutureContinuousContractRequest newRequest(String type) {
    return newRequest(type, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public static FutureContinuousContractRequest newRequest(String type, Language lang) {
    FutureContinuousContractRequest request = new FutureContinuousContractRequest();
    FutureContinuousContractModel model = new FutureContinuousContractModel(type, lang);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<FutureContractResponse> getResponseClass() {
    return FutureContractResponse.class;
  }
}
