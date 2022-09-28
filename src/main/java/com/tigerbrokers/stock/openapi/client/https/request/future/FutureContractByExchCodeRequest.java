package com.tigerbrokers.stock.openapi.client.https.request.future;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureContractByExchCodeModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureBatchContractResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

/**
 * Description:
 * Created by lijiawen on 2018/12/20.
 */
public class FutureContractByExchCodeRequest extends TigerCommonRequest
    implements TigerRequest<FutureBatchContractResponse> {

  public FutureContractByExchCodeRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.FUTURE_CONTRACT_BY_EXCHANGE_CODE);
  }

  public static FutureContractByExchCodeRequest newRequest(String exchangeCode) {
    return newRequest(exchangeCode, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public static FutureContractByExchCodeRequest newRequest(String exchangeCode, Language lang) {
    FutureContractByExchCodeRequest request = new FutureContractByExchCodeRequest();
    FutureContractByExchCodeModel model = new FutureContractByExchCodeModel(exchangeCode, lang);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<FutureBatchContractResponse> getResponseClass() {
    return FutureBatchContractResponse.class;
  }
}
