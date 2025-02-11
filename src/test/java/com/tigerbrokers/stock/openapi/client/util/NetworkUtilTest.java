package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.struct.enums.BizType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Env;
import com.tigerbrokers.stock.openapi.client.struct.enums.License;

import io.netty.handler.ssl.SslProvider;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyString;

/**
 * @author liutongping
 * @version 1.0
 * @date 2022/3/9
 */
@RunWith(MockitoJUnitRunner.class)
public class NetworkUtilTest {
  String domainConfigJson;

  @Before
  public void setUp() {
    domainConfigJson = "{\"ret\":0,\"serverTime\":1646652924198,\"items\":["
        + "{\"openapi\":{\"port\":9887,\"socket_port\":9883,"
        + "\"COMMON\":\"https://openapi.tigerfintech.com\",},"
        + "\"openapi-sandbox\":{\"port\":9889,\"socket_port\":9885,"
        + "\"COMMON\":\"https://openapi-sandbox.tigerfintech.com\"}"
        + "}]}";
  }

  @Test
  public void testGetServerAddress() {

    try (MockedStatic<HttpUtils> theMock = Mockito.mockStatic(HttpUtils.class)) {
      ClientConfig config = ClientConfig.DEFAULT_CONFIG;
      theMock.when(() -> HttpUtils.get(anyString(), config.token)).thenReturn(domainConfigJson);
      // theMock.when(HttpUtils::get).thenReturn(cgplayJson);

      // prod
      config.setEnv(Env.PROD);
      config.isSslSocket = false;
      System.out.println("\r\nenv:" + config.getEnv()
          + ", isSslSocket:" + config.isSslSocket);
      System.out.println(NetworkUtil.getHttpServerAddress(config, null));
      Assert.assertEquals("https://openapi.tigerfintech.com/gateway", NetworkUtil.getHttpServerAddress(config, null));
      System.out.println(NetworkUtil.getServerAddress(config, null));
      Assert.assertEquals("wss://openapi.tigerfintech.com:9887/stomp", NetworkUtil.getServerAddress(config, null));
      config.isSslSocket = true;
      System.out.println(NetworkUtil.getServerAddress(config, null));
      Assert.assertEquals("wss://openapi.tigerfintech.com:9883", NetworkUtil.getServerAddress(config, null));

      // SANDBOX
      config.setEnv(Env.SANDBOX);
      config.isSslSocket = false;
      System.out.println("\r\nenv:" + config.getEnv()
          + ", isSslSocket:" + config.isSslSocket);
      System.out.println(NetworkUtil.getHttpServerAddress(config, null));
      Assert.assertEquals("https://openapi-sandbox.tigerfintech.com/gateway", NetworkUtil.getHttpServerAddress(config, null));
      System.out.println(NetworkUtil.getServerAddress(config, null));
      Assert.assertEquals("wss://openapi-sandbox.tigerfintech.com:9889/stomp", NetworkUtil.getServerAddress(config, null));
      config.isSslSocket = true;
      System.out.println(NetworkUtil.getServerAddress(config, null));
      Assert.assertEquals("wss://openapi-sandbox.tigerfintech.com:9885", NetworkUtil.getServerAddress(config, null));

    }
  }

