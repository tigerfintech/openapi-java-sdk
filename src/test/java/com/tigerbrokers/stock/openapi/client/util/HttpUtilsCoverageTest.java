package com.tigerbrokers.stock.openapi.client.util;

import org.junit.Assert;
import org.junit.Test;

public class HttpUtilsCoverageTest {

  @Test(expected = RuntimeException.class)
  public void testPost_nullUrl() throws Exception {
    HttpUtils.post(null, "{}", "token", 0);
  }

  @Test(expected = RuntimeException.class)
  public void testPost_nullJson() throws Exception {
    HttpUtils.post("http://localhost", null, "token", 0);
  }

  @Test(expected = RuntimeException.class)
  public void testGet_nullUrl() throws Exception {
    HttpUtils.get(null, "token");
  }

  @Test
  public void testConstants() {
    Assert.assertEquals(5000, HttpUtils.CONNECT_TIMEOUT);
    Assert.assertEquals(5000, HttpUtils.SOCKET_TIMEOUT);
    Assert.assertNotNull(HttpUtils.JSON);
    Assert.assertNotNull(HttpUtils.client);
  }
}
