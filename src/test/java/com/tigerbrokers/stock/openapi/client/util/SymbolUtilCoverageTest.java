package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.struct.OptionSymbol;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import org.junit.Assert;
import org.junit.Test;

public class SymbolUtilCoverageTest {

  @Test
  public void testIsUsStockSymbol() {
    Assert.assertTrue(SymbolUtil.isUsStockSymbol("AAPL"));
    Assert.assertTrue(SymbolUtil.isUsStockSymbol("GOOG"));
    Assert.assertTrue(SymbolUtil.isUsStockSymbol("BRK.A"));
    Assert.assertFalse(SymbolUtil.isUsStockSymbol(null));
    Assert.assertFalse(SymbolUtil.isUsStockSymbol(""));
    Assert.assertFalse(SymbolUtil.isUsStockSymbol("aapl"));
    Assert.assertFalse(SymbolUtil.isUsStockSymbol("123"));
  }

  @Test
  public void testIsHkOptionSymbol() {
    Assert.assertTrue(SymbolUtil.isHkOptionSymbol("12345.HK"));
    Assert.assertFalse(SymbolUtil.isHkOptionSymbol(null));
    Assert.assertFalse(SymbolUtil.isHkOptionSymbol(""));
    Assert.assertFalse(SymbolUtil.isHkOptionSymbol("12345"));
    Assert.assertFalse(SymbolUtil.isHkOptionSymbol("12345.US"));
  }

  @Test
  public void testGetZoneIdBySymbol() {
    Assert.assertEquals(TimeZoneId.HongKong, SymbolUtil.getZoneIdBySymbol("12345.HK"));
    Assert.assertEquals(TimeZoneId.NewYork, SymbolUtil.getZoneIdBySymbol("AAPL"));
    Assert.assertEquals(TimeZoneId.Shanghai, SymbolUtil.getZoneIdBySymbol("600000"));
    Assert.assertNotNull(SymbolUtil.getZoneIdBySymbol(null));
    Assert.assertNotNull(SymbolUtil.getZoneIdBySymbol(""));
  }

  @Test(expected = TigerApiException.class)
  public void testConvertToOptionSymbolObject_null_throws() throws TigerApiException {
    SymbolUtil.convertToOptionSymbolObject(null);
  }

  @Test(expected = TigerApiException.class)
  public void testConvertToOptionSymbolObject_wrongLength_throws() throws TigerApiException {
    SymbolUtil.convertToOptionSymbolObject("short");
  }

  @Test
  public void testConvertToOptionSymbolObject_valid() throws TigerApiException {
    // Build a valid 21-char identifier: "AAPL  240119C00150000" = symbol(6) + expiry(6) + right(1) + strike(6) + decimal(1) = 20... need 21 chars
    // Format: 6 chars symbol, then 15 chars: YYMMDD + C/P + 5 digits strike + 1 decimal
    String identifier = "AAPL  240119C00150000"; // 21 chars
    OptionSymbol os = SymbolUtil.convertToOptionSymbolObject(identifier);
    Assert.assertNotNull(os);
    Assert.assertEquals("AAPL", os.getSymbol());
    Assert.assertEquals("CALL", os.getRight());
  }

  @Test
  public void testConvertToOptionSymbolObject_put() throws TigerApiException {
    String identifier = "AAPL  240119P00150000";
    OptionSymbol os = SymbolUtil.convertToOptionSymbolObject(identifier);
    Assert.assertNotNull(os);
    Assert.assertEquals("PUT", os.getRight());
  }

  @Test
  public void testIsFutureSymbol() {
    Assert.assertTrue(SymbolUtil.isFutureSymbol("CL2024"));
    Assert.assertTrue(SymbolUtil.isFutureSymbol("ESmain"));
    Assert.assertFalse(SymbolUtil.isFutureSymbol(null));
    Assert.assertFalse(SymbolUtil.isFutureSymbol(""));
    Assert.assertFalse(SymbolUtil.isFutureSymbol("BK1234"));
    Assert.assertFalse(SymbolUtil.isFutureSymbol("12345678"));  // all numeric
    Assert.assertFalse(SymbolUtil.isFutureSymbol("CL")); // too short
    Assert.assertFalse(SymbolUtil.isFutureSymbol("VERYLONGSYMBOL1234")); // too long
  }
}
