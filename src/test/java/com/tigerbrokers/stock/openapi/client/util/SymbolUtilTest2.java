package com.tigerbrokers.stock.openapi.client.util;
import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.struct.OptionSymbol;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
public class SymbolUtilTest2 {
  @Test
  public void testIsUsStockSymbol_valid() { Assert.assertTrue(SymbolUtil.isUsStockSymbol("AAPL")); Assert.assertTrue(SymbolUtil.isUsStockSymbol("GOOG")); }
  @Test
  public void testIsUsStockSymbol_null() { Assert.assertFalse(SymbolUtil.isUsStockSymbol(null)); }
  @Test
  public void testIsUsStockSymbol_empty() { Assert.assertFalse(SymbolUtil.isUsStockSymbol("")); }
  @Test
  public void testIsUsStockSymbol_lowercase() { Assert.assertFalse(SymbolUtil.isUsStockSymbol("aapl")); }
  @Test
  public void testIsUsStockSymbol_withDotSuffix() { Assert.assertTrue(SymbolUtil.isUsStockSymbol("ABC.DE")); }
  @Test
  public void testIsHkOptionSymbol_valid() { Assert.assertTrue(SymbolUtil.isHkOptionSymbol("00700.HK")); }
  @Test
  public void testIsHkOptionSymbol_notHk() { Assert.assertFalse(SymbolUtil.isHkOptionSymbol("AAPL")); }
  @Test
  public void testIsHkOptionSymbol_null() { Assert.assertFalse(SymbolUtil.isHkOptionSymbol(null)); }
  @Test
  public void testGetZoneIdBySymbol_empty() { Assert.assertNotNull(SymbolUtil.getZoneIdBySymbol("")); }
  @Test
  public void testGetZoneIdBySymbol_hkOption() { Assert.assertEquals(TimeZoneId.HongKong, SymbolUtil.getZoneIdBySymbol("00700.HK")); }
  @Test
  public void testGetZoneIdBySymbol_usStock() { Assert.assertEquals(TimeZoneId.NewYork, SymbolUtil.getZoneIdBySymbol("AAPL")); }
  @Test
  public void testGetZoneIdBySymbol_futureSymbol() { Assert.assertEquals(TimeZoneId.Shanghai, SymbolUtil.getZoneIdBySymbol("ESmain")); }
  @Test
  public void testConvertToOptionSymbolObject_valid() throws TigerApiException {
    OptionSymbol r = SymbolUtil.convertToOptionSymbolObject("AAPL  240119C00150000");
    Assert.assertEquals("AAPL", r.getSymbol()); Assert.assertEquals("CALL", r.getRight()); Assert.assertEquals("150.0", r.getStrike());
  }
  @Test
  public void testConvertToOptionSymbolObject_putOption() throws TigerApiException { Assert.assertEquals("PUT", SymbolUtil.convertToOptionSymbolObject("AAPL  240119P00150000").getRight()); }
  @Test(expected = TigerApiException.class)
  public void testConvertToOptionSymbolObject_null() throws TigerApiException { SymbolUtil.convertToOptionSymbolObject(null); }
  @Test(expected = TigerApiException.class)
  public void testConvertToOptionSymbolObject_wrongLength() throws TigerApiException { SymbolUtil.convertToOptionSymbolObject("short"); }
}
