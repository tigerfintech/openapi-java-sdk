package com.tigerbrokers.stock.openapi.client.config;

import com.tigerbrokers.stock.openapi.client.struct.enums.Env;
import com.tigerbrokers.stock.openapi.client.struct.enums.License;
import com.tigerbrokers.stock.openapi.client.struct.enums.Protocol;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
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
  /** default client config */
  public static final ClientConfig DEFAULT_CONFIG = new ClientConfig();
  private static final Protocol DEFAULT_PROTOCOL = Protocol.STOMP;
  private static final Env DEFAULT_ENV = Env.PROD;
  private static final License DEFAULT_LICENSE = License.TBNZ;
  private static final SslProvider DEFAULT_SSLPROVIDER = SslProvider.OPENSSL;

  private Protocol subscribeProtocol = DEFAULT_PROTOCOL;

  private Env env = DEFAULT_ENV;

  private License license = DEFAULT_LICENSE;

  private SslProvider sslProvider = DEFAULT_SSLPROVIDER;

  /**
   * config label ：prod，sandbox
   */
  @Deprecated
  public String label = "prod";

  /**
   * http interface server url
   */
  public String serverUrl;

  /**
   * socket server url
   */
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
   * institutional trader private key 机构交易员专有密钥
   */
  public String secretKey = null;

  public ClientConfig() {
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

  public License getLicense() {
    return license;
  }

  public void setLicense(License license) {
    if (license == null) {
      return;
    }
    this.license = license;
  }

  public SslProvider getSslProvider() {
    return sslProvider;
  }

  public void setSslProvider(SslProvider sslProvider) {
    this.sslProvider = sslProvider;
  }

  /**
   * read private key from file
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
