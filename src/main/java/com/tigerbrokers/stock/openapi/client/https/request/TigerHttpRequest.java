package com.tigerbrokers.stock.openapi.client.https.request;

import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.response.TigerHttpResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.DATE_TIME_FORMAT;

public class TigerHttpRequest implements TigerRequest<TigerHttpResponse> {

  private String apiVersion = TigerApiConstants.VERSION_CODE;
  private String tigerId;
  private String signType;
  private String apiMethodName;
  private String bizContent;
  private String notifyUrl;
  private String charset;
  private String timestamp;
  private String sign;
  private Map<String, String> textParams;

  public TigerHttpRequest(String apiMethodName) {
    this.apiMethodName = apiMethodName;
    timestamp = new SimpleDateFormat(DATE_TIME_FORMAT).format(new Date());
  }

  public TigerHttpRequest(String apiMethodName, String notifyUrl) {
    this.apiMethodName = apiMethodName;
    this.notifyUrl = notifyUrl;
    timestamp = new SimpleDateFormat(DATE_TIME_FORMAT).format(new Date());
  }

  public TigerHttpRequest(String apiMethodName, Map<String, String> textParams, String notifyUrl) {
    this.apiMethodName = apiMethodName;
    this.textParams = textParams;
    this.notifyUrl = notifyUrl;
    timestamp = new SimpleDateFormat(DATE_TIME_FORMAT).format(new Date());
  }

  public String getApiVersion() {
    return apiVersion;
  }

  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }

  public String getApiMethodName() {
    return apiMethodName;
  }

  public void setApiMethodName(String apiMethodName) {
    this.apiMethodName = apiMethodName;
  }

  public Map<String, String> getTextParams() {
    return textParams;
  }

  public void setTextParams(Map<String, String> textParams) {
    this.textParams = textParams;
  }

  public String getNotifyUrl() {
    return this.notifyUrl;
  }

  public void setNotifyUrl(String notifyUrl) {
    this.notifyUrl = notifyUrl;
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
