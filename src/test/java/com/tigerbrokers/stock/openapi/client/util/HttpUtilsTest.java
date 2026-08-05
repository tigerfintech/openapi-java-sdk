package com.tigerbrokers.stock.openapi.client.util;

import java.io.IOException;
import java.lang.reflect.Field;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests for {@link HttpUtils} post/get paths with a mocked OkHttpClient.
 */
public class HttpUtilsTest {

  private OkHttpClient originalClient;
  private OkHttpClient mockClient;

  @Before
  public void setUp() throws Exception {
    originalClient = HttpUtils.client;
    mockClient = Mockito.mock(OkHttpClient.class);
    HttpUtils.client = mockClient;
  }

  @After
  public void tearDown() {
    HttpUtils.client = originalClient;
  }

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

  /* ---------- post success path ---------- */

  @Test
  public void testPostSuccess() throws Exception {
    Response mockResponse = mockResponse("ok-body");
    Call mockCall = Mockito.mock(Call.class);
    Mockito.when(mockCall.execute()).thenReturn(mockResponse);
    Mockito.when(mockClient.newCall(Mockito.any(Request.class))).thenReturn(mockCall);

    String result = HttpUtils.post("https://example.com/api", "{\"k\":\"v\"}", "token", 0);
    Assert.assertEquals("ok-body", result);
  }

  @Test
  public void testPostSuccessWithoutToken() throws Exception {
    Response mockResponse = mockResponse("no-token-body");
    Call mockCall = Mockito.mock(Call.class);
    Mockito.when(mockCall.execute()).thenReturn(mockResponse);
    Mockito.when(mockClient.newCall(Mockito.any(Request.class))).thenReturn(mockCall);

    String result = HttpUtils.post("https://example.com/api", "{\"k\":\"v\"}", "", 0);
    Assert.assertEquals("no-token-body", result);
  }

  /* ---------- post retry path ---------- */

  @Test
  public void testPostRetryOnIOException() throws Exception {
    Response okResponse = mockResponse("after-retry");
    Call mockCall = Mockito.mock(Call.class);
    // first call throws IOException, second call returns ok
    Mockito.when(mockCall.execute())
        .thenThrow(new IOException("connect refused"))
        .thenReturn(okResponse);
    Mockito.when(mockClient.newCall(Mockito.any(Request.class))).thenReturn(mockCall);

    String result = HttpUtils.post("https://example.com/api", "{}", "tok", 2);
    Assert.assertEquals("after-retry", result);
  }

  @Test(expected = IOException.class)
  public void testPostRetryExhausted() throws Exception {
    Call mockCall = Mockito.mock(Call.class);
    Mockito.when(mockCall.execute())
        .thenThrow(new IOException("connect refused"));
    Mockito.when(mockClient.newCall(Mockito.any(Request.class))).thenReturn(mockCall);

    // retryCount=0, no retry
    HttpUtils.post("https://example.com/api", "{}", "tok", 0);
  }

  @Test(expected = IOException.class)
  public void testPostRetryExhaustedAfterRetries() throws Exception {
    Call mockCall = Mockito.mock(Call.class);
    Mockito.when(mockCall.execute())
        .thenThrow(new IOException("connect refused"));
    Mockito.when(mockClient.newCall(Mockito.any(Request.class))).thenReturn(mockCall);

    HttpUtils.post("https://example.com/api", "{}", "tok", 2);
  }

  /* ---------- post null response / null body path ---------- */

  @Test(expected = RuntimeException.class)
  public void testPostNullResponse() throws Exception {
    Call mockCall = Mockito.mock(Call.class);
    Mockito.when(mockCall.execute()).thenReturn(null);
    Mockito.when(mockClient.newCall(Mockito.any(Request.class))).thenReturn(mockCall);
    HttpUtils.post("https://example.com/api", "{}", "tok", 0);
  }

  @Test(expected = RuntimeException.class)
  public void testPostNullResponseBody() throws Exception {
    Response mockResponse = Mockito.mock(Response.class);
    Mockito.when(mockResponse.body()).thenReturn(null);
    Call mockCall = Mockito.mock(Call.class);
    Mockito.when(mockCall.execute()).thenReturn(mockResponse);
    Mockito.when(mockClient.newCall(Mockito.any(Request.class))).thenReturn(mockCall);
    HttpUtils.post("https://example.com/api", "{}", "tok", 0);
  }

  /* ---------- post internal_error retry path ---------- */

