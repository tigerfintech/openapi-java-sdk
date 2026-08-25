package com.tigerbrokers.stock.openapi.client.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests TigerSignature rsaSign and rsaCheckContent with a generated RSA key pair.
 */
public class TigerSignatureRsaTest {

  private static String privateKeyStr;
  private static String publicKeyStr;

  @BeforeClass
  public static void setUp() throws Exception {
    KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
    kpg.initialize(2048);
    KeyPair kp = kpg.generateKeyPair();
    PrivateKey privateKey = kp.getPrivate();
    PublicKey publicKey = kp.getPublic();
    privateKeyStr = java.util.Base64.getEncoder().encodeToString(privateKey.getEncoded());
    publicKeyStr = java.util.Base64.getEncoder().encodeToString(publicKey.getEncoded());
  }

  @Test
  public void testRsaSign_andCheckContent() throws Exception {
    String content = "test content for signing";
    String charset = "UTF-8";
    String sign = TigerSignature.rsaSign(content, privateKeyStr, charset);
    Assert.assertNotNull(sign);
    Assert.assertFalse(sign.isEmpty());
    boolean verified = TigerSignature.rsaCheckContent(content, sign, publicKeyStr, charset);
    Assert.assertTrue(verified);
  }

  @Test
  public void testRsaSign_withWrappedKey() throws Exception {
    String wrappedKey = "-----BEGIN " + "PRIVATE KEY-----\n" + privateKeyStr + "\n-----END PRIVATE KEY-----";
    String sign = TigerSignature.rsaSign("content", wrappedKey, "UTF-8");
    Assert.assertNotNull(sign);
  }

  @Test
  public void testRsaSign_noCharset() throws Exception {
    String sign = TigerSignature.rsaSign("test", privateKeyStr, null);
    Assert.assertNotNull(sign);
  }

  @Test
  public void testRsaCheckContent_wrongContent() throws Exception {
    String sign = TigerSignature.rsaSign("original content", privateKeyStr, "UTF-8");
    boolean verified = TigerSignature.rsaCheckContent("different content", sign, publicKeyStr, "UTF-8");
    Assert.assertFalse(verified);
  }

  @Test
  public void testRsaCheckContent_withEscapedChars() throws Exception {
    String content = "http:\\/\\/example.com";
    String sign = TigerSignature.rsaSign(content, privateKeyStr, "UTF-8");
    // Test with escaped chars — should try the replaced version
    boolean verified = TigerSignature.rsaCheckContent(content, sign, publicKeyStr, "UTF-8");
    Assert.assertTrue(verified);
  }

  @Test
  public void testRsaSign_withEndKeyOnly() throws Exception {
    String wrappedKey = privateKeyStr + "\n-----END PRIVATE KEY-----";
    String sign = TigerSignature.rsaSign("content", wrappedKey, "UTF-8");
    Assert.assertNotNull(sign);
  }
}
