package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import java.io.IOException;
import java.util.Properties;

/**
 * description: Created by liutongping on 2021/11/8
 */
public class SdkVersionUtils {
  private static String sdkVersion = null;
  private SdkVersionUtils(){}

  /**
   * 获取sdk版本信息
   * @return sdk version
   */
  public static String getSdkVersion() {
    if (null == sdkVersion) {
      Properties properties = new Properties();
      try {
        properties.load(SdkVersionUtils.class.getClassLoader().getResourceAsStream("tiger-client.properties"));
        String version = null;
        if (!properties.isEmpty()) {
          version = properties.getProperty(TigerApiConstants.SDK_VERSION);
        }
        if (version != null) {
          sdkVersion = "java-" + version;
        }
      } catch (IOException e) {
        ApiLogger.error("SdkVersionUtils getSdkVersion io exception:{}", e.getMessage(), e);
      }
    }
    return sdkVersion;
  }
}
