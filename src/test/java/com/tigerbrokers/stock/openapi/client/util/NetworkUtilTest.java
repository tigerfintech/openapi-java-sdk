package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.struct.enums.Env;
import com.tigerbrokers.stock.openapi.client.struct.enums.License;
import com.tigerbrokers.stock.openapi.client.struct.enums.Protocol;

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
  String cgplayJson;

  @Before
  public void setUp() {
    cgplayJson = "{\"ret\":0,\"serverTime\":1646652924198,\"items\":["
        + "{\"openapi\":{\"port\":8887,\"socket_port\":8883,"
        + "\"COMMON\":\"https://openapi.skytigris.cn\","
        + "\"TBNZ\":\"https://openapi.skytigris.nz\","
        + "\"TBSG\":\"https://openapi.skytigris.sg\"},"
        + "\"openapi-sandbox\":{\"port\":8889,\"socket_port\":8885,"
        + "\"COMMON\":\"https://openapi-sandbox.skytigris.cn\"},"
        + "\"cash-plus\":\"https://cash-plus-api.skytigris.cn\"}]}";

  }

  @Test
  public void testGetServerAddress() {

    try (MockedStatic<HttpUtils> theMock = Mockito.mockStatic(HttpUtils.class)) {
      theMock.when(() -> HttpUtils.get(anyString())).thenReturn(cgplayJson);
      // theMock.when(HttpUtils::get).thenReturn(cgplayJson);
      ClientConfig config = ClientConfig.DEFAULT_CONFIG;

      // 测试生产环境，TBNZ牌照
      config.setEnv(Env.PROD);
      config.setLicense(License.TBNZ);
      config.setSubscribeProtocol(Protocol.STOMP);
      System.out.println("\r\nenv:" + config.getEnv()
          + ", license:" + config.getLicense()
          + ", subscribeProtocal:" + config.getSubscribeProtocol());
      System.out.println(NetworkUtil.getHttpServerAddress());
      Assert.assertEquals("https://openapi.skytigris.nz/gateway", NetworkUtil.getHttpServerAddress());
      System.out.println(NetworkUtil.getServerAddress());
      Assert.assertEquals("wss://openapi.skytigris.nz:8887/stomp", NetworkUtil.getServerAddress());
      config.setSubscribeProtocol(Protocol.SOCKET);
      System.out.println(NetworkUtil.getServerAddress());
      Assert.assertEquals("wss://openapi.skytigris.nz:8883", NetworkUtil.getServerAddress());

      // 测试生产环境，TBSG牌照
      config.setEnv(Env.PROD);
      config.setLicense(License.TBSG);
      config.setSubscribeProtocol(Protocol.STOMP);
      System.out.println("\r\nenv:" + config.getEnv()
          + ", license:" + config.getLicense()
          + ", subscribeProtocal:" + config.getSubscribeProtocol());
      System.out.println(NetworkUtil.getHttpServerAddress());
      Assert.assertEquals("https://openapi.skytigris.sg/gateway", NetworkUtil.getHttpServerAddress());
      System.out.println(NetworkUtil.getServerAddress());
      Assert.assertEquals("wss://openapi.skytigris.sg:8887/stomp", NetworkUtil.getServerAddress());
      config.setSubscribeProtocol(Protocol.SOCKET);
      System.out.println(NetworkUtil.getServerAddress());
      Assert.assertEquals("wss://openapi.skytigris.sg:8883", NetworkUtil.getServerAddress());

      config.setEnv(Env.SANDBOX);
      config.setLicense(License.TBNZ);
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

      config.setEnv(Env.SANDBOX);
      config.setLicense(License.TBSG);
      config.setSubscribeProtocol(Protocol.STOMP);
      System.out.println("\r\nenv:" + config.getEnv()
          + ", license:" + config.getLicense()
          + ", subscribeProtocal:" + config.getSubscribeProtocol());
      System.out.println(NetworkUtil.getHttpServerAddress());
      System.out.println(NetworkUtil.getHttpServerAddress());
      Assert.assertEquals("https://openapi-sandbox.skytigris.cn/gateway", NetworkUtil.getHttpServerAddress());
      System.out.println(NetworkUtil.getServerAddress());
      Assert.assertEquals("wss://openapi-sandbox.skytigris.cn:8889/stomp", NetworkUtil.getServerAddress());
      config.setSubscribeProtocol(Protocol.SOCKET);
      System.out.println(NetworkUtil.getServerAddress());
      Assert.assertEquals("wss://openapi-sandbox.skytigris.cn:8885", NetworkUtil.getServerAddress());
    }
  }
}
