package com.tigerbrokers.stock.openapi.client.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FastJsonPropertyFilterTest {

  @Test
  public void testGetPropertyFilter_nullValue() {
    PropertyFilter filter = FastJsonPropertyFilter.getPropertyFilter();
    Assert.assertFalse(filter.apply(null, "field", null));
  }
  @Test
  public void testGetPropertyFilter_positiveInt() {
    PropertyFilter filter = FastJsonPropertyFilter.getPropertyFilter();
    Assert.assertTrue(filter.apply(null, "field", 10));
  }
  @Test
  public void testGetPropertyFilter_zeroInt() {
    Assert.assertFalse(FastJsonPropertyFilter.getPropertyFilter().apply(null, "field", 0));
  }
  @Test
  public void testGetPropertyFilter_negativeInt() {
    Assert.assertFalse(FastJsonPropertyFilter.getPropertyFilter().apply(null, "field", -1));
  }
  @Test
  public void testGetPropertyFilter_positiveDouble() {
    Assert.assertTrue(FastJsonPropertyFilter.getPropertyFilter().apply(null, "field", 10.5));
  }
  @Test
  public void testGetPropertyFilter_zeroDouble() {
    Assert.assertFalse(FastJsonPropertyFilter.getPropertyFilter().apply(null, "field", 0.0));
  }
  @Test
  public void testGetPropertyFilter_zeroDoubleWithAdjustLimitKey() {
    Assert.assertTrue(FastJsonPropertyFilter.getPropertyFilter().apply(null, "adjust_limit", 0.0));
  }
  @Test
  public void testGetPropertyFilter_negativeDouble() {
    Assert.assertFalse(FastJsonPropertyFilter.getPropertyFilter().apply(null, "field", -1.0));
  }
  @Test
  public void testGetPropertyFilter_positiveFloat() {
    Assert.assertTrue(FastJsonPropertyFilter.getPropertyFilter().apply(null, "field", 1.5f));
  }
  @Test
  public void testGetPropertyFilter_zeroFloat() {
    Assert.assertFalse(FastJsonPropertyFilter.getPropertyFilter().apply(null, "field", 0.0f));
  }
  @Test
  public void testGetPropertyFilter_negativeFloat() {
    Assert.assertFalse(FastJsonPropertyFilter.getPropertyFilter().apply(null, "field", -0.5f));
  }
  @Test
  public void testGetPropertyFilter_stringValue() {
    Assert.assertTrue(FastJsonPropertyFilter.getPropertyFilter().apply(null, "field", "hello"));
  }
  @Test
  public void testGetExcludeFilter_nullValue() {
    PropertyFilter filter = FastJsonPropertyFilter.getExcludeFilter(null);
    Assert.assertFalse(filter.apply(null, "field", null));
    Assert.assertTrue(filter.apply(null, "field", "value"));
  }
  @Test
  public void testGetExcludeFilter_excludesKey() {
    List<String> excludeKeys = Arrays.asList("secret", "password");
    PropertyFilter filter = FastJsonPropertyFilter.getExcludeFilter(excludeKeys);
    Assert.assertFalse(filter.apply(null, "secret", "value"));
    Assert.assertFalse(filter.apply(null, "password", "value"));
    Assert.assertTrue(filter.apply(null, "public", "value"));
  }
  @Test
  public void testGetExcludeFilter_zeroIntExcluded() {
    PropertyFilter filter = FastJsonPropertyFilter.getExcludeFilter(Arrays.asList("secret"));
    Assert.assertFalse(filter.apply(null, "field", 0));
    Assert.assertTrue(filter.apply(null, "field", 1));
  }
  @Test
  public void testGetExcludeFilter_zeroDoubleExcluded() {
    Assert.assertFalse(FastJsonPropertyFilter.getExcludeFilter(null).apply(null, "field", 0.0));
  }
  @Test
  public void testGetExcludeFilter_zeroFloatExcluded() {
    Assert.assertFalse(FastJsonPropertyFilter.getExcludeFilter(null).apply(null, "field", 0.0f));
  }
  @Test
  public void testGetExcludeFilter_emptyList() {
    Assert.assertTrue(FastJsonPropertyFilter.getExcludeFilter(Arrays.asList()).apply(null, "field", "value"));
  }
}