  @Test
  public void testPostInternalErrorRetry() throws Exception {
    Response errResponse = mockResponse(
        "internal_error:A system error occurred, please try again later");
    Response okResponse = mockResponse("ok-after-internal");
    Call mockCall = Mockito.mock(Call.class);
    Mockito.when(mockCall.execute())
        .thenReturn(errResponse)
        .thenReturn(okResponse);
    Mockito.when(mockClient.newCall(Mockito.any(Request.class))).thenReturn(mockCall);

    String result = HttpUtils.post("https://example.com/api", "{}", "tok", 2);
    Assert.assertEquals("ok-after-internal", result);
  }

  /* ---------- post non-retryable exception path ---------- */

  @Test(expected = RuntimeException.class)
  public void testPostNonRetryableException() throws Exception {
    Call mockCall = Mockito.mock(Call.class);
    Mockito.when(mockCall.execute())
        .thenThrow(new RuntimeException("non-io-exception"));
    Mockito.when(mockClient.newCall(Mockito.any(Request.class))).thenReturn(mockCall);

    HttpUtils.post("https://example.com/api", "{}", "tok", 2);
  }

  /* ---------- post(3-arg) overload ---------- */

  @Test
  public void testPostThreeArgOverload() throws Exception {
    Response mockResponse = mockResponse("three-arg-body");
    Call mockCall = Mockito.mock(Call.class);
    Mockito.when(mockCall.execute()).thenReturn(mockResponse);
    Mockito.when(mockClient.newCall(Mockito.any(Request.class))).thenReturn(mockCall);

    String result = HttpUtils.post("https://example.com/api", "{}", "tok");
    Assert.assertEquals("three-arg-body", result);
  }

  /* ---------- get success path ---------- */

  @Test
  public void testGetSuccess() throws Exception {
    Response mockResponse = mockResponse("get-body");
    Call mockCall = Mockito.mock(Call.class);
    Mockito.when(mockCall.execute()).thenReturn(mockResponse);
    Mockito.when(mockClient.newCall(Mockito.any(Request.class))).thenReturn(mockCall);

    String result = HttpUtils.get("https://example.com/api", "tok");
    Assert.assertEquals("get-body", result);
  }

  @Test
  public void testGetSuccessWithoutToken() throws Exception {
    Response mockResponse = mockResponse("get-no-tok");
    Call mockCall = Mockito.mock(Call.class);
    Mockito.when(mockCall.execute()).thenReturn(mockResponse);
    Mockito.when(mockClient.newCall(Mockito.any(Request.class))).thenReturn(mockCall);

    String result = HttpUtils.get("https://example.com/api", "");
    Assert.assertEquals("get-no-tok", result);
  }

  @Test(expected = RuntimeException.class)
  public void testGetNullResponse() throws Exception {
    Call mockCall = Mockito.mock(Call.class);
    Mockito.when(mockCall.execute()).thenReturn(null);
    Mockito.when(mockClient.newCall(Mockito.any(Request.class))).thenReturn(mockCall);
    HttpUtils.get("https://example.com/api", "tok");
  }

  @Test(expected = RuntimeException.class)
  public void testGetNullResponseBody() throws Exception {
    Response mockResponse = Mockito.mock(Response.class);
    Mockito.when(mockResponse.body()).thenReturn(null);
    Call mockCall = Mockito.mock(Call.class);
    Mockito.when(mockCall.execute()).thenReturn(mockResponse);
    Mockito.when(mockClient.newCall(Mockito.any(Request.class))).thenReturn(mockCall);
    HttpUtils.get("https://example.com/api", "tok");
  }

  @Test(expected = IOException.class)
  public void testGetIoException() throws Exception {
    Call mockCall = Mockito.mock(Call.class);
    Mockito.when(mockCall.execute()).thenThrow(new IOException("io"));
    Mockito.when(mockClient.newCall(Mockito.any(Request.class))).thenReturn(mockCall);
    HttpUtils.get("https://example.com/api", "tok");
  }

  @Test(expected = RuntimeException.class)
  public void testGetNonIoException() throws Exception {
    Call mockCall = Mockito.mock(Call.class);
    Mockito.when(mockCall.execute()).thenThrow(new RuntimeException("boom"));
    Mockito.when(mockClient.newCall(Mockito.any(Request.class))).thenReturn(mockCall);
    HttpUtils.get("https://example.com/api", "tok");
  }

  /* ---------- helper ---------- */

  /**
   * Build a Mockito-mocked okhttp3.Response whose body().string() returns the given text.
   */
  private static Response mockResponse(String bodyText) throws IOException {
    Response mockResponse = Mockito.mock(Response.class);
    ResponseBody mockBody = Mockito.mock(ResponseBody.class);
    BufferedSource mockSource = Mockito.mock(BufferedSource.class);
    Mockito.when(mockResponse.body()).thenReturn(mockBody);
    Mockito.when(mockBody.string()).thenReturn(bodyText);
    Mockito.when(mockBody.source()).thenReturn(mockSource);
    return mockResponse;
  }
}
