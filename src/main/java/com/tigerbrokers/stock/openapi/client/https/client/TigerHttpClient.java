package com.tigerbrokers.stock.openapi.client.https.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.BatchApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.model.ContractModel;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.model.ContractsModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.TradeOrderModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerHttpRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.TigerHttpResponse;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import com.tigerbrokers.stock.openapi.client.https.response.contract.ContractResponse;
import com.tigerbrokers.stock.openapi.client.https.validator.ContractRequestValidator;
import com.tigerbrokers.stock.openapi.client.https.validator.PlaceOrderRequestValidator;
import com.tigerbrokers.stock.openapi.client.https.validator.RequestValidator;
import com.tigerbrokers.stock.openapi.client.struct.enums.AccountType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Env;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.FastJsonPropertyFilter;
import com.tigerbrokers.stock.openapi.client.util.HttpUtils;
import com.tigerbrokers.stock.openapi.client.util.NetworkUtil;
import com.tigerbrokers.stock.openapi.client.util.SdkVersionUtils;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import com.tigerbrokers.stock.openapi.client.util.TigerSignature;
import com.tigerbrokers.stock.openapi.client.util.builder.AccountParamBuilder;
import java.security.Security;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.tigerbrokers.stock.openapi.client.constant.ApiServiceType.CONTRACT;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.ACCESS_TOKEN;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.ACCOUNT_TYPE;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.BIZ_CONTENT;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.CHARSET;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.DEVICE_ID;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.METHOD;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.SDK_VERSION;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.SIGN;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.SIGN_TYPE;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.TIGER_ID;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.TIMESTAMP;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.TRADE_TOKEN;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.VERSION;
import static com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest.V2_0;

public class TigerHttpClient implements TigerClient {

  private String serverUrl;
  private String tigerId;
  private String privateKey;
  private String tigerPublicKey;
  private String accessToken;
  private String tradeToken;
  private String accountType;
  private String deviceId;
  private Map<Class<? extends ApiModel>, RequestValidator> validatorMap = new HashMap<>();

  private static final String ONLINE_PUBLIC_KEY =
      "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDNF3G8SoEcCZh2rshUbayDgLLrj6rKgzNMxDL2HSnKcB0+GPOsndqSv+a4IBu9+I3fyBp5hkyMMG2+AXugd9pMpy6VxJxlNjhX1MYbNTZJUT4nudki4uh+LMOkIBHOceGNXjgB+cXqmlUnjlqha/HgboeHSnSgpM3dKSJQlIOsDwIDAQAB";

  private static final String SANDBOX_PUBLIC_KEY =
      "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbm21i11hgAENGd3/f280PSe4g9YGkS3TEXBYMidihTvHHf+tJ0PYD0o3PruI0hl3qhEjHTAxb75T5YD3SGK4IBhHn/Rk6mhqlGgI+bBrBVYaXixmHfRo75RpUUuWACyeqQkZckgR0McxuW9xRMIa2cXZOoL1E4SL4lXKGhKoWbwIDAQAB";

  private String signType = TigerApiConstants.SIGN_TYPE_RSA;
  private String charset = TigerApiConstants.CHARSET_UTF8;

  private static final long REFRESH_URL_INTERVAL_SECONDS = 300;
  private ScheduledThreadPoolExecutor domainExecutorService;

  static {
    Security.setProperty("jdk.certpath.disabledAlgorithms", "");
  }

  private TigerHttpClient() {
  }

  private static class SingletonInner {
    private static TigerHttpClient singleton = new TigerHttpClient();
  }

  /**
   * get TigerHttpClient instance
   * @return TigerHttpClient
   */
  public static TigerHttpClient getInstance() {
    return TigerHttpClient.SingletonInner.singleton;
  }

  public TigerHttpClient clientConfig(ClientConfig clientConfig) {
    init(clientConfig.serverUrl, clientConfig.tigerId, clientConfig.privateKey);
    initDomainCheck();
    if (clientConfig.isAutoGrabPermission) {
      TigerHttpRequest request = new TigerHttpRequest(ApiServiceType.GRAB_QUOTE_PERMISSION);
      request.setBizContent(AccountParamBuilder.instance().buildJson());
      TigerHttpResponse response = execute(request);
      ApiLogger.info("tigerId:{}, grab_quote_permission:{}, data:{}",
          tigerId, response.getMessage(), response.getData());
    }
    return this;
  }

