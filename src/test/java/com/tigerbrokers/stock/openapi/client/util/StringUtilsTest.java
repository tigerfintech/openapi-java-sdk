package com.tigerbrokers.stock.openapi.client.util;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilsTest {

  @Test
  public void testIsEmpty_null() {
    Assert.assertTrue(StringUtils.isEmpty(null));
  }
  @Test
  public void testIsEmpty_emptyString() {
    Assert.assertTrue(StringUtils.isEmpty(""));
  }
  @Test
  public void testIsEmpty_whitespace() {
    Assert.assertTrue(StringUtils.isEmpty("   "));
    Assert.assertTrue(StringUtils.isEmpty(" \t\n\r"));
  }
  @Test
  public void testIsEmpty_nonEmpty() {
    Assert.assertFalse(StringUtils.isEmpty("hello"));
    Assert.assertFalse(StringUtils.isEmpty(" a "));
  }
  @Test
  public void testDefaultIfEmpty_empty() {
    Assert.assertEquals("default", StringUtils.defaultIfEmpty(null, "default"));
    Assert.assertEquals("default", StringUtils.defaultIfEmpty("", "default"));
    Assert.assertEquals("default", StringUtils.defaultIfEmpty("  ", "default"));
  }
  @Test
  public void testDefaultIfEmpty_nonEmpty() {
    Assert.assertEquals("hello", StringUtils.defaultIfEmpty("hello", "default"));
  }
  @Test
  public void testAreNotEmpty_null() {
    Assert.assertFalse(StringUtils.areNotEmpty((String[]) null));
  }
  @Test
  public void testAreNotEmpty_emptyArray() {
    Assert.assertFalse(StringUtils.areNotEmpty());
  }
  @Test
  public void testAreNotEmpty_allNotEmpty() {
    Assert.assertTrue(StringUtils.areNotEmpty("a", "b", "c"));
  }
  @Test
  public void testAreNotEmpty_oneEmpty() {
    Assert.assertFalse(StringUtils.areNotEmpty("a", "", "c"));
    Assert.assertFalse(StringUtils.areNotEmpty("a", null, "c"));
    Assert.assertFalse(StringUtils.areNotEmpty("a", "  ", "c"));
  }
  @Test
  public void testAreNotEmpty_singleValue() {
    Assert.assertTrue(StringUtils.areNotEmpty("hello"));
    Assert.assertFalse(StringUtils.areNotEmpty(""));
  }
  @Test
  public void testIsNumeric_null() {
    Assert.assertFalse(StringUtils.isNumeric(null));
  }
  @Test
  public void testIsNumeric_empty() {
    Assert.assertFalse(StringUtils.isNumeric(""));
  }
  @Test
  public void testIsNumeric_validNumber() {
    Assert.assertTrue(StringUtils.isNumeric("12345"));
    Assert.assertTrue(StringUtils.isNumeric("0"));
  }
  @Test
  public void testIsNumeric_withLetters() {
    Assert.assertFalse(StringUtils.isNumeric("12a34"));
    Assert.assertFalse(StringUtils.isNumeric("abc"));
  }
  @Test
  public void testIsNumeric_withSpecialChars() {
    Assert.assertFalse(StringUtils.isNumeric("12.34"));
    Assert.assertFalse(StringUtils.isNumeric("-123"));
    Assert.assertFalse(StringUtils.isNumeric("12 34"));
  }
  @Test
  public void testToInt_null() {
    Assert.assertEquals(5, StringUtils.toInt(null, 5));
  }
  @Test
  public void testToInt_validNumber() {
    Assert.assertEquals(123, StringUtils.toInt("123", 0));
  }
  @Test
  public void testToInt_invalidNumber() {
    Assert.assertEquals(-1, StringUtils.toInt("abc", -1));
  }
  @Test
  public void testToInt_emptyString() {
    Assert.assertEquals(10, StringUtils.toInt("", 10));
  }
}
