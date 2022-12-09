package com.tigerbrokers.stock.openapi.client.config;

import com.tigerbrokers.stock.openapi.client.struct.enums.Env;
import com.tigerbrokers.stock.openapi.client.struct.enums.License;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.Protocol;
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
  private static final String PPRVATE_KEY_BEGIN = "-----BEGIN PRIVATE KEY-----";
  private static final String PRIVATE_KEY_END = "-----END PRIVATE KEY-----";
  private static final Protocol DEFAULT_PROTOCOL = Protocol.SECURE_SOCKET;
  private static final Env DEFAULT_ENV = Env.PROD;
  private static final SslProvider DEFAULT_SSLPROVIDER = SslProvider.OPENSSL;
  /** default client config */
  public static final ClientConfig DEFAULT_CONFIG = new ClientConfig();

  private Protocol subscribeProtocol = DEFAULT_PROTOCOL;

  private Env env = DEFAULT_ENV;

  private SslProvider sslProvider = DEFAULT_SSLPROVIDER;

  public License license;

  public String version = HeaderBuilder.DEFAULT_VERSION;

  /**
   * http interface server url
   */
  @Deprecated
  public String serverUrl;

  /**
   * socket server url
   */
  @Deprecated
  public String socketServerUrl;

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

  private ClientConfig() {
  }

  public TimeZoneId getDefaultTimeZone() {
    return timeZone == null ? TimeZoneId.Shanghai : timeZone;
  }

  public Language getDefaultLanguage() {
    return language == null ? Language.en_US : language;
  }

  public Protocol getSubscribeProtocol() {
    return subscribeProtocol;
  }

  public void setSubscribeProtocol(Protocol subscribeProtocol) {
    if (subscribeProtocol == null || subscribeProtocol == Protocol.HTTP) {
      return;
    }
    this.subscribeProtocol = subscribeProtocol;
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
      if (content.startsWith(PPRVATE_KEY_BEGIN)) {
        start = PPRVATE_KEY_BEGIN.length();
        while (content.charAt(start) == 10 || content.charAt(start) == 13) {
          start++;
        }
      }
      int end = content.length();
      int endIndex = content.indexOf(PRIVATE_KEY_END);
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
