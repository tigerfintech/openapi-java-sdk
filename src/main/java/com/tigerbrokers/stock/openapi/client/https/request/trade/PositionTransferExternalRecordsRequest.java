package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.PositionTransferRecordsModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.PositionTransferExternalRecordsResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

public class PositionTransferExternalRecordsRequest extends TigerCommonRequest implements
    TigerRequest<PositionTransferExternalRecordsResponse> {

  private PositionTransferExternalRecordsRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.POSITION_TRANSFER_EXTERNAL_RECORDS);
  }

  public static PositionTransferExternalRecordsRequest buildRequest(String accountId,
      String sinceDate, String toDate) {
    return buildRequest(accountId, sinceDate, toDate, null, null, null);
  }

  public static PositionTransferExternalRecordsRequest buildRequest(String accountId,
      String sinceDate, String toDate, String status, String market, String symbol) {
    PositionTransferExternalRecordsRequest request = new PositionTransferExternalRecordsRequest();
    request.setApiModel(
        new PositionTransferRecordsModel(accountId, sinceDate, toDate, status, market, symbol));
    return request;
  }

  public PositionTransferExternalRecordsRequest setSecretKey(String secretKey) {
    PositionTransferRecordsModel model = (PositionTransferRecordsModel) getApiModel();
    model.setSecretKey(secretKey);
    return this;
  }

  @Override
  public Class<PositionTransferExternalRecordsResponse> getResponseClass() {
    return PositionTransferExternalRecordsResponse.class;
  }
}
