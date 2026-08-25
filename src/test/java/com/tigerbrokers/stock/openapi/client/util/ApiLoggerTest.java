package com.tigerbrokers.stock.openapi.client.util;

import org.junit.Test;

public class ApiLoggerTest {

  @Test
  public void testSetEnabled_false() {
    ApiLogger.setEnabled(false);
    ApiLogger.error("test message");
    ApiLogger.info("test info");
    ApiLogger.warn("test warn");
    ApiLogger.debug("test debug", "value");
  }
  @Test
  public void testSetEnabled_true_withLogPath() {
    ApiLogger.setEnabled(true, System.getProperty("java.io.tmpdir") + "/tiger_test_log");
    ApiLogger.info("info message");
    ApiLogger.error("error message");
    ApiLogger.warn("warn message");
    ApiLogger.setEnabled(false);
  }
  @Test
  public void testSetEnabled_true_defaultPath() {
    ApiLogger.setEnabled(true);
    ApiLogger.error("error test");
    ApiLogger.setEnabled(false);
  }
  @Test
  public void testSetDebugEnabled() {
    ApiLogger.setEnabled(true);
    ApiLogger.setDebugEnabled(true);
    ApiLogger.debug("debug message", "value");
    ApiLogger.setDebugEnabled(false);
    ApiLogger.setEnabled(false);
  }
  @Test
  public void testSetInfoEnabled() {
    ApiLogger.setEnabled(true);
    ApiLogger.setInfoEnabled(false);
    ApiLogger.info("should not log");
    ApiLogger.setInfoEnabled(true);
    ApiLogger.setEnabled(false);
  }
  @Test
  public void testSetWarnEnabled() {
    ApiLogger.setEnabled(true);
    ApiLogger.setWarnEnabled(false);
    ApiLogger.warn("should not log");
    ApiLogger.setWarnEnabled(true);
    ApiLogger.setEnabled(false);
  }
  @Test
  public void testSetErrorEnabled() {
    ApiLogger.setEnabled(true);
    ApiLogger.setErrorEnabled(false);
    ApiLogger.error("should not log");
    ApiLogger.setErrorEnabled(true);
    ApiLogger.setEnabled(false);
  }
  @Test
  public void testErrorWithException() {
    ApiLogger.setEnabled(true);
    ApiLogger.error("error with throwable", new RuntimeException("test cause"));
    ApiLogger.setEnabled(false);
  }
  @Test
  public void testErrorWithAppKeyAndMethod() {
    ApiLogger.setEnabled(true);
    ApiLogger.error("appKey", "method", "1.0", new RuntimeException("test"));
    ApiLogger.error("appKey", "method", "1.0", "bizContent", "responseData",
        new RuntimeException("test"));
    ApiLogger.setEnabled(false);
  }
  @Test
  public void testErrorWithVarargs() {
    ApiLogger.setEnabled(true);
    ApiLogger.error("error with {} args", "var");
    ApiLogger.setEnabled(false);
  }
  @Test
  public void testInfoWithMultipleArgs() {
    ApiLogger.setEnabled(true);
    ApiLogger.info("info with {} and {}", "val1", "val2");
    ApiLogger.info("info with {} and {} and {}", "v1", "v2", "v3");
    ApiLogger.setEnabled(false);
  }
  @Test
  public void testWarnWhenDisabled() {
    ApiLogger.setEnabled(false);
    ApiLogger.warn("warn while disabled", "arg1", "arg2");
  }
}
