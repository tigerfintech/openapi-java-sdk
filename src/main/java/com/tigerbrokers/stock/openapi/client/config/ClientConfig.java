package com.tigerbrokers.stock.openapi.client.config;

import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.struct.enums.Env;
import com.tigerbrokers.stock.openapi.client.struct.enums.License;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.builder.HeaderBuilder;
import io.netty.handler.ssl.SslProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * description: Created by liutongping on 2021/11/5
 */
public class ClientConfig {
  private static final String PPRVATE_KEY_PREFIX = "KEY-----";
  private static final String PRIVATE_KEY_SUFFIX = "-----END";
  private static final Env DEFAULT_ENV = Env.PROD;
  private static final SslProvider DEFAULT_SSLPROVIDER = SslProvider.OPENSSL;
  /** default client config */
  public static final ClientConfig DEFAULT_CONFIG = new ClientConfig();

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
   * whether to automatically grab quote permission when the initialization instance is completed
   */
  public boolean isAutoGrabPermission = true;

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
   *
   * @param privateKeyFile absolute path
   * @return privateKey String
   */
  public String readPrivateKey(String privateKeyFile) {
    String content = "";
    File file = new File(privateKeyFile);
    try (FileInputStream in = new FileInputStream(file)) {
      int size = in.available();
      byte[] buffer = new byte[size];
      in.read(buffer);
      content = new String(buffer, "UTF-8");
      int start = 0;
      int startIdx = content.indexOf(PPRVATE_KEY_PREFIX);
      if (startIdx > 0) {
        start = startIdx + PPRVATE_KEY_PREFIX.length();
      }
      int end = content.length();
      int endIndex = content.indexOf(PRIVATE_KEY_SUFFIX);
      if (endIndex > 0) {
        end = endIndex;
      }
      StringBuilder builder = new StringBuilder();
      for (int i = start; i < end; i++) {
        if (content.charAt(i) == 10 || content.charAt(i) == 13) {
          continue;
        }
        builder.append(content.charAt(i));
      }
      content = builder.toString();
    } catch (IOException e) {
      ApiLogger.error("read file fail:" + privateKeyFile, e);
    }
    return content;
  }
}
