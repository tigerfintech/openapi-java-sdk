package com.tigerbrokers.stock.openapi.client.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
  private static final String LOG_FILE_NAME = "tiger_openapi.log";
  private static final String LOG_FILE_PATTERN = "tiger_openapi.%d{yyyy-MM-dd}.log";

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
      Path logFilePath = Paths.get(logPath == null ? "log" : logPath, LOG_FILE_NAME);
      Path parentPath = logFilePath.toAbsolutePath().getParent();
      if (Files.notExists(parentPath)) {
        Files.createDirectories(parentPath);
      }
      String fullFilename = logFilePath.toAbsolutePath().toString();
      String fullFilenamePattern = Paths.get(parentPath.toAbsolutePath().toString(), LOG_FILE_PATTERN).toAbsolutePath().toString();

      LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

      PatternLayoutEncoder encoder = new PatternLayoutEncoder();
      encoder.setContext(loggerContext);
      encoder.setCharset(Charset.forName("UTF-8"));
      encoder.setPattern("%d %level - %msg%n");
      encoder.start();

      RollingFileAppender rollingFileAppender = new RollingFileAppender();
      TimeBasedRollingPolicy timeBasedRollingPolicy = new TimeBasedRollingPolicy<>();
      timeBasedRollingPolicy.setParent(rollingFileAppender);
      timeBasedRollingPolicy.setContext(loggerContext);
      timeBasedRollingPolicy.setFileNamePattern(fullFilenamePattern);
      timeBasedRollingPolicy.setMaxHistory(30);
      timeBasedRollingPolicy.start();
      rollingFileAppender.setName("TigerOpenApi");
      rollingFileAppender.setContext(loggerContext);
      rollingFileAppender.setFile(fullFilename);
      rollingFileAppender.setRollingPolicy(timeBasedRollingPolicy);
      rollingFileAppender.setEncoder(encoder);
      rollingFileAppender.start();

      logger = loggerContext.getLogger("com.tigerbrokers.openapi.client");
      logger.addAppender(rollingFileAppender);
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

      StringBuilder builder = new StringBuilder();
      builder.append(DateUtils.printSystemDate());
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
