package com.tigerbrokers.stock.openapi.client.https.request;

import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import com.tigerbrokers.stock.openapi.client.util.DateUtils;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Description:
 * Created by lijiawen on 2018/12/21.
 */
public class TigerCommonRequest {

  protected String apiVersion = TigerApiConstants.DEFAULT_VERSION;
  protected String tigerId;
  protected MethodName apiMethodName;
  protected String bizContent;
  protected String timestamp;
  protected ApiModel apiModel;
  protected String sign;

  public static final String V2_0 = "2.0";
  public static final String V3_0 = "3.0";

  public TigerCommonRequest() {
    DateTimeFormatter dtf = DateUtils.DATETIME_FORMAT;
    timestamp = dtf.format(LocalDateTime.now(ZoneId.of(TimeZoneId.Shanghai.getZoneId())));
  }

  public String getApiVersion() {
    return apiVersion;
  }

  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }

  public String getTigerId() {
    return tigerId;
  }

  public void setTigerId(String tigerId) {
    this.tigerId = tigerId;
  }

  public MethodName getApiMethodName() {
    return apiMethodName;
  }

  public void setApiMethodName(MethodName apiMethodName) {
    this.apiMethodName = apiMethodName;
  }

  public String getBizContent() {
    return bizContent;
  }

  public void setBizContent(String bizContent) {
    this.bizContent = bizContent;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public ApiModel getApiModel() {
    return apiModel;
  }

  public void setApiModel(ApiModel apiModel) {
    this.apiModel = apiModel;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }
}
