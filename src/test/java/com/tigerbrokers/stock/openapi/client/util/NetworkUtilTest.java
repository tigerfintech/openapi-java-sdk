package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.struct.enums.Env;
import com.tigerbrokers.stock.openapi.client.struct.enums.Protocol;

import io.netty.handler.ssl.SslProvider;
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
 * @date 2022/3/9 下午5:15
 */
@RunWith(MockitoJUnitRunner.class)
public class NetworkUtilTest {
  String domainConfigJson;

  @Before
  public void setUp() {
    domainConfigJson = "{\"ret\":0,\"serverTime\":1646652924198,\"items\":["
        + "{\"openapi\":{\"port\":8887,\"socket_port\":9883,"
        + "\"COMMON\":\"https://openapi.skytigris.cn\",},"
        + "\"openapi-sandbox\":{\"port\":8889,\"socket_port\":9885,"
        + "\"COMMON\":\"https://openapi-sandbox.skytigris.cn\"}"
        + "}]}";
  }

  @Test
  public void testGetServerAddress() {

    try (MockedStatic<HttpUtils> theMock = Mockito.mockStatic(HttpUtils.class)) {
      theMock.when(() -> HttpUtils.get(anyString())).thenReturn(domainConfigJson);
      // theMock.when(HttpUtils::get).thenReturn(cgplayJson);
      ClientConfig config = ClientConfig.DEFAULT_CONFIG;

      // 测试生产环境
      config.setEnv(Env.PROD);
      config.setSubscribeProtocol(Protocol.STOMP);
      System.out.println("\r\nenv:" + config.getEnv()
          + ", license:" + config.getLicense()
          + ", subscribeProtocal:" + config.getSubscribeProtocol());
      System.out.println(NetworkUtil.getHttpServerAddress());
      Assert.assertEquals("https://openapi.skytigris.cn/gateway", NetworkUtil.getHttpServerAddress());
      System.out.println(NetworkUtil.getServerAddress());
      Assert.assertEquals("wss://openapi.skytigris.cn:8887/stomp", NetworkUtil.getServerAddress());
      config.setSubscribeProtocol(Protocol.SOCKET);
      System.out.println(NetworkUtil.getServerAddress());
      Assert.assertEquals("wss://openapi.skytigris.cn:8883", NetworkUtil.getServerAddress());

      // SANDBOX环境
      config.setEnv(Env.SANDBOX);
      config.setSubscribeProtocol(Protocol.STOMP);
      System.out.println("\r\nenv:" + config.getEnv()
          + ", license:" + config.getLicense()
          + ", subscribeProtocal:" + config.getSubscribeProtocol());
      System.out.println(NetworkUtil.getHttpServerAddress());
      Assert.assertEquals("https://openapi-sandbox.skytigris.cn/gateway", NetworkUtil.getHttpServerAddress());
      System.out.println(NetworkUtil.getServerAddress());
      Assert.assertEquals("wss://openapi-sandbox.skytigris.cn:8889/stomp", NetworkUtil.getServerAddress());
      config.setSubscribeProtocol(Protocol.SOCKET);
      System.out.println(NetworkUtil.getServerAddress());
      Assert.assertEquals("wss://openapi-sandbox.skytigris.cn:8885", NetworkUtil.getServerAddress());

    }
  }

  @Test
  public void testGetOpenSslSupportedProtocolsSet() {
    String[] protocols = new String[]{"TLSv1", "TLSv1.1", "TLSv1.2", "TLSv1.3", "TLSv1.4"};
    String[] protocolsJdk = NetworkUtil.getOpenSslSupportedProtocolsSet(protocols, SslProvider.JDK);
    ApiLogger.info("jdk: {}", protocolsJdk);
    Assert.assertArrayEquals(new String[] {"TLSv1", "TLSv1.1", "TLSv1.2", "TLSv1.3", "TLSv1.4"}, protocolsJdk);
    String[] protocols2 = NetworkUtil.getOpenSslSupportedProtocolsSet(protocols, SslProvider.OPENSSL);
    ApiLogger.info("OPENSSL: {}", protocols2);
    Assert.assertArrayEquals(new String[] {"TLSv1", "TLSv1.1", "TLSv1.2", "TLSv1.3"}, protocols2);
  }
}
