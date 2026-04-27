package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.PositionTransferModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.PositionTransferModel.Transfer;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.PositionTransferResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import java.util.List;

public class PositionTransferRequest extends TigerCommonRequest implements
    TigerRequest<PositionTransferResponse> {

  private PositionTransferRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.POSITION_TRANSFER);
  }

  public static PositionTransferRequest buildRequest(String fromAccount,
      String toAccount,
      String market,
      List<Transfer> transfers) {
    PositionTransferModel model = new PositionTransferModel(fromAccount, toAccount,
        transfers, market);
    PositionTransferRequest request = new PositionTransferRequest();
    request.setApiModel(model);
    return request;
  }

  public PositionTransferRequest setSecretKey(String secretKey) {
    PositionTransferModel model = (PositionTransferModel) getApiModel();
    model.setSecretKey(secretKey);
    return this;
  }

  @Override
  public Class<PositionTransferResponse> getResponseClass() {
    return PositionTransferResponse.class;
  }
}
