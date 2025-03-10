package com.tigerbrokers.stock.openapi.client.https.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.BatchApiModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerHttpRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.request.user.UserLicenseRequest;
import com.tigerbrokers.stock.openapi.client.https.response.TigerHttpResponse;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import com.tigerbrokers.stock.openapi.client.https.response.user.UserLicenseResponse;
import com.tigerbrokers.stock.openapi.client.https.validator.ValidatorManager;
import com.tigerbrokers.stock.openapi.client.struct.enums.AccountType;
import com.tigerbrokers.stock.openapi.client.struct.enums.BizType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Env;
import com.tigerbrokers.stock.openapi.client.struct.enums.License;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodType;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;
import com.tigerbrokers.stock.openapi.client.util.AccountUtil;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.ConfigFileUtil;
import com.tigerbrokers.stock.openapi.client.util.HttpUtils;
import com.tigerbrokers.stock.openapi.client.util.NetworkUtil;
import com.tigerbrokers.stock.openapi.client.util.ReflectionUtil;
import com.tigerbrokers.stock.openapi.client.util.SdkVersionUtils;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import com.tigerbrokers.stock.openapi.client.util.TigerSignature;
import com.tigerbrokers.stock.openapi.client.util.builder.AccountParamBuilder;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

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

public class TigerHttpClient implements TigerClient {

  private ClientConfig clientConfig;
  private TokenManager tokenManager;
  private String serverUrl;
  private String quoteServerUrl;
  private String paperServerUrl;
  private String tigerId;
  private String privateKey;
  private String tigerPublicKey;
  private String accessToken;
  private String tradeToken;
  private String accountType;
  private String deviceId;
  private int failRetryCounts = TigerApiConstants.DEFAULT_FAIL_RETRY_COUNT;

  private static final String ONLINE_PUBLIC_KEY =
      "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDNF3G8SoEcCZh2rshUbayDgLLrj6rKgzNMxDL2HSnKcB0+GPOsndqSv+a4IBu9+I3fyBp5hkyMMG2+AXugd9pMpy6VxJxlNjhX1MYbNTZJUT4nudki4uh+LMOkIBHOceGNXjgB+cXqmlUnjlqha/HgboeHSnSgpM3dKSJQlIOsDwIDAQAB";

  private static final String SANDBOX_PUBLIC_KEY =
      "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbm21i11hgAENGd3/f280PSe4g9YGkS3TEXBYMidihTvHHf+tJ0PYD0o3PruI0hl3qhEjHTAxb75T5YD3SGK4IBhHn/Rk6mhqlGgI+bBrBVYaXixmHfRo75RpUUuWACyeqQkZckgR0McxuW9xRMIa2cXZOoL1E4SL4lXKGhKoWbwIDAQAB";

  private String signType = TigerApiConstants.SIGN_TYPE_RSA;
  private String charset = TigerApiConstants.UTF_8;

  private static final long REFRESH_URL_INTERVAL_SECONDS = 300;
  private ScheduledThreadPoolExecutor domainExecutorService;

  static {
    Security.setProperty("jdk.certpath.disabledAlgorithms", "");
  }

  public TigerHttpClient() {
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
    this.clientConfig = clientConfig;
    ConfigFileUtil.loadConfigFile(clientConfig);
    init(clientConfig.tigerId, clientConfig.privateKey);
    if (clientConfig.failRetryCounts <= TigerApiConstants.MAX_FAIL_RETRY_COUNT) {
      this.failRetryCounts = Math.max(clientConfig.failRetryCounts, 0);
    }
    this.tokenManager = new TokenManager(this);
    this.tokenManager.init();
    initDomainRefreshTask();
    if (clientConfig.isAutoGrabPermission) {
      TigerHttpRequest request = new TigerHttpRequest(MethodName.GRAB_QUOTE_PERMISSION);
      request.setBizContent(AccountParamBuilder.instance().buildJsonWithoutDefaultAccount());
      TigerHttpResponse response = execute(request);
      ApiLogger.info("tigerId:{}, grab_quote_permission:{}, data:{}",
          tigerId, response.getMessage(), response.getData());
    }
    return this;
  }

