package com.tigerbrokers.stock.openapi.client.util;

import okhttp3.MediaType;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link HttpUtils}.
 */
public class HttpUtilsTest {

  @Test(expected = RuntimeException.class)
  public void testPostNullUrl() throws Exception {
    HttpUtils.post(null, "{\"key\":\"value\"}", "token", 0);
  }

  @Test(expected = RuntimeException.class)
  public void testPostNullJson() throws Exception {
    HttpUtils.post("https://httpbin.org/post", null, "token", 0);
  }

  @Test(expected = RuntimeException.class)
  public void testPostNullUrlAndNullJson() throws Exception {
    HttpUtils.post(null, null, "token", 0);
  }

  @Test(expected = RuntimeException.class)
  public void testGetNullUrl() throws Exception {
    HttpUtils.get(null, "token");
  }

  @Test
  public void testConstantsAndClient() {
    Assert.assertEquals(5000, HttpUtils.CONNECT_TIMEOUT);
    Assert.assertEquals(5000, HttpUtils.SOCKET_TIMEOUT);
    Assert.assertNotNull(HttpUtils.JSON);
    Assert.assertEquals("application/json; charset=utf-8", HttpUtils.JSON.toString());
    Assert.assertNotNull(HttpUtils.client);
  }
}