  @Test
  public void testGetServerAddress02() {

    String domainConfigJson02 = "{\"ret\":0,\"serverTime\":1646652924198,\"items\":["
        + "{\"openapi\":{\"socket_port\":9883,\"port\":9887,"
        + "\"COMMON\":\"https://openapi.tigerfintech.com\","
        + "\"TBSG\":\"https://openapi.tigerfintech.com/hkg\","
        + "\"TBNZ\":\"https://openapi.tigerfintech.com/hkg\","
        + "\"TBSG-QUOTE\":\"https://openapi.tigerfintech.com/hkg-quote\","
        + "\"TBNZ-QUOTE\":\"https://openapi.tigerfintech.com/hkg-quote\","
        + "\"TBSG-PAPER\":\"https://openapi-sandbox.tigerfintech.com/hkg\","
        + "\"TBNZ-PAPER\":\"https://openapi-sandbox.tigerfintech.com/hkg\"}"
        + ","
        + "\"openapi-sandbox\":{\"port\":9889,\"socket_port\":9885,"
        + "\"COMMON\":\"https://openapi-sandbox.tigerfintech.com\"}"
        + "}]}";

    try (MockedStatic<HttpUtils> theMock = Mockito.mockStatic(HttpUtils.class)) {
      ClientConfig config = ClientConfig.DEFAULT_CONFIG;
      theMock.when(() -> HttpUtils.get(anyString(), config.token)).thenReturn(domainConfigJson02);
      // theMock.when(HttpUtils::get).thenReturn(cgplayJson);

      // prod
      config.setEnv(Env.PROD);
      config.isSslSocket = false;
      System.out.println("\r\nenv:" + config.getEnv()
          + ", isSslSocket:" + config.isSslSocket);
      System.out.println(NetworkUtil.getHttpServerAddress(config, null));
      Assert.assertEquals("https://openapi.tigerfintech.com/gateway", NetworkUtil.getHttpServerAddress(config, null));
      System.out.println(NetworkUtil.getServerAddress(config, null));
      Assert.assertEquals("wss://openapi.tigerfintech.com:9887/stomp", NetworkUtil.getServerAddress(config, null));

      config.isSslSocket = true;
      System.out.println(NetworkUtil.getServerAddress(config, null));
      Assert.assertEquals("wss://openapi.tigerfintech.com:9883", NetworkUtil.getServerAddress(config, null));

      System.out.println("===========");
      config.license = License.TBNZ;
      // assert url
      Map<BizType, String> urlMap = NetworkUtil.getHttpServerAddress(config, config.license, null);
      System.out.println(urlMap.get(BizType.COMMON));
      Assert.assertEquals("https://openapi.tigerfintech.com/gateway",
          urlMap.get(BizType.COMMON));

      System.out.println(urlMap.get(BizType.TRADE));
      Assert.assertEquals("https://openapi.tigerfintech.com/hkg/gateway",
          urlMap.get(BizType.TRADE));
      System.out.println(urlMap.get(BizType.QUOTE));
      Assert.assertEquals("https://openapi.tigerfintech.com/hkg-quote/gateway",
          urlMap.get(BizType.QUOTE));
      System.out.println(urlMap.get(BizType.PAPER));
      Assert.assertEquals("https://openapi-sandbox.tigerfintech.com/hkg/gateway",
          urlMap.get(BizType.PAPER));
    }
  }
  @Test
  public void testGetServerAddress03() {

    String domainConfigJson03 = "{\"ret\":0,\"serverTime\":1646652924198,\"items\":["
        + "{\"openapi\":{\"socket_port\":9883,\"port\":9887,"
        + "\"COMMON\":\"https://openapi.tigerfintech.com\","
        + "\"TBNZ\":\"https://openapi.tigerfintech.com/hkg\","
        + "\"TBNZ-QUOTE\":\"https://openapi.tigerfintech.com/hkg-quote\","
        + "\"TBNZ-PAPER\":\"https://openapi-sandbox.tigerfintech.com/hkg\"}"
        + ","
        + "\"openapi-sandbox\":{\"port\":9889,\"socket_port\":9885,"
        + "\"COMMON\":\"https://openapi-sandbox.tigerfintech.com\"}"
        + "}]}";

    try (MockedStatic<HttpUtils> theMock = Mockito.mockStatic(HttpUtils.class)) {
      ClientConfig config = ClientConfig.DEFAULT_CONFIG;
      theMock.when(() -> HttpUtils.get(anyString(), config.token)).thenReturn(domainConfigJson03);
      // theMock.when(HttpUtils::get).thenReturn(cgplayJson);

      // prod
      config.setEnv(Env.PROD);
      config.isSslSocket = false;
      System.out.println("\r\nenv:" + config.getEnv()
          + ", isSslSocket:" + config.isSslSocket);
      System.out.println(NetworkUtil.getHttpServerAddress(config, null));
      Assert.assertEquals("https://openapi.tigerfintech.com/gateway", NetworkUtil.getHttpServerAddress(config, null));
      System.out.println(NetworkUtil.getServerAddress(config, null));
      Assert.assertEquals("wss://openapi.tigerfintech.com:9887/stomp", NetworkUtil.getServerAddress(config, null));

      config.isSslSocket = true;
      System.out.println(NetworkUtil.getServerAddress(config, null));
      Assert.assertEquals("wss://openapi.tigerfintech.com:9883", NetworkUtil.getServerAddress(config, null));

      System.out.println("===========");
      config.license = License.TBSG;
      // assert url
      Map<BizType, String> urlMap = NetworkUtil.getHttpServerAddress(config, config.license, null);
      System.out.println(urlMap.get(BizType.COMMON));
      Assert.assertEquals("https://openapi.tigerfintech.com/gateway",
          urlMap.get(BizType.COMMON));

      System.out.println(urlMap.get(BizType.TRADE));
      Assert.assertNull(urlMap.get(BizType.TRADE));
      System.out.println(urlMap.get(BizType.QUOTE));
      Assert.assertNull(urlMap.get(BizType.QUOTE));
      System.out.println(urlMap.get(BizType.PAPER));
      Assert.assertNull(urlMap.get(BizType.PAPER));
    }
  }

  @Test
  public void testGetOpenSslSupportedProtocolsSet() {
    String[] protocols = new String[]{"TLSv1", "TLSv1.1", "TLSv1.2", "TLSv1.3", "TLSv1.4"};
    String[] protocolsJdk = NetworkUtil.getSupportedProtocolsSet(protocols, SslProvider.JDK);
    ApiLogger.info("JDK: {}", protocolsJdk);
    Assert.assertArrayEquals(new String[] {"TLSv1", "TLSv1.1", "TLSv1.2", "TLSv1.3"}, protocolsJdk);
    String[] opensslProtocols = NetworkUtil.getSupportedProtocolsSet(protocols, SslProvider.OPENSSL);
    ApiLogger.info("OPENSSL: {}", opensslProtocols);
    Assert.assertArrayEquals(new String[] {"TLSv1", "TLSv1.1", "TLSv1.2", "TLSv1.3"}, opensslProtocols);
    String[] opensslProtocols2 = NetworkUtil.getSupportedProtocolsSet(protocols, SslProvider.OPENSSL_REFCNT);
    ApiLogger.info("OPENSSL_REFCNT: {}", opensslProtocols2);
    Assert.assertArrayEquals(new String[] {"TLSv1", "TLSv1.1", "TLSv1.2", "TLSv1.3"}, opensslProtocols2);
  }
}
