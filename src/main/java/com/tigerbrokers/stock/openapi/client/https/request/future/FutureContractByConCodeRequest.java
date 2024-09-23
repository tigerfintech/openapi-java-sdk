package com.tigerbrokers.stock.openapi.client.https.request.future;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureContractByConCodeModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureContractResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

/**
 * Description:
 * Created by lijiawen on 2018/12/20.
 */
public class FutureContractByConCodeRequest extends TigerCommonRequest implements TigerRequest<FutureContractResponse> {

  public FutureContractByConCodeRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.FUTURE_CONTRACT_BY_CONTRACT_CODE);
  }

  public static FutureContractByConCodeRequest newRequest(String contractCode) {
    return newRequest(contractCode, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public static FutureContractByConCodeRequest newRequest(String contractCode, Language lang) {
    FutureContractByConCodeRequest request = new FutureContractByConCodeRequest();
    FutureContractByConCodeModel model = new FutureContractByConCodeModel(contractCode, lang);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<FutureContractResponse> getResponseClass() {
    return FutureContractResponse.class;
  }
}
