package com.tigerbrokers.stock.openapi.client.testsupport;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.struct.enums.Env;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

/**
 * 测试用 TigerHttpClient 工厂。
 *
 * <p>构造一个能走完 execute() 全链路（签名、构建参数、解析响应）但不出网的 client。
 * 核心思路和 Python 一样：内存生成 RSA 密钥，绕过文件读取和网络。
 *
 * <p>为什么不能只 mock client 本身：我们要测的是「构造 Request → buildParams → 签名
 * → 序列化 → 反序列化 Response → 字段映射」这整条链路。如果只 mock execute()，
 * 就测不到 Request 里的参数拼装和 Response 里的字段映射。
 *
 * <p>唯一需要 mock 的是 {@code HttpUtils.post()}（真正出网的那个调用点）。
 */
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

  /**
   * 获取可用于单测的 TigerHttpClient。
   *
   * <p><b>注意</b>：TigerHttpClient 是进程级单例，这个方法会修改它的状态（tigerId、
   * privateKey、serverUrl）。如果多个测试类并行跑且都调这个方法，理论上有竞争风险，
   * 但 Maven surefire 默认串行执行测试类，所以实际无问题。
   *
   * <p>必须在 {@code MockedStatic<HttpUtils>} 里调用，否则 clientConfig() 里的
   * autoGrabPermission 会真正打网络。这里已经关掉了 isAutoGrabPermission，但
   * refreshUrl() 内的 NetworkUtil 还会调 HttpUtils.get —— 用 mockStatic 拦住即可。
   */
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
