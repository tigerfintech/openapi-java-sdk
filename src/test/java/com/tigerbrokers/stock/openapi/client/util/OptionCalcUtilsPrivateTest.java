package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.struct.OptionFundamentals;
import java.lang.reflect.Method;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests private calcOptionIndex method via reflection to cover BAW approximation,
 * Black-Scholes, volatility, and greek calculation logic.
 */
public class OptionCalcUtilsPrivateTest {

  private OptionFundamentals invokeCalcOptionIndex(double r, long expiry, long execDate,
      double latestPrice, double targetPrice, double dividend, double strike,
      String type, long currentTime, boolean isTrading) throws Exception {
    Method m = OptionCalcUtils.class.getDeclaredMethod("calcOptionIndex",
        double.class, long.class, long.class, double.class, double.class,
        double.class, double.class, String.class, long.class, boolean.class);
    m.setAccessible(true);
    return (OptionFundamentals) m.invoke(null, r, expiry, execDate, latestPrice,
        targetPrice, dividend, strike, type, currentTime, isTrading);
  }

  @Test
  public void testCalcOptionIndex_callOption() throws Exception {
    long now = System.currentTimeMillis();
    long expiry = now + 30L * 24 * 60 * 60 * 1000; // 30 days
    long execDate = now + 10L * 24 * 60 * 60 * 1000; // 10 days
    OptionFundamentals result = invokeCalcOptionIndex(0.05, expiry, execDate,
        150.0, 5.0, 0.5, 145.0, "CALL", now, true);
    Assert.assertNotNull(result);
    Assert.assertFalse(Double.isNaN(result.getVolatility()));
    Assert.assertFalse(Double.isNaN(result.getDelta()));
    Assert.assertFalse(Double.isNaN(result.getLeverage()));
    Assert.assertFalse(Double.isNaN(result.getInsideValue()));
    Assert.assertFalse(Double.isNaN(result.getPremiumRate()));
    Assert.assertFalse(Double.isNaN(result.getProfitRate()));
    Assert.assertFalse(Double.isNaN(result.getTimeValue()));
  }

  @Test
  public void testCalcOptionIndex_putOption() throws Exception {
    long now = System.currentTimeMillis();
    long expiry = now + 30L * 24 * 60 * 60 * 1000;
    long execDate = now + 10L * 24 * 60 * 60 * 1000;
    OptionFundamentals result = invokeCalcOptionIndex(0.05, expiry, execDate,
        140.0, 5.0, 0.5, 145.0, "PUT", now, true);
    Assert.assertNotNull(result);
    Assert.assertFalse(Double.isNaN(result.getVolatility()));
  }

  @Test
  public void testCalcOptionIndex_callOption_notTrading() throws Exception {
    long now = System.currentTimeMillis();
    long expiry = now + 30L * 24 * 60 * 60 * 1000;
    long execDate = now + 10L * 24 * 60 * 60 * 1000;
    OptionFundamentals result = invokeCalcOptionIndex(0.05, expiry, execDate,
        150.0, 5.0, 0.5, 145.0, "CALL", now, false);
    Assert.assertNotNull(result);
  }

  @Test
  public void testCalcOptionIndex_putOption_notTrading() throws Exception {
    long now = System.currentTimeMillis();
    long expiry = now + 30L * 24 * 60 * 60 * 1000;
    long execDate = now + 10L * 24 * 60 * 60 * 1000;
    OptionFundamentals result = invokeCalcOptionIndex(0.05, expiry, execDate,
        140.0, 5.0, 0.5, 145.0, "PUT", now, false);
    Assert.assertNotNull(result);
  }

  @Test
  public void testCalcOptionIndex_zeroTarget_returnsNull() throws Exception {
    long now = System.currentTimeMillis();
    long expiry = now + 30L * 24 * 60 * 60 * 1000;
    OptionFundamentals result = invokeCalcOptionIndex(0.05, expiry, 0,
        150.0, 0, 0.5, 145.0, "CALL", now, true);
    Assert.assertNull(result);
  }

  @Test
  public void testCalcOptionIndex_zeroStrike_returnsNull() throws Exception {
    long now = System.currentTimeMillis();
    long expiry = now + 30L * 24 * 60 * 60 * 1000;
    OptionFundamentals result = invokeCalcOptionIndex(0.05, expiry, 0,
        150.0, 5.0, 0.5, 0, "CALL", now, true);
    Assert.assertNull(result);
  }

  @Test
  public void testCalcOptionIndex_callNoDividend() throws Exception {
    long now = System.currentTimeMillis();
    long expiry = now + 60L * 24 * 60 * 60 * 1000; // 60 days
    long execDate = 0; // no dividend
    OptionFundamentals result = invokeCalcOptionIndex(0.05, expiry, execDate,
        150.0, 5.0, 0.0, 145.0, "CALL", now, true);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.getVolatility() >= 0);
  }

  @Test
  public void testCalcOptionIndex_putNoDividend() throws Exception {
    long now = System.currentTimeMillis();
    long expiry = now + 60L * 24 * 60 * 60 * 1000;
    OptionFundamentals result = invokeCalcOptionIndex(0.05, expiry, 0,
        140.0, 5.0, 0.0, 145.0, "PUT", now, false);
    Assert.assertNotNull(result);
  }

  @Test
  public void testCalcOptionIndex_callExpiredDividend() throws Exception {
    long now = System.currentTimeMillis();
    long expiry = now + 30L * 24 * 60 * 60 * 1000;
    long execDate = now - 1; // dividend already executed (before current time)
    OptionFundamentals result = invokeCalcOptionIndex(0.05, expiry, execDate,
        150.0, 5.0, 0.5, 145.0, "CALL", now, true);
    Assert.assertNotNull(result);
  }

  @Test
  public void testCalcOptionIndex_callExpiryBeforeExecDate() throws Exception {
    long now = System.currentTimeMillis();
    long expiry = now + 5L * 24 * 60 * 60 * 1000; // 5 days
    long execDate = now + 10L * 24 * 60 * 60 * 1000; // 10 days (after expiry)
    OptionFundamentals result = invokeCalcOptionIndex(0.05, expiry, execDate,
        150.0, 5.0, 0.5, 145.0, "CALL", now, true);
    Assert.assertNotNull(result);
  }

  @Test
  public void testCalcOptionIndex_callPriceBelowIntrinsic() throws Exception {
    // target <= latestPrice - strike, should go to else branch
    long now = System.currentTimeMillis();
    long expiry = now + 30L * 24 * 60 * 60 * 1000;
    OptionFundamentals result = invokeCalcOptionIndex(0.05, expiry, 0,
        150.0, 1.0, 0.0, 145.0, "CALL", now, true);
    // target (1.0) <= latestPrice - strike (5.0), so it goes to the else branch
    // which sets all metrics to NaN
    Assert.assertNotNull(result);
    Assert.assertTrue(Double.isNaN(result.getDelta()));
  }

  @Test
  public void testCalcOptionIndex_putPriceBelowIntrinsic() throws Exception {
    // target <= strike - latestPrice, should go to else branch
    long now = System.currentTimeMillis();
    long expiry = now + 30L * 24 * 60 * 60 * 1000;
    OptionFundamentals result = invokeCalcOptionIndex(0.05, expiry, 0,
        150.0, 1.0, 0.0, 155.0, "PUT", now, true);
    // target (1.0) <= strike - latestPrice (5.0), so it goes to the else branch
    Assert.assertNotNull(result);
    Assert.assertTrue(Double.isNaN(result.getDelta()));
  }
}
