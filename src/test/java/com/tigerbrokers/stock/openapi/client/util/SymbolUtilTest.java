package com.tigerbrokers.stock.openapi.client.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author liutongping
 * @date 2022/10/20
 */
@RunWith(MockitoJUnitRunner.class)
public class SymbolUtilTest {

  @Test
  public void testIsFutureSymbol() {
    Assert.assertTrue(SymbolUtil.isFutureSymbol("ESmain"));
    Assert.assertTrue(SymbolUtil.isFutureSymbol("ES2212"));
    Assert.assertTrue(SymbolUtil.isFutureSymbol("NQ2309"));
    Assert.assertTrue(SymbolUtil.isFutureSymbol("HSI2212"));
    Assert.assertTrue(SymbolUtil.isFutureSymbol("HSImain"));
    Assert.assertTrue(SymbolUtil.isFutureSymbol("HTI2303"));
    Assert.assertTrue(SymbolUtil.isFutureSymbol("NG2302"));
    Assert.assertTrue(SymbolUtil.isFutureSymbol("CL2306"));
  }
}
