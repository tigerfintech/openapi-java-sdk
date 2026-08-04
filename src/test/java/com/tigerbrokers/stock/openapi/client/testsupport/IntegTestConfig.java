package com.tigerbrokers.stock.openapi.client.testsupport;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.struct.enums.Env;
import org.junit.Assert;

/**
 * Shared setup for integration tests. Reads credentials from environment variables
 * (TIGEROPEN_TIGER_ID, TIGEROPEN_PRIVATE_KEY, TIGEROPEN_ACCOUNT) or falls back to
 * config file via -Dtest.config.path / TIGEROPEN_PROPS_PATH.
 */
public final class IntegTestConfig {

  private static final String DEFAULT_SERVER_URL =
      "https://" + TigerApiConstants.API_ONLINE_DOMAIN_URL + "/gateway";

  private static ClientConfig lastConfig;

  private IntegTestConfig() {}

  public static TigerHttpClient createClient() {
    ClientConfig config = new ClientConfig();

    String tigerId = env("TIGEROPEN_TIGER_ID");
    String privateKey = env("TIGEROPEN_PRIVATE_KEY");
    String account = env("TIGEROPEN_ACCOUNT");

    if (tigerId != null && privateKey != null) {
      config.tigerId = tigerId;
      config.privateKey = privateKey;
      config.defaultAccount = account;
    } else {
      String configPath = System.getProperty("test.config.path");
      if (configPath == null || configPath.isEmpty()) {
        configPath = env("TIGEROPEN_PROPS_PATH");
      }
      Assert.assertNotNull(
          "Set TIGEROPEN_TIGER_ID+TIGEROPEN_PRIVATE_KEY env vars, or -Dtest.config.path",
          configPath);
      config.configFilePath = configPath;
    }

    String envStr = System.getProperty("test.env", "PROD");
    try {
      config.setEnv(Env.valueOf(envStr.trim().toUpperCase()));
    } catch (IllegalArgumentException e) {
      config.setEnv(Env.PROD);
    }
    config.isAutoGrabPermission = false;
    config.isAutoRefreshToken = false;

    TigerHttpClient client = TigerHttpClient.getInstance();

    String serverUrl = System.getProperty("test.server.url");
    if (serverUrl == null || serverUrl.isEmpty()) {
      serverUrl = env("TIGEROPEN_SERVER_URL");
    }
    if (serverUrl == null || serverUrl.isEmpty()) {
      serverUrl = DEFAULT_SERVER_URL;
    }
    client.useCustomServerUrl(serverUrl);
    client.clientConfig(config);

    lastConfig = config;
    return client;
  }

  public static String getAccount() {
    String account = System.getProperty("test.account");
    if (account == null || account.isEmpty()) {
      account = env("TIGEROPEN_ACCOUNT");
    }
    if (account == null || account.isEmpty() && lastConfig != null) {
      account = lastConfig.defaultAccount;
    }
    return account;
  }

  private static String env(String key) {
    String val = System.getenv(key);
    return (val != null && !val.isEmpty()) ? val : null;
  }
}
