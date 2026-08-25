package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.struct.enums.BizType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Env;
import com.tigerbrokers.stock.openapi.client.struct.enums.License;

import io.netty.handler.ssl.OpenSsl;
import io.netty.handler.ssl.SslProvider;
import java.util.Arrays;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;

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
      theMock.when(() -> HttpUtils.get(anyString(), nullable(String.class))).thenReturn(domainConfigJson);
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
      theMock.when(() -> HttpUtils.get(anyString(), nullable(String.class))).thenReturn(domainConfigJson02);
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
      theMock.when(() -> HttpUtils.get(anyString(), nullable(String.class))).thenReturn(domainConfigJson03);
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

  /**
   * 断言的是过滤契约，不是某个平台上的具体协议列表。
   *
   * 本地支持的协议集随 JDK 版本和 netty native 库变化：JDK 8 早期版本没有 TLSv1.3，
   * 新版本又默认禁用 TLSv1 / TLSv1.1；OpenSSL provider 在缺少对应平台 native 库时
   * （如 macOS aarch64、精简 CI 镜像）拿不到协议集，按约定返回 null。
   * 断言固定数组会让这个用例的结果取决于跑在哪台机器上。
   */
  @Test
  public void testGetOpenSslSupportedProtocolsSet() {
    String[] serverProtocols = new String[] {"TLSv1", "TLSv1.1", "TLSv1.2", "TLSv1.3", "TLSv1.4"};

    // JDK provider 总是可用：服务端声明的协议里，本地不认识的必须被过滤掉
    String[] protocolsJdk = NetworkUtil.getSupportedProtocolsSet(serverProtocols, SslProvider.JDK);
    ApiLogger.info("JDK: {}", protocolsJdk);
    Assert.assertNotNull("JDK provider 应始终可用", protocolsJdk);
    Assert.assertFalse("不存在的 TLSv1.4 应被过滤掉", Arrays.asList(protocolsJdk).contains("TLSv1.4"));
    Assert.assertTrue("TLSv1.2 在 JDK 8+ 上应始终受支持", Arrays.asList(protocolsJdk).contains("TLSv1.2"));

    // OpenSSL provider 依赖 netty native，不可用时约定返回 null
    for (SslProvider provider : new SslProvider[] {SslProvider.OPENSSL, SslProvider.OPENSSL_REFCNT}) {
      String[] result = NetworkUtil.getSupportedProtocolsSet(serverProtocols, provider);
      ApiLogger.info("{}: {}", provider, result);
      if (OpenSsl.isAvailable()) {
        Assert.assertNotNull(provider + " 可用时不应返回 null", result);
        Assert.assertFalse("不存在的 TLSv1.4 应被过滤掉", Arrays.asList(result).contains("TLSv1.4"));
      } else {
        Assert.assertNull(provider + " 不可用时约定返回 null", result);
      }
    }
  }

  @Test
  public void testGetSupportedProtocolsSetWithEmptyInput() {
    Assert.assertNull(NetworkUtil.getSupportedProtocolsSet(null, SslProvider.JDK));
    String[] empty = new String[0];
    Assert.assertSame(empty, NetworkUtil.getSupportedProtocolsSet(empty, SslProvider.JDK));
  }
}
