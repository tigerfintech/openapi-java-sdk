package com.tigerbrokers.stock.openapi.client.https.client;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.user.item.UserTokenItem;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TokenManagerTest {

  private TigerHttpClient client;
  private ClientConfig config;

  @Before
  public void setUp() throws Exception {
    client = new TigerHttpClient();
    config = new ClientConfig();
    config.tigerId = "t";
    config.privateKey = "k";
    config.isAutoGrabPermission = false;
    config.isAutoRefreshToken = false;

    Field f = TigerHttpClient.class.getDeclaredField("clientConfig");
    f.setAccessible(true);
    f.set(client, config);
  }

  static class CB implements RefreshTokenCallback {
    @Override
    public void tokenChange(ClientConfig config, String oldToken, UserTokenItem newTokenItem) {
    }
  }

  @Test
  public void testRegister() {
    TokenManager mgr = new TokenManager(client);
    Assert.assertEquals(0, mgr.getCallbackList().size());
    CB cb = new CB();
    mgr.register(cb);
    Assert.assertEquals(1, mgr.getCallbackList().size());
  }

  @Test
  public void testRegisterNull() {
    TokenManager mgr = new TokenManager(client);
    mgr.register(null);
    Assert.assertEquals(0, mgr.getCallbackList().size());
  }

  @Test
  public void testRegisterDup() {
    TokenManager mgr = new TokenManager(client);
    CB cb = new CB();
    mgr.register(cb);
    mgr.register(cb);
    Assert.assertEquals(1, mgr.getCallbackList().size());
  }

  @Test
  public void testUnregister() {
    TokenManager mgr = new TokenManager(client);
    CB cb = new CB();
    mgr.register(cb);
    mgr.unregister(cb);
    Assert.assertEquals(0, mgr.getCallbackList().size());
  }

  @Test
  public void testDestroy() {
    TokenManager mgr = new TokenManager(client);
    mgr.register(new CB());
    mgr.destroy();
    Assert.assertEquals(0, mgr.getCallbackList().size());
  }

  @Test
  public void testLoadTokenMissing() {
    config.configFilePath = "/none";
    TokenManager mgr = new TokenManager(client);
    Assert.assertFalse(mgr.loadTokenFile(config));
  }

  @Test
  public void testLoadTokenExists() throws Exception {
    Path dir = Files.createTempDirectory("tk");
    Path tokenFile = dir.resolve("tiger_openapi_token.properties");
    Files.write(tokenFile, "token=tok123".getBytes());
    try {
      config.configFilePath = dir.toString();
      TokenManager mgr = new TokenManager(client);
      Assert.assertTrue(mgr.loadTokenFile(config));
      Assert.assertEquals("tok123", config.token);
    } finally {
      Files.deleteIfExists(tokenFile);
      Files.deleteIfExists(dir);
    }
  }

  @Test
  public void testDefaultCallback() {
    DefaultRefreshTokenCallback cb = new DefaultRefreshTokenCallback();
    config.token = "old";
    config.configFilePath = null;
    UserTokenItem item = new UserTokenItem();
    item.setToken("new");
    cb.tokenChange(config, "old", item);
    Assert.assertEquals("new", config.token);
  }
}