  private void init(String tigerId, String privateKey) {
    if (tigerId == null) {
      throw new RuntimeException("tigerId is empty.");
    }
    if (privateKey == null) {
      throw new RuntimeException("privateKey is empty.");
    }
    this.tigerId = tigerId;
    this.privateKey = privateKey;
    if (this.clientConfig.getEnv() == Env.PROD) {
      this.tigerPublicKey = ONLINE_PUBLIC_KEY;
    } else {
      this.tigerPublicKey = SANDBOX_PUBLIC_KEY;
    }
    this.deviceId = NetworkUtil.getDeviceId();

    initLicense();
    refreshUrl();
    if (this.serverUrl == null) {
      throw new RuntimeException("serverUrl is empty.");
    }
  }

  public void destroy() {
    if (this.tokenManager != null) {
      this.tokenManager.destroy();
    }
    if (domainExecutorService != null && !domainExecutorService.isShutdown()) {
      domainExecutorService.shutdown();
    }
  }

  public TigerHttpClient accessToken(String accessToken) {
    this.accessToken = accessToken;
    return this;
  }

  public ClientConfig getClientConfig() {
    return clientConfig;
  }

  private void initLicense() {
    if (null == this.clientConfig.license) {
      try {
        Map<BizType, String> urlMap = NetworkUtil.getHttpServerAddress(this.clientConfig,null, this.serverUrl);
        this.serverUrl = StringUtils.defaultIfEmpty(urlMap.get(BizType.COMMON), this.serverUrl);
        UserLicenseRequest request = UserLicenseRequest.newRequest();
        UserLicenseResponse response = execute(request);
        if (response.isSuccess() && response.getLicenseItem() != null) {
          ApiLogger.debug("license:{}", JSON.toJSONString(response.getLicenseItem(), SerializerFeature.WriteEnumUsingToString));
          this.clientConfig.license = License.valueOf(response.getLicenseItem().getLicense());
        }
      } catch (Exception e) {
        ApiLogger.debug("get license fail. tigerId:{}", tigerId);
      }
    }
  }

