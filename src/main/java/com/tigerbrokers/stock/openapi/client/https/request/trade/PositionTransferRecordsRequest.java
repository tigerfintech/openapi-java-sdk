package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.PositionTransferRecordsModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.PositionTransferRecordsResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

public class PositionTransferRecordsRequest extends TigerCommonRequest implements
    TigerRequest<PositionTransferRecordsResponse> {

  private PositionTransferRecordsRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.POSITION_TRANSFER_RECORDS);
  }

  public static PositionTransferRecordsRequest buildRequest(String accountId, String sinceDate,
      String toDate) {
    return buildRequest(accountId, sinceDate, toDate, null, null, null);
  }

  public static PositionTransferRecordsRequest buildRequest(String accountId, String sinceDate,
      String toDate, String status, String market, String symbol) {
    PositionTransferRecordsRequest request = new PositionTransferRecordsRequest();
    request.setApiModel(
        new PositionTransferRecordsModel(accountId, sinceDate, toDate, status, market, symbol));
    return request;
  }

  public PositionTransferRecordsRequest setSecretKey(String secretKey) {
    PositionTransferRecordsModel model = (PositionTransferRecordsModel) getApiModel();
    model.setSecretKey(secretKey);
    return this;
  }

  @Override
  public Class<PositionTransferRecordsResponse> getResponseClass() {
    return PositionTransferRecordsResponse.class;
  }
}
