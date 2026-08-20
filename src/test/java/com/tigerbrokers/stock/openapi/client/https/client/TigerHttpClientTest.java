package com.tigerbrokers.stock.openapi.client.https.client;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.BatchApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.PrimeAssetModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerHttpRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.PrimeAssetRequest;
import com.tigerbrokers.stock.openapi.client.https.request.user.UserLicenseRequest;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import com.tigerbrokers.stock.openapi.client.https.response.user.UserLicenseResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.AccountType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Env;
import com.tigerbrokers.stock.openapi.client.struct.enums.License;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.util.HttpUtils;
import com.tigerbrokers.stock.openapi.client.util.TigerSignature;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class TigerHttpClientTest {

  private TigerHttpClient client;

  @Before
  public void setUp() throws Exception {
    client = new TigerHttpClient();
    client.useCustomServerUrl("http://localhost:9999/mock");

    ClientConfig config = new ClientConfig();
    config.tigerId = "testTigerId";
    config.privateKey = "testPrivateKey";
    config.defaultAccount = "testAccount";
    config.token = "testToken";

    Field f = TigerHttpClient.class.getDeclaredField("clientConfig");
    f.setAccessible(true);
    f.set(client, config);
  }

  @Test
  public void testGetInstance() {
    TigerHttpClient instance = TigerHttpClient.getInstance();
    Assert.assertNotNull(instance);
    Assert.assertSame(instance, TigerHttpClient.getInstance());
  }

  @Test
  public void testClientConfig() {
    ClientConfig config = new ClientConfig();
    config.tigerId = "testId";
    config.privateKey = "testKey";
    TigerHttpClient c = new TigerHttpClient();
    TigerHttpClient returned = c.clientConfig(config);
    Assert.assertSame(c, returned);
  }

  @Test
  public void testAccessToken() {
    TigerHttpClient c = new TigerHttpClient();
    c.setAccessToken("tok123");
    Assert.assertEquals("tok123", c.getAccessToken());
    TigerHttpClient returned = c.accessToken("tok456");
    Assert.assertSame(c, returned);
    Assert.assertEquals("tok456", c.getAccessToken());
  }

  @Test
  public void testTradeToken() {
    TigerHttpClient c = new TigerHttpClient();
    c.setTradeToken("trade123");
    Assert.assertEquals("trade123", c.getTradeToken());
  }

  @Test
  public void testAccountType() {
    TigerHttpClient c = new TigerHttpClient();
    c.setAccountType(AccountType.GLOBAL);
    Assert.assertEquals("GLOBAL", c.getAccountType());
  }

  @Test
  public void testAccountTypeNull() {
    TigerHttpClient c = new TigerHttpClient();
    c.setAccountType(null);
    Assert.assertNull(c.getAccountType());
  }

  @Test
  public void testUseCustomServerUrl() {
    TigerHttpClient c = new TigerHttpClient();
    c.useCustomServerUrl("http://test:8080");
  }

  @Test
  public void testDestroy() {
    TigerHttpClient c = new TigerHttpClient();
    c.destroy();
  }

  @Test
  public void testExecute_emptyResponse() {
    try (MockedStatic<HttpUtils> mocked = Mockito.mockStatic(HttpUtils.class)) {
      mocked.when(() -> HttpUtils.post(
          Mockito.anyString(), Mockito.anyString(),
          Mockito.anyString(), Mockito.anyInt()))
          .thenReturn("");

      UserLicenseRequest request = new UserLicenseRequest();
      TigerResponse response = client.execute(request);
      Assert.assertNotNull(response);
      Assert.assertTrue(response.getCode() != 0);
    }
  }

  @Test
  public void testExecute_validResponse() {
    try (MockedStatic<HttpUtils> mocked = Mockito.mockStatic(HttpUtils.class)) {
      String json = "{\"code\":0,\"message\":\"success\",\"timestamp\":12345,\"data\":{\"license\":0,\"expireAt\":9999999999}}";
      mocked.when(() -> HttpUtils.post(
          Mockito.anyString(), Mockito.anyString(),
          Mockito.anyString(), Mockito.anyInt()))
          .thenReturn(json);

      UserLicenseRequest request = new UserLicenseRequest();
      TigerResponse response = client.execute(request);
      Assert.assertNotNull(response);
    }
  }

  @Test
  public void testExecute_nullResponse() {
    try (MockedStatic<HttpUtils> mocked = Mockito.mockStatic(HttpUtils.class)) {
      mocked.when(() -> HttpUtils.post(
          Mockito.anyString(), Mockito.anyString(),
          Mockito.anyString(), Mockito.anyInt()))
          .thenReturn(null);

      UserLicenseRequest request = new UserLicenseRequest();
      TigerResponse response = client.execute(request);
      Assert.assertNotNull(response);
      Assert.assertTrue(response.getCode() != 0);
    }
  }

  @Test
  public void testExecute_exception() {
    try (MockedStatic<HttpUtils> mocked = Mockito.mockStatic(HttpUtils.class)) {
      mocked.when(() -> HttpUtils.post(
          Mockito.anyString(), Mockito.anyString(),
          Mockito.anyString(), Mockito.anyInt()))
          .thenThrow(new RuntimeException("network error"));

      UserLicenseRequest request = new UserLicenseRequest();
      TigerResponse response = client.execute(request);
      Assert.assertNotNull(response);
      Assert.assertTrue(response.getCode() != 0);
    }
  }

  /* ---------- buildParams paths via execute ---------- */

  /**
   * Covers the TigerCommonRequest + apiModel path (PrimeAssetRequest is a TRADE method),
   * setDefaultAccountIfAbsent (account empty + defaultAccount set),
   * and setDefaultSecretKey (when secretKey set in config).
   */
  @Test
  public void testExecute_tradeRequestWithApiModel_setsDefaultAccountAndSecretKey() throws Exception {
    // secretKey is on clientConfig, not on client
    ClientConfig cfg = (ClientConfig) getField(client, "clientConfig");
    cfg.secretKey = "sk-xyz";

    try (MockedStatic<HttpUtils> mocked = Mockito.mockStatic(HttpUtils.class)) {
      String json = "{\"code\":0,\"message\":\"ok\",\"timestamp\":1,\"data\":{\"items\":[]}}";
      mocked.when(() -> HttpUtils.post(
          Mockito.anyString(), Mockito.anyString(),
          Mockito.anyString(), Mockito.anyInt()))
          .thenReturn(json);

      // PrimeAssetRequest with empty account → defaultAccount injected by setDefaultAccountIfAbsent,
      // then setDefaultSecretKey fires because account is now non-empty.
      PrimeAssetRequest request = PrimeAssetRequest.buildPrimeAssetRequest("");
      TigerResponse response = client.execute(request);
      Assert.assertNotNull(response);
      // verify default account was set on the model
      Assert.assertEquals("testAccount", request.getApiModel().getAccount());
    }
  }

  /**
   * Covers accessToken/tradeToken/accountType/deviceId branches in buildParams.
   * Verifies that the request reaches HttpUtils.post (meaning all buildParams branches
   * were exercised without throwing) and that the response is non-null.
   */
  @Test
  public void testExecute_buildParamsIncludesAllOptionalFields() {
    client.setAccessToken("at-1");
    client.setTradeToken("tt-2");
    client.setAccountType(AccountType.GLOBAL);
    setFieldNoEx(client, "deviceId", "dev-1");
    setFieldNoEx(client, "tigerId", "testTigerId");
    setFieldNoEx(client, "privateKey", "pk-1");

    try (MockedStatic<HttpUtils> mocked = Mockito.mockStatic(HttpUtils.class);
        MockedStatic<TigerSignature> sigMock = Mockito.mockStatic(TigerSignature.class)) {
      sigMock.when(() -> TigerSignature.getSignContent(Mockito.anyMap())).thenReturn("content");
      sigMock.when(() -> TigerSignature.rsaSign(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
          .thenReturn("signed");

      // Capture params map passed to HttpUtils.post to verify optional fields were included.
      String[] capturedBody = new String[1];
      mocked.when(() -> HttpUtils.post(
          Mockito.anyString(), Mockito.anyString(),
          Mockito.anyString(), Mockito.anyInt()))
          .thenAnswer(inv -> {
            capturedBody[0] = inv.getArgument(1);
            return "{\"code\":0,\"message\":\"ok\",\"timestamp\":1}";
          });

      UserLicenseRequest request = new UserLicenseRequest();
      TigerResponse response = client.execute(request);
      Assert.assertNotNull(response);
      // Verify optional fields were present in the serialized params
      Assert.assertNotNull("request body should not be null", capturedBody[0]);
      Assert.assertTrue("access_token should be in params", capturedBody[0].contains("at-1"));
      Assert.assertTrue("trade_token should be in params", capturedBody[0].contains("tt-2"));
      Assert.assertTrue("account_type should be in params", capturedBody[0].contains("GLOBAL"));
    }
  }

  /**
   * Covers the BatchApiModel branch in buildParams.
   * Verifies that BatchApiModel content is serialized and the response is non-null.
   */
  @Test
  public void testExecute_batchApiModel() throws Exception {
    try (MockedStatic<HttpUtils> mocked = Mockito.mockStatic(HttpUtils.class)) {
      String[] capturedBody = new String[1];
      mocked.when(() -> HttpUtils.post(
          Mockito.anyString(), Mockito.anyString(),
          Mockito.anyString(), Mockito.anyInt()))
          .thenAnswer(inv -> {
            capturedBody[0] = inv.getArgument(1);
            return "{\"code\":0,\"message\":\"ok\",\"timestamp\":1,\"data\":{}}";
          });

      // Build a TigerCommonRequest and set a BatchApiModel as its apiModel via reflection.
      UserLicenseRequest req = new UserLicenseRequest();
      PrimeAssetModel item = new PrimeAssetModel("acct1");
      @SuppressWarnings("unchecked")
      BatchApiModel<ApiModel> batch = new BatchApiModel<>(Collections.singletonList(item));
      Field apiModelField = TigerCommonRequest.class.getDeclaredField("apiModel");
      apiModelField.setAccessible(true);
      apiModelField.set(req, batch);

      TigerResponse response = client.execute(req);
      Assert.assertNotNull(response);
      // Verify the batch model was serialized into the request body
      Assert.assertNotNull("request body should not be null", capturedBody[0]);
      Assert.assertTrue("batch item content (acct1) should be serialized into params",
          capturedBody[0].contains("acct1"));
    }
  }

  /* ---------- sign check path ---------- */

  @Test
  public void testExecute_signCheckPass() {
    setFieldNoEx(client, "tigerPublicKey", "fake-pub-key");
    setFieldNoEx(client, "tigerId", "testTigerId");
    setFieldNoEx(client, "privateKey", "pk");

    try (MockedStatic<HttpUtils> mocked = Mockito.mockStatic(HttpUtils.class);
        MockedStatic<TigerSignature> sigMock = Mockito.mockStatic(TigerSignature.class)) {
      sigMock.when(() -> TigerSignature.getSignContent(Mockito.anyMap())).thenReturn("c");
      sigMock.when(() -> TigerSignature.rsaSign(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
          .thenReturn("s");
      sigMock.when(() -> TigerSignature.rsaCheckContent(
          Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
          .thenReturn(true);

      String json = "{\"code\":0,\"message\":\"ok\",\"timestamp\":1,\"sign\":\"abc\"}";
      mocked.when(() -> HttpUtils.post(
          Mockito.anyString(), Mockito.anyString(),
          Mockito.anyString(), Mockito.anyInt()))
          .thenReturn(json);

      UserLicenseRequest request = new UserLicenseRequest();
      TigerResponse response = client.execute(request);
      Assert.assertNotNull(response);
      Assert.assertEquals(0, response.getCode());
    }
  }

  @Test
  public void testExecute_signCheckFail() {
    setFieldNoEx(client, "tigerPublicKey", "fake-pub-key");
    setFieldNoEx(client, "tigerId", "testTigerId");
    setFieldNoEx(client, "privateKey", "pk");

    try (MockedStatic<HttpUtils> mocked = Mockito.mockStatic(HttpUtils.class);
        MockedStatic<TigerSignature> sigMock = Mockito.mockStatic(TigerSignature.class)) {
      sigMock.when(() -> TigerSignature.getSignContent(Mockito.anyMap())).thenReturn("c");
      sigMock.when(() -> TigerSignature.rsaSign(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
          .thenReturn("s");
      sigMock.when(() -> TigerSignature.rsaCheckContent(
          Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
          .thenReturn(false);

      String json = "{\"code\":0,\"message\":\"ok\",\"timestamp\":1,\"sign\":\"abc\"}";
      mocked.when(() -> HttpUtils.post(
          Mockito.anyString(), Mockito.anyString(),
          Mockito.anyString(), Mockito.anyInt()))
          .thenReturn(json);

      UserLicenseRequest request = new UserLicenseRequest();
      TigerResponse response = client.execute(request);
      Assert.assertNotNull(response);
      Assert.assertTrue(response.getCode() != 0);
    }
  }

  /* ---------- getServerUrl paper-account path ---------- */

  @Test
  public void testExecute_paperAccountRoutesToPaperServerUrl() {
    setFieldNoEx(client, "paperServerUrl", "http://paper:1234");
    setFieldNoEx(client, "tigerId", "testTigerId");
    setFieldNoEx(client, "privateKey", "pk");

    try (MockedStatic<HttpUtils> mocked = Mockito.mockStatic(HttpUtils.class);
        MockedStatic<TigerSignature> sigMock = Mockito.mockStatic(TigerSignature.class)) {
      sigMock.when(() -> TigerSignature.getSignContent(Mockito.anyMap())).thenReturn("c");
      sigMock.when(() -> TigerSignature.rsaSign(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
          .thenReturn("s");
      // capture the url passed to HttpUtils.post to verify paper url used
      String[] usedUrl = new String[1];
      mocked.when(() -> HttpUtils.post(
          Mockito.anyString(), Mockito.anyString(),
          Mockito.anyString(), Mockito.anyInt()))
          .thenAnswer(inv -> {
            usedUrl[0] = inv.getArgument(0);
            return "{\"code\":0,\"message\":\"ok\",\"timestamp\":1,\"data\":{}}";
          });

      // 17-digit numeric account is treated as virtual
      PrimeAssetRequest req = PrimeAssetRequest.buildPrimeAssetRequest("12345678901234567");
      TigerResponse response = client.execute(req);
      Assert.assertNotNull(response);
      Assert.assertEquals("http://paper:1234", usedUrl[0]);
    }
  }

  @Test
  public void testExecute_quoteMethodRoutesToQuoteServerUrl() {
    setFieldNoEx(client, "quoteServerUrl", "http://quote:5678");
    setFieldNoEx(client, "tigerId", "testTigerId");
    setFieldNoEx(client, "privateKey", "pk");

    try (MockedStatic<HttpUtils> mocked = Mockito.mockStatic(HttpUtils.class);
        MockedStatic<TigerSignature> sigMock = Mockito.mockStatic(TigerSignature.class)) {
      sigMock.when(() -> TigerSignature.getSignContent(Mockito.anyMap())).thenReturn("c");
      sigMock.when(() -> TigerSignature.rsaSign(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
          .thenReturn("s");
      String[] usedUrl = new String[1];
      mocked.when(() -> HttpUtils.post(
          Mockito.anyString(), Mockito.anyString(),
          Mockito.anyString(), Mockito.anyInt()))
          .thenAnswer(inv -> {
            usedUrl[0] = inv.getArgument(0);
            return "{\"code\":0,\"message\":\"ok\",\"timestamp\":1,\"data\":{}}";
          });

      TigerHttpRequest req = new TigerHttpRequest(MethodName.BRIEF);
      req.setBizContent("{\"symbols\":[\"AAPL\"]}");
      TigerResponse response = client.execute(req);
      Assert.assertNotNull(response);
      Assert.assertEquals("http://quote:5678", usedUrl[0]);
    }
  }

  /* ---------- init validation errors via reflection ---------- */

  @Test
  public void testInit_tigerIdNull() throws Exception {
    ClientConfig cfg = new ClientConfig();
    cfg.tigerId = null;
    cfg.privateKey = "k";
    setField(client, "clientConfig", cfg);
    try {
      invokeInit(null, "k");
      Assert.fail("expected RuntimeException");
    } catch (java.lang.reflect.InvocationTargetException e) {
      Assert.assertTrue(e.getCause() instanceof RuntimeException);
    }
  }

  @Test
  public void testInit_privateKeyNull() throws Exception {
    ClientConfig cfg = new ClientConfig();
    cfg.tigerId = "tid";
    cfg.privateKey = null;
    setField(client, "clientConfig", cfg);
    try {
      invokeInit("tid", null);
      Assert.fail("expected RuntimeException");
    } catch (java.lang.reflect.InvocationTargetException e) {
      Assert.assertTrue(e.getCause() instanceof RuntimeException);
    }
  }

  /* ---------- refreshUrl via reflection (custom url returns early) ---------- */

  @Test
  public void testRefreshUrl_customUrlReturnsEarly() throws Exception {
    // isCustomServerUrl is already true from setUp
    invokeRefreshUrl();
    // no exception means success
  }

  @Test
  public void testRefreshUrl_throwableCaught() throws Exception {
    // unset custom url and make clientConfig.license null + tigerPublicKey null
    setFieldNoEx(client, "isCustomServerUrl", false);
    // serverUrl stays null → getHttpServerAddress via NetworkUtil may throw;
    // refreshUrl catches Throwable
    invokeRefreshUrl();
  }

  /* ---------- destroy with tokenManager ---------- */

  @Test
  public void testDestroy_withTokenManagerAndExecutor() throws Exception {
    TigerHttpClient c = new TigerHttpClient();
    // inject a mock TokenManager via reflection
    Object mockTm = Mockito.mock(com.tigerbrokers.stock.openapi.client.https.client.TokenManager.class);
    Field tmField = TigerHttpClient.class.getDeclaredField("tokenManager");
    tmField.setAccessible(true);
    tmField.set(c, mockTm);
    c.destroy();
  }

  /* ---------- helpers ---------- */

  private static Object getField(Object target, String name) throws Exception {
    Field f = TigerHttpClient.class.getDeclaredField(name);
    f.setAccessible(true);
    return f.get(target);
  }

  private static void setField(Object target, String name, Object value) throws Exception {
    Field f = TigerHttpClient.class.getDeclaredField(name);
    f.setAccessible(true);
    f.set(target, value);
  }

  private static void setFieldNoEx(Object target, String name, Object value) {
    try {
      Field f = TigerHttpClient.class.getDeclaredField(name);
      f.setAccessible(true);
      f.set(target, value);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void invokeInit(String tigerId, String privateKey) throws Exception {
    Method m = TigerHttpClient.class.getDeclaredMethod("init", String.class, String.class);
    m.setAccessible(true);
    m.invoke(client, tigerId, privateKey);
  }

  private void invokeRefreshUrl() throws Exception {
    Method m = TigerHttpClient.class.getDeclaredMethod("refreshUrl");
    m.setAccessible(true);
    m.invoke(client);
  }
}
