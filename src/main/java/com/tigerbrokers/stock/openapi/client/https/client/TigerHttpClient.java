package com.tigerbrokers.stock.openapi.client.https.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.request.TigerHttpRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;
import com.tigerbrokers.stock.openapi.client.util.HttpUtils;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import com.tigerbrokers.stock.openapi.client.util.TigerSignature;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.BIZ_CONTENT;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.CHARSET;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.METHOD;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.SIGN;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.SIGN_TYPE;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.TIGER_ID;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.TIMESTAMP;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.VERSION;

/**
 * HTTP客户端
 */
public class TigerHttpClient implements TigerClient {

  private static final Logger logger = LoggerFactory.getLogger(TigerHttpClient.class);

  private String serverUrl;
  private String tigerId;
  private String privateKey;
  private String tigerPublicKey;

  private static final String ONLINE_PUBLIC_KEY =
      "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDNF3G8SoEcCZh2rshUbayDgLLrj6rKgzNMxDL2HSnKcB0+GPOsndqSv+a4IBu9+I3fyBp5hkyMMG2+AXugd9pMpy6VxJxlNjhX1MYbNTZJUT4nudki4uh+LMOkIBHOceGNXjgB+cXqmlUnjlqha/HgboeHSnSgpM3dKSJQlIOsDwIDAQAB";
  private String signType = TigerApiConstants.SIGN_TYPE_RSA;
  private String charset = TigerApiConstants.CHARSET_UTF8;

  static {
    Security.setProperty("jdk.certpath.disabledAlgorithms", "");
  }

  public TigerHttpClient(String serverUrl, String tigerId, String privateKey) {
    this(serverUrl, tigerId, privateKey, ONLINE_PUBLIC_KEY);
  }

  public TigerHttpClient(String serverUrl, String tigerId, String privateKey, String tigerPublicKey) {
    this.serverUrl = serverUrl;
    this.tigerId = tigerId;
    this.privateKey = privateKey;
    this.tigerPublicKey = tigerPublicKey;
  }

  public <T extends TigerResponse> T execute(TigerRequest<T> request) {
    T response;
    String data = null;
    try {
      data = HttpUtils.post(serverUrl, JSONObject.toJSONString(buildParams(request)));

      if (StringUtils.isEmpty(data)) {
        return null;
      }
      response = JSON.parseObject(data, request.getResponseClass());

      if (StringUtils.isEmpty(this.tigerPublicKey) || response.getSign() == null) {
        return response;
      }
      boolean signSuccess =
          TigerSignature.rsaCheckContent(request.getTimestamp(), response.getSign(), this.tigerPublicKey, this.charset);

      if (!signSuccess) {
        throw new TigerApiException(TigerApiCode.SIGN_CHECK_FAILED);
      }
      return response;
    } catch (RuntimeException e) {
      logger.error("client execute runtime exception,request:{},response:{},error:{}", request, data, e.getMessage());
      return errorResponse(request, e);
    } catch (TigerApiException e) {
      logger.error("client execute api exception,request:{},response:{},error:{}", request, data, e.getMessage(), e);
      return errorResponse(request, e);
    } catch (Exception e) {
      logger.error("client execute exception,request:{},response:{},error:{}", request, data, e.getMessage(), e);
      return errorResponse(request, e);
    }
  }

  private <T extends TigerResponse> T errorResponse(TigerRequest<T> request, TigerApiException e) {
    T response;
    try {
      response = request.getResponseClass().newInstance();
      response.setCode(e.getErrCode());
      response.setMessage(e.getErrMsg());
      return response;
    } catch (InstantiationException e1) {
      logger.error("instantiationException:", e1.getMessage(), e1);
    } catch (IllegalAccessException e1) {
      logger.error("illegalAccessException:", e1.getMessage(), e1);
    }
    return null;
  }

  private <T extends TigerResponse> T errorResponse(TigerRequest<T> request, Exception e) {
    T response;
    try {
      response = request.getResponseClass().newInstance();
      response.setCode(TigerApiCode.CLIENT_API_ERROR.getCode());
      response.setMessage(TigerApiCode.CLIENT_API_ERROR.getMessage() + "(" + e.getMessage() + ")");
      return response;
    } catch (InstantiationException e1) {
      logger.error("instantiationException:", e1.getMessage(), e1);
    } catch (IllegalAccessException e1) {
      logger.error("illegalAccessException:", e1.getMessage(), e1);
    }
    return null;
  }

  private Map<String, Object> buildParams(TigerRequest request) {
    Map params = new HashMap<>();
    params.put(METHOD, request.getApiMethodName());
    params.put(VERSION, request.getApiVersion());
    if (request instanceof TigerHttpRequest) {
      params.put(BIZ_CONTENT, ((TigerHttpRequest) request).getBizContent());
    } else {
      params.put(BIZ_CONTENT, JSONObject.toJSONString(request.getApiModel()));
    }
    params.put(TIMESTAMP, request.getTimestamp());
    params.put(CHARSET, this.charset);
    params.put(TIGER_ID, this.tigerId);
    params.put(SIGN_TYPE, this.signType);

    String signContent = TigerSignature.getSignContent(params);
    String sign = TigerSignature.rsaSign(signContent, privateKey, charset);
    params.put(SIGN, sign);
    return params;
  }
}
