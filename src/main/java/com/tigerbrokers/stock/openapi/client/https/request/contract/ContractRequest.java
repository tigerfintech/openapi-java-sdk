package com.tigerbrokers.stock.openapi.client.https.request.contract;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.model.ContractModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.contract.ContractResponse;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;

/**
 * 作者：ltc
 * 时间：2019/05/29
 */
public class ContractRequest extends TigerCommonRequest implements TigerRequest<ContractResponse> {

  public ContractRequest() {
    setApiMethodName(ApiServiceType.CONTRACT);
    setApiVersion(V3_0);
  }

  /**
   * if account is null, use ClientConfig.DEFAULT_CONFIG.defaultAccount
   */
  public static ContractRequest newRequest(ContractModel contractModel) {
    ContractRequest request = new ContractRequest();
    if (StringUtils.isEmpty(contractModel.getAccount())) {
      contractModel.setAccount(ClientConfig.DEFAULT_CONFIG.defaultAccount);
    }
    request.setApiModel(contractModel);
    return request;
  }

  public static ContractRequest newRequest(ContractModel contractModel, String account) {
    ContractRequest request = new ContractRequest();
    contractModel.setAccount(account);
    request.setApiModel(contractModel);
    return request;
  }

  @Override
  public Class<ContractResponse> getResponseClass() {
    return ContractResponse.class;
  }
}
