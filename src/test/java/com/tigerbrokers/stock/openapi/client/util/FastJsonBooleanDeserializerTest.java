package com.tigerbrokers.stock.openapi.client.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import java.lang.reflect.Type;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link FastJsonBooleanDeserializer}.
 */
public class FastJsonBooleanDeserializerTest {

  private final FastJsonBooleanDeserializer deserializer = new FastJsonBooleanDeserializer();
  private static final Type BOOLEAN_TYPE = Boolean.class;

  private Boolean deserialize(String json) {
    DefaultJSONParser parser = new DefaultJSONParser(json);
    try {
      return deserializer.deserialze(parser, BOOLEAN_TYPE, "testField");
    } finally {
      parser.close();
    }
  }

  @Test
  public void testDeserializeTrue() {
    Boolean result = deserialize("true");
    Assert.assertEquals(Boolean.TRUE, result);
  }

  @Test
  public void testDeserializeFalse() {
    Boolean result = deserialize("false");
    Assert.assertEquals(Boolean.FALSE, result);
  }

  @Test
  public void testDeserializeIntOne() {
    Boolean result = deserialize("1");
    Assert.assertEquals(Boolean.TRUE, result);
  }

  @Test
  public void testDeserializeIntZero() {
    Boolean result = deserialize("0");
    Assert.assertEquals(Boolean.FALSE, result);
  }

  @Test
  public void testDeserializeIntNonZero() {
    Boolean result = deserialize("42");
    Assert.assertEquals(Boolean.TRUE, result);
  }

  @Test
  public void testDeserializeStringTrue() {
    // String "true" in JSON is parsed as a string token, then cast to boolean
    Boolean result = deserialize("\"true\"");
    Assert.assertEquals(Boolean.TRUE, result);
  }

  @Test
  public void testDeserializeStringFalse() {
    Boolean result = deserialize("\"false\"");
    Assert.assertEquals(Boolean.FALSE, result);
  }

  @Test
  public void testGetFastMatchToken() {
    Assert.assertEquals(JSONToken.TRUE, deserializer.getFastMatchToken());
  }
}