  /** please use TigerHttpClient.getInstance().clientConfig(ClientConfig.DEFAULT_CONFIG) */
  @Deprecated
  public TigerHttpClient(String serverUrl, String tigerId, String privateKey) {
    init(serverUrl, tigerId, privateKey);
  }

  private void init(String serverUrl, String tigerId, String privateKey) {
    if (StringUtils.isEmpty(serverUrl)) {
      serverUrl = NetworkUtil.getHttpServerAddress(null);
    }
    if (serverUrl == null) {
      throw new RuntimeException("serverUrl is empty.");
    }
    if (tigerId == null) {
      throw new RuntimeException("tigerId is empty.");
    }
    if (privateKey == null) {
      throw new RuntimeException("privateKey is empty.");
    }
    this.serverUrl = serverUrl;
    this.tigerId = tigerId;
    this.privateKey = privateKey;
    if (ClientConfig.DEFAULT_CONFIG.getEnv() == Env.PROD) {
      this.tigerPublicKey = ONLINE_PUBLIC_KEY;
    } else {
      this.tigerPublicKey = SANDBOX_PUBLIC_KEY;
    }
    this.deviceId = NetworkUtil.getDeviceId();
    initValidator();
  }
  private void initValidator() {
    ContractRequestValidator contractRequestValidator = new ContractRequestValidator();
    validatorMap.put(ContractModel.class, contractRequestValidator);
    validatorMap.put(ContractsModel.class, contractRequestValidator);
    validatorMap.put(TradeOrderModel.class, new PlaceOrderRequestValidator());
  }

  @Deprecated
  public TigerHttpClient(String serverUrl) {
    this.serverUrl = serverUrl;
  }

  @Deprecated
  public TigerHttpClient(String serverUrl, String accessToken) {
    this.serverUrl = serverUrl;
    this.accessToken = accessToken;
  }

