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

    // Futures with special characters, contracts that start with numbers and end with numbers
    Assert.assertTrue(SymbolUtil.isFutureSymbol("10Ymain"));
    Assert.assertTrue(SymbolUtil.isFutureSymbol("5YYmain"));
    Assert.assertTrue(SymbolUtil.isFutureSymbol("M2Kmain"));
    Assert.assertTrue(SymbolUtil.isFutureSymbol("M2K2306"));
    Assert.assertTrue(SymbolUtil.isFutureSymbol("6Emain"));
    Assert.assertTrue(SymbolUtil.isFutureSymbol("6E2306"));
    Assert.assertTrue(SymbolUtil.isFutureSymbol("M6Emain"));
    Assert.assertTrue(SymbolUtil.isFutureSymbol("M6E2306"));

    Assert.assertTrue(SymbolUtil.isFutureSymbol("NK225main"));
    Assert.assertTrue(SymbolUtil.isFutureSymbol("NK2252306"));
    Assert.assertTrue(SymbolUtil.isFutureSymbol("NK225Mmain"));
    Assert.assertTrue(SymbolUtil.isFutureSymbol("TOPIXMmain"));
    Assert.assertTrue(SymbolUtil.isFutureSymbol("TOPIXM2306"));
    Assert.assertTrue(SymbolUtil.isFutureSymbol("A50main"));
    Assert.assertTrue(SymbolUtil.isFutureSymbol("A502306"));

    // other
    Assert.assertFalse(SymbolUtil.isFutureSymbol("AAPL"));
    Assert.assertFalse(SymbolUtil.isFutureSymbol("00700"));
    Assert.assertFalse(SymbolUtil.isFutureSymbol("BK4555"));
  }
}
