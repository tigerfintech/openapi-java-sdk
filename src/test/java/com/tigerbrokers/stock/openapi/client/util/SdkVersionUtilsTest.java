package com.tigerbrokers.stock.openapi.client.util;

import org.junit.Assert;
import org.junit.Test;

public class SdkVersionUtilsTest {

  @Test
  public void testGetSdkVersion() {
    String version = SdkVersionUtils.getSdkVersion();
    if (version != null) {
      Assert.assertTrue(version.startsWith("java-"));
    }
  }
  @Test
  public void testGetSdkVersion_cachesResult() {
    String v1 = SdkVersionUtils.getSdkVersion();
    String v2 = SdkVersionUtils.getSdkVersion();
    Assert.assertSame(v1, v2);
  }
}
