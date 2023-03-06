package com.tigerbrokers.stock.openapi.client.config;

import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.struct.enums.Env;
import com.tigerbrokers.stock.openapi.client.struct.enums.License;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.ConfigFileUtil;
import com.tigerbrokers.stock.openapi.client.util.builder.HeaderBuilder;
import io.netty.handler.ssl.SslProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.CHARSET_UTF8;

/**
 * description: Created by liutongping on 2021/11/5
 */
public class ClientConfig {
  private static final Env DEFAULT_ENV = Env.PROD;
  private static final SslProvider DEFAULT_SSLPROVIDER = SslProvider.OPENSSL;
  /** default client config */
  public static final ClientConfig DEFAULT_CONFIG = new ClientConfig();

  public String configFilePath;

  private Env env = DEFAULT_ENV;

  private SslProvider sslProvider = DEFAULT_SSLPROVIDER;

  public License license;

  public String version = HeaderBuilder.DEFAULT_VERSION;

  public boolean isSslSocket = true;

  /**
   * tigerId : 2015xxxx,  address：https://www.itiger.com/openapi/info
   */
  public String tigerId = null;

  /**
   * default account
   */
  public String defaultAccount = null;

  /**
   * private key
   */
  public String privateKey = null;

  /**
   * token(Only Hong Kong license required)
   */
  public volatile String token = null;

  /**
   * refresh token frequency
   */
  public int refreshTokenIntervalDays;

  /**
   * refresh token time, formate: HH:mm:ss
   */
  public String refreshTokenTime;

  /**
   * whether to automatically grab quote permission when the initialization instance is completed
   */
  public boolean isAutoGrabPermission = true;

  /**
   * whether to automatically refresh token
   */
  public boolean isAutoRefreshToken = true;

  /**
   * default time zone
   */
  public TimeZoneId timeZone = TimeZoneId.Shanghai;

  /**
   * default language
   */
  public Language language = Language.en_US;

  /**
   * institutional trader private key
   */
  public String secretKey = null;

  /**
   * request fail retry counts, range:[0, 5], if less than 1 will no retry; if bigger than 5 will set default value
   */
  public int failRetryCounts = TigerApiConstants.DEFAULT_FAIL_RETRY_COUNT;

  private ClientConfig() {
  }

  public TimeZoneId getDefaultTimeZone() {
    return timeZone == null ? TimeZoneId.Shanghai : timeZone;
  }

  public Language getDefaultLanguage() {
    return language == null ? Language.en_US : language;
  }

  public Env getEnv() {
    return env;
  }

  public void setEnv(Env env) {
    if (env == null) {
      return;
    }
    this.env = env;
  }

  public SslProvider getSslProvider() {
    return sslProvider;
  }

  public void setSslProvider(SslProvider sslProvider) {
    this.sslProvider = sslProvider;
  }

  /**
   * read private key from file(Remove first and last lines and line breaks)
   * @see ConfigFileUtil readPrivateKey()
   *
   * @param privateKeyFile absolute path
   * @return privateKey String
   */
  @Deprecated
  public String readPrivateKey(String privateKeyFile) {
    String content = "";
    File file = new File(privateKeyFile);
    try (FileInputStream in = new FileInputStream(file)) {
      int size = in.available();
      byte[] buffer = new byte[size];
      in.read(buffer);
      content = ConfigFileUtil.processPrivateKey(new String(buffer, CHARSET_UTF8));
    } catch (IOException e) {
      ApiLogger.error("read file fail:" + privateKeyFile, e);
    }
    return content;
  }
}
