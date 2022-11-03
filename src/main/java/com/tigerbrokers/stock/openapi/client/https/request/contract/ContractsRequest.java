package com.tigerbrokers.stock.openapi.client.https.request.contract;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.model.ContractsModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.contract.ContractsResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;

/**
 * Description:
 * Created by lijiawen on 2019/06/26.
 */
public class ContractsRequest extends TigerCommonRequest implements TigerRequest<ContractsResponse> {

  public ContractsRequest() {
    setApiMethodName(MethodName.CONTRACTS);
  }

  public static ContractsRequest newRequest(ContractsModel contractsModel) {
    ContractsRequest request = new ContractsRequest();
    if (StringUtils.isEmpty(contractsModel.getAccount())) {
      contractsModel.setAccount(ClientConfig.DEFAULT_CONFIG.defaultAccount);
    }
    request.setApiModel(contractsModel);
    return request;
  }

  public static ContractsRequest newRequest(ContractsModel contractsModel, String account) {
    ContractsRequest request = new ContractsRequest();
    contractsModel.setAccount(account);
    request.setApiModel(contractsModel);
    return request;
  }

  @Override
  public Class<ContractsResponse> getResponseClass() {
    return ContractsResponse.class;
  }
}
