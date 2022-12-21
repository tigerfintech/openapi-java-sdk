package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;
import com.tigerbrokers.stock.openapi.client.util.codec.Base64;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by lijiawen on 2018/04/25.
 */
public class TigerSignature {

  private static final String PRIVATE_KEY_BEGIN = "-----BEGIN PRIVATE KEY-----";
  private static final String PRIVATE_KEY_END = "-----END PRIVATE KEY-----";

  /**
   * 获取签名内容
   *
   * @param request 签名字段
   * @return 签名字符串
   */
  public static String getSignContent(Map<String, Object> request) {
    StringBuilder content = new StringBuilder();
    List<String> keys = new ArrayList<>(request.keySet());
    Collections.sort(keys);
    int index = 0;
    for (int i = 0; i < keys.size(); i++) {
      String key = keys.get(i);
      Object value = request.get(key);
      String strValue;
      if (value instanceof String) {
        strValue = (String) value;
      } else {
        strValue = value == null ? null : value.toString();
      }
      if (StringUtils.areNotEmpty(key, strValue)) {
        content.append((index == 0 ? "" : "&")).append(key).append("=").append(value);
        index++;
      }
    }
    return content.toString();
  }

  /**
   * 使用私钥签名
   *
   * @param content 待签参数
   * @param privateKey 私钥
   * @param charset 字符集，如UTF-8, GBK, GB2312
   * @return 签名后的内容
   */
  public static String rsaSign(String content, String privateKey, String charset) {
    if (privateKey.startsWith("-")) {
      if (privateKey.contains(PRIVATE_KEY_BEGIN)) {
        privateKey = privateKey.replace(PRIVATE_KEY_BEGIN, "");
      }
      if (privateKey.contains(PRIVATE_KEY_END)) {
        privateKey = privateKey.replace(PRIVATE_KEY_END, "");
      }
    }
    try {
      PrivateKey priKey =
          getPrivateKey(TigerApiConstants.SIGN_TYPE_RSA, new ByteArrayInputStream(privateKey.getBytes()));
      Signature signature = Signature.getInstance(TigerApiConstants.SIGN_ALGORITHMS);
      signature.initSign(priKey);

      if (StringUtils.isEmpty(charset)) {
        signature.update(content.getBytes());
      } else {
        signature.update(content.getBytes(charset));
      }
      byte[] signed = signature.sign();
      return new String(Base64.encodeBase64(signed));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static PrivateKey getPrivateKey(String algorithm, InputStream ins) throws Exception {
    if (ins == null || StringUtils.isEmpty(algorithm)) {
      return null;
    }

    KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
    byte[] encodedKey = StreamUtil.readText(ins).getBytes();
    encodedKey = Base64.decodeBase64(encodedKey);

    return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
  }

  public static boolean rsaCheckContent(String content, String sign, String publicKey, String charset)
      throws TigerApiException {
    boolean rsaCheckContent = rsaCheck(content, sign, publicKey, charset);
    if (!rsaCheckContent && (content.contains("\\/") || content.contains("\\\""))) {
      String source = content.replace("\\/", "/").replace("\\\"", "\"");
      return rsaCheck(source, sign, publicKey, charset);
    }

    return rsaCheckContent;
  }

  private static boolean rsaCheck(String content, String sign, String publicKey, String charset)
      throws TigerApiException {
    try {
      PublicKey pubKey = getPublicKey("RSA", new ByteArrayInputStream(publicKey.getBytes()));
      Signature signature = Signature.getInstance(TigerApiConstants.SIGN_ALGORITHMS);
      signature.initVerify(pubKey);
      if (StringUtils.isEmpty(charset)) {
        signature.update(content.getBytes());
      } else {
        signature.update(content.getBytes(charset));
      }
      return signature.verify(Base64.decodeBase64(sign.getBytes()));
    } catch (Exception e) {
      throw new TigerApiException(TigerApiCode.SIGN_CHECK_FAILED);
    }
  }

  public static PublicKey getPublicKey(String algorithm, InputStream ins) throws Exception {
    KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
    StringWriter writer = new StringWriter();
    StreamUtil.io(new InputStreamReader(ins), writer);
    byte[] encodedKey = writer.toString().getBytes();
    encodedKey = Base64.decodeBase64(encodedKey);
    return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
  }
}
