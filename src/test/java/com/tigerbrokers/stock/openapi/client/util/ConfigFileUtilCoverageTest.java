package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.struct.enums.Env;
import com.tigerbrokers.stock.openapi.client.struct.enums.License;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.Test;

public class ConfigFileUtilCoverageTest {

  @Test
  public void testLoadConfigFile_valid() throws Exception {
    Path dir = Files.createTempDirectory("cfg");
    Path configFile = dir.resolve("tiger_openapi_config.properties");
    try (FileWriter fw = new FileWriter(configFile.toFile())) {
      fw.write("tiger_id=12345678\n");
      fw.write("private_key_pk8=dGVzdEtleQ==\n");
      fw.write("account=test_acct\n");
      fw.write("license=free\n");
      fw.write("env=prod\n");
      fw.write("secret_key=secret123\n");
    }

    ClientConfig config = new ClientConfig();
    config.configFilePath = dir.toString();
    ConfigFileUtil.loadConfigFile(config);
    Assert.assertEquals("12345678", config.tigerId);
    Assert.assertEquals("dGVzdEtleQ==", config.privateKey);
    Assert.assertEquals("test_acct", config.defaultAccount);
    Assert.assertEquals("secret123", config.secretKey);

    Files.deleteIfExists(configFile);
    Files.deleteIfExists(dir);
  }

  @Test
  public void testLoadConfigFile_missingDir() {
    ClientConfig config = new ClientConfig();
    config.configFilePath = "/nonexistent/path";
    ConfigFileUtil.loadConfigFile(config);
    // should not crash
  }

  @Test
  public void testUpdateTokenFile() throws Exception {
    Path dir = Files.createTempDirectory("tok");
    Path tokenFile = dir.resolve("tiger_openapi_token.properties");
    Files.createFile(tokenFile);
    boolean result = ConfigFileUtil.updateTokenFile(dir.toString(), "tiger_openapi_token.properties", "newToken123");
    Assert.assertTrue(result);
    Files.deleteIfExists(tokenFile);
    Files.deleteIfExists(dir);
  }

  @Test
  public void testUpdateTokenFile_emptyToken() {
    boolean result = ConfigFileUtil.updateTokenFile("/tmp", "token", "");
    Assert.assertFalse(result);
  }

  @Test
  public void testUpdateTokenFile_missingDir() {
    boolean result = ConfigFileUtil.updateTokenFile("/nonexistent", "token", "tok");
    Assert.assertFalse(result);
  }

  @Test
  public void testGetCreateTime_valid() {
    // token format: base64(timestamp + SEPARATOR + expiry)
    String token = java.util.Base64.getEncoder().encodeToString("1234567890,1234567890123".getBytes());
    long createTime = ConfigFileUtil.getCreateTime(token);
    Assert.assertEquals(1234567890L, createTime);
  }

  @Test
  public void testGetCreateTime_noSeparator() {
    String token = java.util.Base64.getEncoder().encodeToString("1234567890".getBytes());
    long createTime = ConfigFileUtil.getCreateTime(token);
    Assert.assertEquals(0, createTime);
  }

  @Test
  public void testTryGetCreateTime_empty() {
    Assert.assertEquals(0, ConfigFileUtil.tryGetCreateTime(""));
    Assert.assertEquals(0, ConfigFileUtil.tryGetCreateTime(null));
  }

  @Test
  public void testTryGetCreateTime_invalid() {
    Assert.assertEquals(0, ConfigFileUtil.tryGetCreateTime("not_base64!!!"));
  }

  @Test
  public void testGetExpiredTime_valid() {
    String token = java.util.Base64.getEncoder().encodeToString("1234567890,1234567890123".getBytes());
    long expiredTime = ConfigFileUtil.getExpiredTime(token);
    Assert.assertEquals(1234567890123L, expiredTime);
  }

  @Test
  public void testGetExpiredTime_noSeparator() {
    String token = java.util.Base64.getEncoder().encodeToString("1234567890".getBytes());
    long expiredTime = ConfigFileUtil.getExpiredTime(token);
    Assert.assertEquals(0, expiredTime);
  }

  @Test
  public void testReadPrivateKey() throws Exception {
    Path tmp = Files.createTempFile("key", ".pem");
    try (FileWriter fw = new FileWriter(tmp.toFile())) {
      fw.write("-----BEGIN " + "PRIVATE KEY-----\ndGVzdEtleQ==\n-----END PRIVATE KEY-----");
    }
    String key = ConfigFileUtil.readPrivateKey(tmp.toString());
    Assert.assertEquals("dGVzdEtleQ==", key);
    Files.deleteIfExists(tmp);
  }

  @Test
  public void testReadPrivateKey_missingFile() {
    String key = ConfigFileUtil.readPrivateKey("/nonexistent/key.pem");
    Assert.assertEquals("", key);
  }
}
