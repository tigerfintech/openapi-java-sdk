package com.tigerbrokers.stock.openapi.client.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.FileAppender;
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.slf4j.LoggerFactory;

/**
 * Description:
 * Created by lijiawen on 2018/12/27.
 */
public class ApiLogger {

  private static Logger logger = null;

  private static boolean enabled = false;
  private static boolean debugEnabled = false;
  private static boolean infoEnabled = true;
  private static boolean errorEnabled = true;
  private static final String SPLITTER = "###";

  public static void setEnabled(boolean isEnabled) {
    setEnabled(isEnabled, null);
  }

  public static void setEnabled(boolean isEnabled, String logPath) {
    Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    root.setLevel(Level.INFO);
    if (isEnabled) {
      initConfig(logPath);
    }
    ApiLogger.enabled = isEnabled;
  }

  private static void initConfig(String logPath) {
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtils.FORMAT_DATE)
          .withZone(ZoneId.of(ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone().getZoneId()));
      String filename = "tiger_openapi_" + LocalDateTime.now().format(formatter) + ".log";
      File logFilePath;
      if (logPath == null) {
        logFilePath = new File("log/");
      } else {
        logFilePath = new File(logPath);
      }
      if (!logFilePath.exists()) {
        logFilePath.mkdir();
      }
      String filePath = (logPath == null ? "log/" : logPath) + filename;
      File logFile = new File(filePath);
      if (!logFilePath.exists()) {
        try {
          logFile.createNewFile();
        } catch (IOException e) {
          throw new RuntimeException("create log error:" + e.getMessage());
        }
      }
      LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

      PatternLayoutEncoder encoder = new PatternLayoutEncoder();
      encoder.setContext(loggerContext);
      encoder.setCharset(Charset.forName("UTF-8"));
      encoder.setPattern("%d %level - %msg%n");
      encoder.start();

      FileAppender fileAppender = new FileAppender();
      fileAppender.setName("TigerOpenApi");
      fileAppender.setContext(loggerContext);
      fileAppender.setFile(filePath);
      fileAppender.setEncoder(encoder);
      fileAppender.start();

      logger = loggerContext.getLogger("com.tigerbrokers.openapi.client");
      logger.addAppender(fileAppender);
      logger.setLevel(Level.INFO);
    } catch (Throwable e) {
      throw new RuntimeException("an error occurred while init log config, error message:{}" + e.getMessage());
    }
  }

  public static void setDebugEnabled(boolean debugEnabled) {
    ApiLogger.debugEnabled = debugEnabled;
  }

  public static void setInfoEnabled(boolean infoEnabled) {
    ApiLogger.infoEnabled = infoEnabled;
  }

  public static void setErrorEnabled(boolean errorEnabled) {
    ApiLogger.errorEnabled = errorEnabled;
  }

  public static void error(String appKey, String method, String version, Exception e) {
    error(appKey, method, version, null, null, e);
  }

  public static void error(String appKey, String method, String version, String bizContent, String responseData,
      Exception e) {
    if (!enabled || !errorEnabled) {
      return;
    }

    try {
      DateTimeFormatter dtf = DateUtils.DATE_FORMAT;
      if (ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone() != TimeZoneId.Shanghai) {
        dtf = DateTimeFormatter.ofPattern(DateUtils.FORMAT_DATE)
            .withZone(ZoneId.of(ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone().getZoneId()));
      }

      StringBuilder builder = new StringBuilder();
      builder.append(dtf.format(LocalDateTime.now(ZoneId.of(
          ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone().getZoneId()))));
      builder.append(SPLITTER);
      builder.append(method);
      builder.append(SPLITTER);
      builder.append(version);
      builder.append(SPLITTER);
      builder.append(bizContent);
      builder.append(SPLITTER);
      builder.append(appKey);
      builder.append(SPLITTER);
      builder.append(responseData);
      builder.append(SPLITTER);
      builder.append(e.getMessage());

      logger.error(builder.toString().replaceAll("\r\n", " "));
    } catch (Exception ex) {
      throw new RuntimeException("an error occurred while writing the error log, origin error message:{}" + e.getMessage());
    }
  }

  public static void error(String message, Object value, Object exception) {
    if (!enabled || !errorEnabled) {
      return;
    }
    logger.error(message, value, exception);
  }

  public static void error(String message) {
    if (!enabled || !errorEnabled) {
      return;
    }
    logger.error(message);
  }

  public static void error(String message, Object... value) {
    if (!enabled || !errorEnabled) {
      return;
    }
    logger.error(message, value);
  }

  public static void info(String message) {
    if (!enabled || !infoEnabled) {
      return;
    }
    logger.info(message);
  }

  public static void info(String message, Object value) {
    if (!enabled || !infoEnabled) {
      return;
    }
    logger.info(message, value);
  }

  public static void info(String message, Object value1, Object value2) {
    if (!enabled || !infoEnabled) {
      return;
    }
    logger.info(message, value1, value2);
  }

  public static void info(String message, Object value1, Object value2, Object value3) {
    if (!enabled || !infoEnabled) {
      return;
    }
    logger.info(message, value1, value2, value3);
  }

  public static void debug(String message, Object value) {
    if (!enabled || !debugEnabled) {
      return;
    }
    logger.info(message, value);
  }
}