  private void initDomainRefreshTask() {
    synchronized (TigerHttpClient.SingletonInner.singleton) {
      if (domainExecutorService == null || domainExecutorService.isTerminated()) {
        domainExecutorService = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
          @Override
          public Thread newThread(Runnable r) {
            Thread t = Executors.defaultThreadFactory().newThread(r);
            t.setDaemon(true);
            return t;
          }
        });
        domainExecutorService.scheduleWithFixedDelay(
            new Runnable() {
              @Override
              public void run() {
                refreshUrl();
              }
            }, REFRESH_URL_INTERVAL_SECONDS,
            REFRESH_URL_INTERVAL_SECONDS, TimeUnit.SECONDS);
      }
    }
  }

  private void refreshUrl() {
    try {
      Map<BizType, String> urlMap = NetworkUtil.getHttpServerAddress(this.clientConfig, this.clientConfig.license, this.serverUrl);
      String newServerUrl = urlMap.get(BizType.TRADE);
      if (newServerUrl == null) {
        newServerUrl = urlMap.get(BizType.COMMON);
      }
      String newQuoteServerUrl = urlMap.get(BizType.QUOTE) == null ? newServerUrl : urlMap.get(BizType.QUOTE);
      String newPaperServerUrl = urlMap.get(BizType.PAPER) == null ? newServerUrl : urlMap.get(BizType.PAPER);

      this.serverUrl = StringUtils.defaultIfEmpty(newServerUrl, this.serverUrl);
      this.quoteServerUrl = StringUtils.defaultIfEmpty(newQuoteServerUrl, this.quoteServerUrl);
      this.paperServerUrl = StringUtils.defaultIfEmpty(newPaperServerUrl, this.paperServerUrl);
    } catch (Throwable t) {
      ApiLogger.error("refresh serverUrl error", t);
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

  private void setDefaultAccountIfAbsent(TigerRequest request) {
    if (request instanceof TigerHttpRequest) {
      return;
    }
    // TigerCommonRequest
    MethodType methodType = request.getApiMethodName().getType();
    if (MethodType.TRADE == methodType && MethodName.ACCOUNTS != request.getApiMethodName()) {
      ApiModel apiModel = request.getApiModel();
      if (apiModel != null && StringUtils.isEmpty(apiModel.getAccount())
          && !StringUtils.isEmpty(this.clientConfig.defaultAccount)) {
        apiModel.setAccount(this.clientConfig.defaultAccount);
      }
    }
  }

  @Override
  public <T extends TigerResponse> T execute(TigerRequest<T> request) {
    T response;
    String param = null;
    String data = null;
    try {
      setDefaultAccountIfAbsent(request);
      validate(request);
      // after successful verification（string enumeration values may be reset）, generate JSON data
      param = JSONObject.toJSONString(buildParams(request), SerializerFeature.WriteEnumUsingToString);
      ApiLogger.debug("request param:{}", param);

      data = HttpUtils.post(getServerUrl(request), param, this.clientConfig.token,
          MethodName.PLACE_ORDER == request.getApiMethodName() ? 0 : failRetryCounts);

      ApiLogger.debug("response result:{}", data);
      if (StringUtils.isEmpty(data)) {
        throw new TigerApiException(TigerApiCode.EMPTY_DATA_ERROR);
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
      ApiLogger.error("request fail. tigerId:{}, method:{}, param:{}, response:{}",
          tigerId, request == null ? null : request.getApiMethodName(), param, data, e);
      return errorResponse(tigerId, request, e);
    } catch (TigerApiException e) {
      ApiLogger.error("request fail. tigerId:{}, method:{}, param:{}, response:{}",
          tigerId, request == null ? null : request.getApiMethodName(), param, data, e);
      return errorResponse(tigerId, request, e);
    } catch (Exception e) {
      ApiLogger.error("request fail. tigerId:{}, method:{}, param:{}, response:{}",
          tigerId, request == null ? null : request.getApiMethodName(), param, data, e);
      return errorResponse(tigerId, request, e);
    }
  }

  private <T extends TigerResponse> T errorResponse(String tigerId, TigerRequest<T> request, TigerApiException e) {
    try {
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
    params.put(METHOD, request.getApiMethodName().getValue());
    params.put(VERSION, request.getApiVersion());
    params.put(SDK_VERSION, SdkVersionUtils.getSdkVersion());
    if (request instanceof TigerHttpRequest) {
      params.put(BIZ_CONTENT, ((TigerHttpRequest) request).getBizContent());
    } else if (request.getApiModel() == null && request instanceof TigerCommonRequest) {
      params.put(BIZ_CONTENT, ((TigerCommonRequest) request).getBizContent());
    } else {
      ApiModel apiModel = request.getApiModel();
      if (apiModel instanceof BatchApiModel) {
        params.put(BIZ_CONTENT, JSONObject.toJSONString(((BatchApiModel) apiModel).getItems(), SerializerFeature.WriteEnumUsingToString));
      } else {
        setDefaultSecretKey(apiModel, request.getApiMethodName());
        params.put(BIZ_CONTENT, JSONObject.toJSONString(apiModel, SerializerFeature.WriteEnumUsingToString));
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

  private void setDefaultSecretKey(ApiModel apiModel, MethodName methodName) {
    if (methodName != null && methodName.getType() == MethodType.TRADE
        && !StringUtils.isEmpty(apiModel.getAccount())
        && !StringUtils.isEmpty(this.clientConfig.secretKey)) {
      // set default secretKey
      ReflectionUtil.checkAndSetDefaultValue(apiModel, "secretKey", "setSecretKey", this.clientConfig.secretKey);
    }
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
    ValidatorManager.getInstance().validate(request.getApiModel());
  }

  private String getServerUrl(TigerRequest request) {
    String url = null;
    MethodType methodType = request.getApiMethodName().getType();
    if (MethodType.QUOTE == methodType) {
      url = this.quoteServerUrl;
    } else if (MethodType.TRADE == methodType && paperServerUrl != null) {
      String account = AccountUtil.parseAccount(request);
      if (AccountUtil.isVirtualAccount(account)) {
        url = this.paperServerUrl;
      }
    }
    return url == null ? this.serverUrl : url;
  }

}
