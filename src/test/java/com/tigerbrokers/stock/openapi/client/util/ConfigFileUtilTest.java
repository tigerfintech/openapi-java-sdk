package com.tigerbrokers.stock.openapi.client.util;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Collections;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

public class ConfigFileUtilTest {

  @Test
  public void testProcessPrivateKey_withHeaders() {
    String pem = "-----BEGIN " + "PRIVATE KEY-----\ndGVzdEtleQ==\n-----END PRIVATE KEY-----";
    Assert.assertEquals("dGVzdEtleQ==", ConfigFileUtil.processPrivateKey(pem));
  }
  @Test
  public void testProcessPrivateKey_noHeaders() {
    Assert.assertEquals("dGVzdEtleQ==", ConfigFileUtil.processPrivateKey("dGVzdEtleQ=="));
  }
  @Test
  public void testProcessPrivateKey_emptyString() {
    Assert.assertEquals("", ConfigFileUtil.processPrivateKey(""));
  }
  @Test
  public void testProcessPrivateKey_withSpacesAndNewlines() {
    String pem = "-----BEGIN " + "PRIVATE KEY-----\n  M I I E v Q  \r\n-----END PRIVATE KEY-----";
    Assert.assertEquals("MIIEvQ", ConfigFileUtil.processPrivateKey(pem));
  }
  @Test
  public void testReadPropertiesFile_basic() throws Exception {
    Path tmp = Files.createTempFile("test_config", ".properties");
    try (FileWriter fw = new FileWriter(tmp.toFile())) {
      fw.write("tiger_id=12345678\n");
      fw.write("# comment line\n");
      fw.write("\n");
      fw.write("account=test_account\n");
      fw.write("invalidline\n");
      fw.write("key_without_value=\n");
    }
    Map<String, String> result = ConfigFileUtil.readPropertiesFile(tmp);
    Assert.assertEquals("12345678", result.get("tiger_id"));
    Assert.assertEquals("test_account", result.get("account"));
    Assert.assertNull(result.get("invalidline"));
    Files.deleteIfExists(tmp);
  }
  @Test
  public void testReadPropertiesFile_withIncludeKeys() throws Exception {
    Path tmp = Files.createTempFile("test_config2", ".properties");
    try (FileWriter fw = new FileWriter(tmp.toFile())) {
      fw.write("tiger_id=12345678\n");
      fw.write("account=test_account\n");
      fw.write("license=free\n");
    }
    Set<String> includeKeys = Collections.singleton("tiger_id");
    Map<String, String> result = ConfigFileUtil.readPropertiesFile(tmp, includeKeys);
    Assert.assertEquals("12345678", result.get("tiger_id"));
    Assert.assertNull(result.get("account"));
    Files.deleteIfExists(tmp);
  }
  @Test
  public void testGetCreateTime_validToken() {
    String token = java.util.Base64.getEncoder().encodeToString("1234567890,abc".getBytes());
    Assert.assertEquals(1234567890L, ConfigFileUtil.getCreateTime(token));
  }
  @Test
  public void testGetCreateTime_noSeparator() {
    String token = java.util.Base64.getEncoder().encodeToString("noseparator".getBytes());
    Assert.assertEquals(0, ConfigFileUtil.getCreateTime(token));
  }
  @Test
  public void testGetExpiredTime_validToken() {
    String raw = "1234567890,1234567890123abc";
    String token = java.util.Base64.getEncoder().encodeToString(raw.getBytes());
    Assert.assertEquals(1234567890123L, ConfigFileUtil.getExpiredTime(token));
  }
  @Test
  public void testGetExpiredTime_noSeparator() {
    String token = java.util.Base64.getEncoder().encodeToString("noseparator".getBytes());
    Assert.assertEquals(0, ConfigFileUtil.getExpiredTime(token));
  }
  @Test
  public void testTryGetCreateTime_emptyToken() {
    Assert.assertEquals(0, ConfigFileUtil.tryGetCreateTime(""));
    Assert.assertEquals(0, ConfigFileUtil.tryGetCreateTime(null));
  }
  @Test
  public void testTryGetCreateTime_invalidBase64() {
    Assert.assertEquals(0, ConfigFileUtil.tryGetCreateTime("!!!invalidbase64!!!"));
  }
  @Test
  public void testCheckFile_nullDir() {
    Assert.assertFalse(ConfigFileUtil.checkFile(null, "file.txt", false));
  }
  @Test
  public void testCheckFile_emptyDir() {
    Assert.assertFalse(ConfigFileUtil.checkFile("  ", "file.txt", false));
  }
  @Test
  public void testCheckFile_nonExistentDir() {
    Assert.assertFalse(ConfigFileUtil.checkFile("/nonexistent_dir_12345", "file.txt", false));
  }
  @Test
  public void testCheckFile_validReadableFile() throws Exception {
    Path tmpDir = Files.createTempDirectory("test_dir");
    Path tmpFile = tmpDir.resolve("test.txt");
    Files.write(tmpFile, "hello".getBytes());
    Assert.assertTrue(ConfigFileUtil.checkFile(tmpDir.toString(), "test.txt", false));
    Files.deleteIfExists(tmpFile);
    Files.deleteIfExists(tmpDir);
  }
  @Test
  public void testCheckFile_nonExistentFile() throws Exception {
    Path tmpDir = Files.createTempDirectory("test_dir2");
    Assert.assertFalse(ConfigFileUtil.checkFile(tmpDir.toString(), "nonexistent.txt", false));
    Files.deleteIfExists(tmpDir);
  }
  @Test
  public void testUpdateTokenFile_emptyToken() {
    Assert.assertFalse(ConfigFileUtil.updateTokenFile("/tmp", "token.txt", ""));
    Assert.assertFalse(ConfigFileUtil.updateTokenFile("/tmp", "token.txt", null));
  }
  @Test
  public void testUpdateTokenFile_valid() throws Exception {
    Path tmpDir = Files.createTempDirectory("test_token_dir");
    Path tmpFile = tmpDir.resolve("token.txt");
    Files.write(tmpFile, "old".getBytes());
    boolean result = ConfigFileUtil.updateTokenFile(tmpDir.toString(), "token.txt", "mytoken123");
    Assert.assertTrue(result);
    String content = new String(Files.readAllBytes(tmpFile));
    Assert.assertTrue(content.contains("token=mytoken123"));
    Files.deleteIfExists(tmpFile);
    Files.deleteIfExists(tmpDir);
  }
  @Test
  public void testReadPrivateKey_nonExistentFile() {
    Assert.assertEquals("", ConfigFileUtil.readPrivateKey("/nonexistent/key/file.pem"));
  }
}
