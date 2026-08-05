package com.tigerbrokers.stock.openapi.client.https.client;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.request.user.UserLicenseRequest;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.AccountType;
import com.tigerbrokers.stock.openapi.client.util.HttpUtils;
import java.lang.reflect.Field;
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
}
