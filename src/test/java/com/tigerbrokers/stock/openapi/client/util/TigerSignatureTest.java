package com.tigerbrokers.stock.openapi.client.util;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class TigerSignatureTest {

  @Test
  public void testGetSignContent_singleEntry() {
    Map<String, Object> request = new LinkedHashMap<>();
    request.put("key1", "value1");
    Assert.assertEquals("key1=value1", TigerSignature.getSignContent(request));
  }
  @Test
  public void testGetSignContent_multipleEntries_sorted() {
    Map<String, Object> request = new LinkedHashMap<>();
    request.put("c_key", "c_value");
    request.put("a_key", "a_value");
    request.put("b_key", "b_value");
    Assert.assertEquals("a_key=a_value&b_key=b_value&c_key=c_value", TigerSignature.getSignContent(request));
  }
  @Test
  public void testGetSignContent_nonStringValue() {
    Map<String, Object> request = new LinkedHashMap<>();
    request.put("count", 100);
    Assert.assertEquals("count=100", TigerSignature.getSignContent(request));
  }
  @Test
  public void testGetSignContent_emptyValue() {
    Map<String, Object> request = new LinkedHashMap<>();
    request.put("key1", "");
    request.put("key2", "value2");
    Assert.assertEquals("key2=value2", TigerSignature.getSignContent(request));
  }
  @Test
  public void testGetSignContent_nullValue() {
    Map<String, Object> request = new LinkedHashMap<>();
    request.put("key1", null);
    request.put("key2", "value2");
    Assert.assertEquals("key2=value2", TigerSignature.getSignContent(request));
  }
  @Test
  public void testGetSignContent_emptyMap() {
    Assert.assertEquals("", TigerSignature.getSignContent(new LinkedHashMap<>()));
  }
  @Test
  public void testGetSignContent_whitespaceValue() {
    Map<String, Object> request = new LinkedHashMap<>();
    request.put("key1", "   ");
    request.put("key2", "value2");
    Assert.assertEquals("key2=value2", TigerSignature.getSignContent(request));
  }
  @Test(expected = RuntimeException.class)
  public void testRsaSign_invalidKey() {
    TigerSignature.rsaSign("content", "invalid_key", "UTF-8");
  }
  @Test(expected = RuntimeException.class)
  public void testRsaSign_invalidKeyNoCharset() {
    TigerSignature.rsaSign("content", "invalid_key", null);
  }
  @Test(expected = NullPointerException.class)
  public void testGetSignContent_nullMap() {
    TigerSignature.getSignContent(null);
  }
}