  private void initDomainCheck() {
    synchronized (TigerHttpClient.SingletonInner.singleton) {
      if (domainExecutorService == null || domainExecutorService.isTerminated()) {
        Runnable domainChecker = () -> {
          try {
            String newServerUrl = NetworkUtil.getHttpServerAddress(this.serverUrl);
            if (!newServerUrl.equals(this.serverUrl)) {
              ApiLogger.info("server url changed. {}-->{}", this.serverUrl, newServerUrl);
            }
            this.serverUrl = newServerUrl;
          } catch (Throwable t) {
            ApiLogger.error("refresh serverUrl error", t);
          }
        };
        domainExecutorService = new ScheduledThreadPoolExecutor(1);
        domainExecutorService.scheduleWithFixedDelay(domainChecker, REFRESH_URL_INTERVAL_SECONDS,
            REFRESH_URL_INTERVAL_SECONDS, TimeUnit.SECONDS);
      }
    }
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public void setTradeToken(String tradeToken) {
    this.tradeToken = tradeToken;
  }

  public String getTradeToken() {
    return tradeToken;
  }

  public void setAccountType(AccountType accountType) {
    if (accountType != null) {
      this.accountType = accountType.name();
    }
  }

  public String getAccountType() {
    return accountType;
  }

  @Override
  public <T extends TigerResponse> T execute(TigerRequest<T> request) {
    T response;
    String param = null;
    String data = null;
    try {
      validate(request);
      param = JSONObject.toJSONString(buildParams(request));
      ApiLogger.debug("request param:{}", param);

      data = HttpUtils.post(serverUrl, param);

      if (StringUtils.isEmpty(data)) {
        throw new TigerApiException(TigerApiCode.EMPTY_DATA_ERROR);
      }
      response = JSON.parseObject(data, request.getResponseClass());
      if (CONTRACT.equals(request.getApiMethodName())) {
        convertContractItem(response, request.getApiVersion());
      }
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
      ApiLogger.error(tigerId, request.getApiMethodName(), request.getApiVersion(), param, data, e);
      return errorResponse(tigerId, request, e);
    } catch (TigerApiException e) {
      ApiLogger.error(tigerId, request.getApiMethodName(), request.getApiVersion(), param, data, e);
      return errorResponse(tigerId, request, e);
    } catch (Exception e) {
      ApiLogger.error(tigerId, request.getApiMethodName(), request.getApiVersion(), param, data, e);
      return errorResponse(tigerId, request, e);
    }
  }

  private void convertContractItem(TigerResponse response, String apiVersion) {
    if (response instanceof ContractResponse) {
      ContractResponse contractResponse = (ContractResponse) response;
      if (StringUtils.isEmpty(contractResponse.getData())) {
        return;
      }
      if (V2_0.equals(apiVersion)) {
        List<ContractItem> items = ContractItem.convertFromJsonV2(contractResponse.getData());
        contractResponse.setItems(items);
        if (items != null && items.size() > 0) {
          contractResponse.setItem(items.get(0));
        }
      } else {
        contractResponse.setItem(ContractItem.convertFromJson(contractResponse.getData()));
      }
    }
  }

  private <T extends TigerResponse> T errorResponse(String tigerId, TigerRequest<T> request, TigerApiException e) {
    try {
      ApiLogger.error(tigerId, request.getApiMethodName(), request.getApiVersion(), e);

      T response = request.getResponseClass().newInstance();
      response.setCode(e.getErrCode());
      response.setMessage(e.getErrMsg());
      return response;
    } catch (Exception e1) {
      ApiLogger.error(tigerId, request.getApiMethodName(), request.getApiVersion(), e1);
      return null;
    }
  }

  private <T extends TigerResponse> T errorResponse(String tigerId, TigerRequest<T> request, Exception e) {
    try {
      ApiLogger.error(tigerId, request.getApiMethodName(), request.getApiVersion(), e);

      T response = request.getResponseClass().newInstance();
      response.setCode(TigerApiCode.CLIENT_API_ERROR.getCode());
      response.setMessage(TigerApiCode.CLIENT_API_ERROR.getMessage() + "(" + e.getMessage() + ")");
      return response;
    } catch (Exception e1) {
      ApiLogger.error(tigerId, request.getApiMethodName(), request.getApiVersion(), e1);
      return null;
    }
  }

  private Map<String, Object> buildParams(TigerRequest request) {
    Map<String,Object> params = new HashMap<>();
    params.put(METHOD, request.getApiMethodName());
    params.put(VERSION, request.getApiVersion());
    params.put(SDK_VERSION, SdkVersionUtils.getSdkVersion());
    if (request instanceof TigerHttpRequest) {
      params.put(BIZ_CONTENT, ((TigerHttpRequest) request).getBizContent());
    } else {
      ApiModel apiModel = request.getApiModel();
      if (apiModel instanceof BatchApiModel) {
        params.put(BIZ_CONTENT, JSONObject.toJSONString(((BatchApiModel) apiModel).getItems()));
      } else if (apiModel instanceof TradeOrderModel) {
        params.put(BIZ_CONTENT, JSONObject.toJSONString(apiModel, FastJsonPropertyFilter.getPropertyFilter()));
      } else {
        params.put(BIZ_CONTENT, JSONObject.toJSONString(apiModel));
      }
    }
    params.put(TIMESTAMP, request.getTimestamp());
    params.put(CHARSET, this.charset);
    params.put(TIGER_ID, this.tigerId);
    params.put(SIGN_TYPE, this.signType);
    if (this.accessToken != null) {
      params.put(ACCESS_TOKEN, this.accessToken);
    }
    if (this.tradeToken != null) {
      params.put(TRADE_TOKEN, this.tradeToken);
    }
    if (this.accountType != null) {
      params.put(ACCOUNT_TYPE, this.accountType);
    }
    if (this.deviceId != null) {
      params.put(DEVICE_ID, this.deviceId);
    }
    if (this.tigerId != null) {
      String content = TigerSignature.getSignContent(params);
      params.put(SIGN, TigerSignature.rsaSign(content, privateKey, charset));
    }

    return params;
  }

  /**
   * validate parameters
   * @param request
   * @throws TigerApiException
   */
  private void validate(TigerRequest request) throws TigerApiException {
    if (request instanceof TigerHttpRequest) {
      return;
    }
    // TigerCommonRequest
    ApiModel apiModel = request.getApiModel();
    RequestValidator validator = validatorMap.get(apiModel.getClass());
    if (validator == null) {
      return;
    }
    validator.validate(apiModel);
  }
}
