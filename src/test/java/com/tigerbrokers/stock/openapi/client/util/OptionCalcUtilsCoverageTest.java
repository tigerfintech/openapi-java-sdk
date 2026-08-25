package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.struct.OptionFundamentals;
import com.tigerbrokers.stock.openapi.client.struct.enums.Right;
import java.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

public class OptionCalcUtilsCoverageTest {

  @Test
  public void testCalculateAvgPrice_bothNull() {
    Assert.assertEquals(0.0, OptionCalcUtils.calculateAvgPrice(null, null), 0.001);
  }

  @Test
  public void testCalculateAvgPrice_bothPresent() {
    Assert.assertEquals(15.0, OptionCalcUtils.calculateAvgPrice(10.0, 20.0), 0.001);
  }

  @Test
  public void testCalculateAvgPrice_onlyAsk() {
    Assert.assertEquals(10.0, OptionCalcUtils.calculateAvgPrice(10.0, null), 0.001);
  }

  @Test
  public void testCalculateAvgPrice_onlyBid() {
    Assert.assertEquals(20.0, OptionCalcUtils.calculateAvgPrice(null, 20.0), 0.001);
  }

  @Test
  public void testCalcOptionIndex_call() {
    LocalDate settlement = LocalDate.of(2027, 1, 1);
    LocalDate expiration = LocalDate.of(2027, 3, 15);
    OptionFundamentals result = OptionCalcUtils.calcOptionIndex(
        Right.CALL, 150.0, 145.0, 0.05, 0.02, 10.0, 8.0, settlement, expiration);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.getDelta() > 0);
    Assert.assertTrue(result.getGamma() > 0);
    Assert.assertTrue(result.getVega() > 0);
    Assert.assertTrue(result.getPredictedValue() > 0);
  }

  @Test
  public void testCalcOptionIndex_put() {
    LocalDate settlement = LocalDate.of(2027, 1, 1);
    LocalDate expiration = LocalDate.of(2027, 3, 15);
    OptionFundamentals result = OptionCalcUtils.calcOptionIndex(
        Right.PUT, 150.0, 155.0, 0.05, 0.02, 10.0, 8.0, settlement, expiration);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.getDelta() < 0);
    Assert.assertTrue(result.getGamma() > 0);
  }

  @Test
  public void testCalcOptionIndex_withPrice() {
    LocalDate settlement = LocalDate.of(2027, 1, 1);
    LocalDate expiration = LocalDate.of(2027, 3, 15);
    OptionFundamentals result = OptionCalcUtils.calcOptionIndex(
        Right.CALL, 150.0, 145.0, 0.05, 0.02, 10.0, 8.0, settlement, expiration);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.getDelta() > 0);
    Assert.assertTrue(result.getVolatility() >= 0);
  }

  @Test
  public void testCalcEuropeanOptionIndex_call() {
    LocalDate settlement = LocalDate.of(2027, 1, 1);
    LocalDate expiration = LocalDate.of(2027, 3, 15);
    OptionFundamentals result = OptionCalcUtils.calcEuropeanOptionIndex(
        Right.CALL, 150.0, 145.0, 0.05, 0.02, 10.0, 8.0, settlement, expiration);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.getDelta() > 0);
  }

  @Test
  public void testCalcEuropeanOptionIndex_put() {
    LocalDate settlement = LocalDate.of(2027, 1, 1);
    LocalDate expiration = LocalDate.of(2027, 3, 15);
    OptionFundamentals result = OptionCalcUtils.calcEuropeanOptionIndex(
        Right.PUT, 150.0, 155.0, 0.05, 0.02, 10.0, 8.0, settlement, expiration);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.getDelta() < 0);
  }
}
