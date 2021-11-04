package com.tigerbrokers.stock.openapi.client.https.request.contract;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.model.ContractModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.contract.ContractResponse;

/**
 * 作者：ltc
 * 时间：2019/05/29
 */
public class ContractRequest extends TigerCommonRequest implements TigerRequest<ContractResponse> {

  public ContractRequest() {
    setApiMethodName(ApiServiceType.CONTRACT);
    setApiVersion(V3_0);
  }

  public static ContractRequest newRequest(ContractModel contractModel) {
    ContractRequest request = new ContractRequest();
    request.setApiModel(contractModel);
    return request;
  }

  @Override
  public Class<ContractResponse> getResponseClass() {
    return ContractResponse.class;
  }
}
