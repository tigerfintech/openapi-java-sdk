package com.tigerbrokers.stock.openapi.client.https.request.contract;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.model.ContractsModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.contract.ContractsResponse;

/**
 * Description:
 * Created by lijiawen on 2019/06/26.
 */
public class ContractsRequest extends TigerCommonRequest implements TigerRequest<ContractsResponse> {

  public ContractsRequest() {
    setApiMethodName(ApiServiceType.CONTRACTS);
  }

  public static ContractsRequest newRequest(ContractsModel contractsModel) {
    ContractsRequest request = new ContractsRequest();
    request.setApiModel(contractsModel);
    return request;
  }

  @Override
  public Class<ContractsResponse> getResponseClass() {
    return ContractsResponse.class;
  }
}
