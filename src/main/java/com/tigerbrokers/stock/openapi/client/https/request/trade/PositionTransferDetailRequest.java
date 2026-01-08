package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.PositionTransferDetailModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.PositionTransferDetailResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

public class PositionTransferDetailRequest extends TigerCommonRequest implements
    TigerRequest<PositionTransferDetailResponse> {

  private PositionTransferDetailRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.POSITION_TRANSFER_DETAIL);
  }

  public static PositionTransferDetailRequest buildRequest(Long id,
      String accountId) {
    PositionTransferDetailRequest request = new PositionTransferDetailRequest();
    request.setApiModel(
        new PositionTransferDetailModel(id, accountId));
    return request;
  }

  public PositionTransferDetailRequest setSecretKey(String secretKey) {
    PositionTransferDetailModel model = (PositionTransferDetailModel) getApiModel();
    model.setSecretKey(secretKey);
    return this;
  }

  @Override
  public Class<PositionTransferDetailResponse> getResponseClass() {
    return PositionTransferDetailResponse.class;
  }
}
