package com.tigerbrokers.stock.openapi.client.config;

import com.tigerbrokers.stock.openapi.client.struct.enums.Env;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import org.junit.Assert;
import org.junit.Test;

public class ClientConfigTest {

  @Test
  public void testDefaultConfig() {
    ClientConfig config = ClientConfig.DEFAULT_CONFIG;
    Assert.assertNotNull(config);
    Assert.assertNotNull(config.getDefaultTimeZone());
    Assert.assertNotNull(config.getDefaultLanguage());
    Assert.assertNotNull(config.getEnv());
  }

  @Test
  public void testSetEnv() {
    ClientConfig config = new ClientConfig();
    config.setEnv(Env.PROD);
    Assert.assertEquals(Env.PROD, config.getEnv());
    config.setEnv(Env.SANDBOX);
    Assert.assertEquals(Env.SANDBOX, config.getEnv());
  }

  @Test
  public void testFields() {
    ClientConfig config = new ClientConfig();
    config.tigerId = "12345678";
    config.privateKey = "privateKey";
    config.defaultAccount = "acct";
    config.secretKey = "secret";
    config.token = "tok";
    config.configFilePath = "/path";

    Assert.assertEquals("12345678", config.tigerId);
    Assert.assertEquals("privateKey", config.privateKey);
    Assert.assertEquals("acct", config.defaultAccount);
    Assert.assertEquals("secret", config.secretKey);
    Assert.assertEquals("tok", config.token);
    Assert.assertEquals("/path", config.configFilePath);
  }

  @Test
  public void testDefaultTimeZoneAndLanguage() {
    ClientConfig config = new ClientConfig();
    Assert.assertNotNull(config.getDefaultTimeZone());
    Assert.assertNotNull(config.getDefaultLanguage());
  }
}
