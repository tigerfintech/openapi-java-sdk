package com.tigerbrokers.stock.openapi.client.constant;

public class TigerApiConstants {

  public static final String DEFAULT_VERSION = "1.0";

  public static final String SIGN_TYPE = "sign_type";

  public static final String SIGN_TYPE_RSA = "RSA";

  public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

  public static final String TIGER_ID = "tiger_id";

  public static final String ACCESS_TOKEN = "access_token";

  public static final String TRADE_TOKEN = "trade_token";

  public static final String ACCOUNT_TYPE = "account_type";

  public static final String METHOD = "method";

  public static final String TIMESTAMP = "timestamp";

  public static final String VERSION = "version";

  public static final String SDK_VERSION = "sdk-version";

  public static final String SIGN = "sign";

  public static final String CHARSET = "charset";

  public static final String NOTIFY_URL = "notify_url";

  public static final String BIZ_CONTENT = "biz_content";

  public static final String CHARSET_UTF8 = "UTF-8";

  public static final String CONTENT_TYPE_JSON = "application/json";

  public static final String CODE = "code";

  public static final String MESSAGE = "message";

  public static final String DATA = "data";

  public static final String ACCOUNT = "account";

  public static final String DEVICE_ID = "device_id";

  public static final String SSL_HANDLER_NAME = "sslHandler";

  public static final String DEFAULT_DOMAIN_KEY = "COMMON";

  @Deprecated
  public static final String API_ONLINE_DOMAIN_URL_OLD = "openapi.itiger.com";
  @Deprecated
  public static final String API_ONLINE_DOMAIN_URL_TEMP = "openapi.skytigris.cn";
  public static final String API_ONLINE_DOMAIN_URL = "openapi.tigerfintech.com";
  public static final String API_SANDBOX_DOMAIN_URL = "openapi-sandbox.tigerfintech.com";

  public static final String DEFAULT_PROD_DOMAIN_URL = API_ONLINE_DOMAIN_URL;
  public static final String DEFAULT_SANDBOX_DOMAIN_URL = API_SANDBOX_DOMAIN_URL;
  public static final String DOMAIN_GARDEN_ADDRESS = "https://cg.play-analytics.com/";

  public static final String DEFAULT_PROD_STOMP_PORT = "9887";
  public static final String DEFAULT_PROD_SOCKET_PORT = "9883";
  public static final String DEFAULT_SANDBOX_STOMP_PORT = "9889";
  public static final String DEFAULT_SANDBOX_SOCKET_PORT = "9885";

}
