package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 * Created by lijiawen on 2018/12/27.
 */
public class ApiLogger {

  private static final Logger logger = LoggerFactory.getLogger("openapi.sdk.common.error");
  private static boolean enabled = true;
  private static final String SPLITER = "###";

  public static void setEnabled(boolean enabled) {
    ApiLogger.enabled = enabled;
  }

  public static void error(String appKey, String method, String version, String bizContent, String responseData,
      Exception e) {
    if (!enabled) {
      return;
    }
    DateFormat df = new SimpleDateFormat(TigerApiConstants.DATE_TIME_FORMAT);
    df.setTimeZone(TimeZone.getTimeZone(TigerApiConstants.DATE_TIMEZONE));

    StringBuilder builder = new StringBuilder();
    builder.append(df.format(new Date()));
    builder.append(SPLITER);
    builder.append(method);
    builder.append(SPLITER);
    builder.append(version);
    builder.append(SPLITER);
    builder.append(bizContent);
    builder.append(SPLITER);
    builder.append(appKey);
    builder.append(SPLITER);
    builder.append(responseData);
    builder.append(SPLITER);
    builder.append(e.getMessage());

    logger.error(builder.toString().replaceAll("\r\n", " "));
  }
}
