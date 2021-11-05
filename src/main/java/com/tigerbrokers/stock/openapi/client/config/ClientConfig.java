package com.tigerbrokers.stock.openapi.client.config;

import com.tigerbrokers.stock.openapi.client.constant.ClientConfigProd;
import com.tigerbrokers.stock.openapi.client.constant.ClientConfigSandbox;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author liutongping
 * @version 1.0
 * @description:
 * @date 2021/11/5 下午4:11
 */
public class ClientConfig {
  private static final String PPRVATE_KEY_BEGIN = "-----BEGIN PRIVATE KEY-----";
  private static final String PRIVATE_KEY_END = "-----END PRIVATE KEY-----";
  /** default client config */
  public static final ClientConfig DEFAULT_CONFIG = new ClientConfig();

  /**
   * config environment ：prod，sandbox
   */
  public String env = ClientConfigProd.env;

  /**
   * http interface server url
   */
  public String serverUrl = ClientConfigProd.serverUrl;

  /**
   * socket server url
   */
  public String socketServerUrl = ClientConfigProd.socketServerUrl;

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

  public void init(boolean isProd) {
    if (isProd) {
      env = ClientConfigProd.env;
      serverUrl = ClientConfigProd.serverUrl;
      socketServerUrl = ClientConfigProd.socketServerUrl;
    } else {
      env = ClientConfigSandbox.env;
      serverUrl = ClientConfigSandbox.serverUrl;
      socketServerUrl = ClientConfigSandbox.socketServerUrl;
    }
  }

  /**
   * read private key from file
   *
   * @param privateKeyFile absolute path
   */
  public String readPrivateKey(String privateKeyFile) {
    String content = "";
    File file = new File(privateKeyFile);
    try (FileInputStream in = new FileInputStream(file)) {
      int size = in.available();
      byte[] buffer = new byte[size];
      in.read(buffer);
      content = new String(buffer, "UTF-8");
      if (content.startsWith(PPRVATE_KEY_BEGIN)) {
        content = content.substring(PPRVATE_KEY_BEGIN.length());
      }
      if (content.endsWith(PRIVATE_KEY_END)) {
        content = content.substring(0, content.length() - PRIVATE_KEY_END.length());
      }
    } catch (IOException e) {
      ApiLogger.error("read file fail:" + privateKeyFile, e);
    }
    return content;
  }
}
