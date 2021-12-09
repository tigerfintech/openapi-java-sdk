package com.tigerbrokers.stock.openapi.client.config;

import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
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

  /**
   * config label ：prod，sandbox
   */
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
    this.serverUrl = "https://openapi.itiger.com/gateway";
    this.socketServerUrl = "wss://openapi.itiger.com:8887/stomp";
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
