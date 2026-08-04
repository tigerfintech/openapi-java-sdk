package com.tigerbrokers.stock.openapi.client.testsupport;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.struct.enums.Env;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

/** 测试用 TigerHttpClient 工厂。. */
public final class TestClientFactory {

  /** PKCS8 DER 格式私钥的 base64，TigerSignature.rsaSign 需要这个格式。 */
  private static final String TEST_PRIVATE_KEY;
  private static final String TEST_TIGER_ID = "00000000";
  private static final String TEST_ACCOUNT = "00000000000000000";
  private static final String TEST_SERVER_URL = "http://localhost:0/gateway";

  static {
    try {
      KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
      gen.initialize(2048);
      KeyPair pair = gen.generateKeyPair();
      TEST_PRIVATE_KEY = Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());
    } catch (Exception e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  private TestClientFactory() {
  }

  /** 获取可用于单测的 TigerHttpClient。. */
  public static TigerHttpClient createOfflineClient() {
    ClientConfig config = new ClientConfig();
    config.tigerId = TEST_TIGER_ID;
    config.privateKey = TEST_PRIVATE_KEY;
    config.defaultAccount = TEST_ACCOUNT;
    config.isAutoGrabPermission = false;
    config.isAutoRefreshToken = false;
    config.failRetryCounts = 0;
    config.setEnv(Env.SANDBOX);  // 用 SANDBOX 公钥，避免签名校验问题

    TigerHttpClient client = TigerHttpClient.getInstance();
    client.useCustomServerUrl(TEST_SERVER_URL);  // 必须在 clientConfig 之前
    client.clientConfig(config);
    return client;
  }

  public static String testAccount() {
    return TEST_ACCOUNT;
  }
}
