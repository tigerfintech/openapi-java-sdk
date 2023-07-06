package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.BatchOrderResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import com.tigerbrokers.stock.openapi.client.util.DateUtils;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class QueryOrderRequest extends TigerCommonRequest implements TigerRequest<BatchOrderResponse> {

  public QueryOrderRequest() {
    this(MethodName.ORDERS);
  }

  public QueryOrderRequest(MethodName methodName) {
    super.apiMethodName = MethodName.ORDERS;
    DateTimeFormatter dtf = DateUtils.DATETIME_FORMAT;
    timestamp = dtf.format(LocalDateTime.now(ZoneId.of(TimeZoneId.Shanghai.getZoneId())));
  }

  @Override
  public Class<BatchOrderResponse> getResponseClass() {
    return BatchOrderResponse.class;
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
