package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.SingleOrderResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import com.tigerbrokers.stock.openapi.client.util.DateUtils;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class QuerySingleOrderRequest implements TigerRequest<SingleOrderResponse> {

  private String apiVersion = TigerApiConstants.DEFAULT_VERSION;
  private String tigerId;
  private String signType;
  private final MethodName apiMethodName = MethodName.ORDERS;
  private String bizContent;
  private String charset;
  private String timestamp;
  private String sign;

  public QuerySingleOrderRequest() {
    DateTimeFormatter dtf = DateUtils.DATETIME_FORMAT;
    timestamp = dtf.format(LocalDateTime.now(ZoneId.of(TimeZoneId.Shanghai.getZoneId())));
  }

  public String getApiVersion() {
    return apiVersion;
  }

  @Override
  public Class<SingleOrderResponse> getResponseClass() {
    return SingleOrderResponse.class;
  }

  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }

  public MethodName getApiMethodName() {
    return apiMethodName;
  }

  public String getTigerId() {
    return tigerId;
  }

  public void setTigerId(String tigerId) {
    this.tigerId = tigerId;
  }

  public String getSignType() {
    return signType;
  }

  public void setSignType(String signType) {
    this.signType = signType;
  }

  public String getBizContent() {
    return bizContent;
  }

  public void setBizContent(String bizContent) {
    this.bizContent = bizContent;
  }

  public String getCharset() {
    return charset;
  }

  public void setCharset(String charset) {
    this.charset = charset;
  }

  public String getTimestamp() {
    return timestamp;
  }

  @Override
  public void setApiModel(ApiModel apiModel) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ApiModel getApiModel() {
    return null;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }
}
