package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.PositionsResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import com.tigerbrokers.stock.openapi.client.util.DateUtils;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class PositionsRequest extends TigerCommonRequest implements TigerRequest<PositionsResponse> {

  public PositionsRequest() {
    super.apiMethodName = MethodName.POSITIONS;
    DateTimeFormatter dtf = DateUtils.DATETIME_FORMAT;
    timestamp = dtf.format(LocalDateTime.now(ZoneId.of(TimeZoneId.Shanghai.getZoneId())));
  }

  @Override
  public Class<PositionsResponse> getResponseClass() {
    return PositionsResponse.class;
  }

  @Override
  public void setApiModel(ApiModel apiModel) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ApiModel getApiModel() {
    return null;
  }
}
