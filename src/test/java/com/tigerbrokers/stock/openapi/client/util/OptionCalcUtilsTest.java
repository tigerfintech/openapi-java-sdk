package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.struct.OptionFundamentals;
import com.tigerbrokers.stock.openapi.client.struct.enums.Right;
import java.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Tests for {@link OptionCalcUtils}.
 */
@RunWith(MockitoJUnitRunner.class)
public class OptionCalcUtilsTest {

  @Test
  public void testCalculateAvgPriceBothNull() {
    double result = OptionCalcUtils.calculateAvgPrice(null, null);
    Assert.assertEquals(0.0, result, 0.001);
  }

  @Test
  public void testCalculateAvgPriceBothNonNull() {
    double result = OptionCalcUtils.calculateAvgPrice(150.0, 148.0);
    Assert.assertEquals(149.0, result, 0.001);
  }

  @Test
  public void testCalculateAvgPriceAskOnly() {
    double result = OptionCalcUtils.calculateAvgPrice(150.0, null);
    Assert.assertEquals(150.0, result, 0.001);
  }

  @Test
  public void testCalculateAvgPriceBidOnly() {
    double result = OptionCalcUtils.calculateAvgPrice(null, 148.0);
    Assert.assertEquals(148.0, result, 0.001);
  }

  @Test
  public void testCalcOptionIndexWithImpliedVolatilityCall() {
    LocalDate settlementDate = LocalDate.of(2027, 1, 1);
    LocalDate expirationDate = LocalDate.of(2027, 6, 1);
    OptionFundamentals result = OptionCalcUtils.calcOptionIndex(
        Right.CALL, 150.0, 145.0, 0.05, 0.02, 0.3, settlementDate, expirationDate);
    Assert.assertNotNull(result);
    Assert.assertFalse(Double.isNaN(result.getDelta()));
    Assert.assertFalse(Double.isNaN(result.getGamma()));
    Assert.assertFalse(Double.isNaN(result.getTheta()));
    Assert.assertFalse(Double.isNaN(result.getVega()));
    Assert.assertFalse(Double.isNaN(result.getRho()));
    Assert.assertFalse(Double.isNaN(result.getPredictedValue()));
  }

  @Test
  public void testCalcOptionIndexWithImpliedVolatilityPut() {
    LocalDate settlementDate = LocalDate.of(2027, 1, 1);
    LocalDate expirationDate = LocalDate.of(2027, 6, 1);
    OptionFundamentals result = OptionCalcUtils.calcOptionIndex(
        Right.PUT, 150.0, 155.0, 0.05, 0.02, 0.3, settlementDate, expirationDate);
    Assert.assertNotNull(result);
    Assert.assertFalse(Double.isNaN(result.getDelta()));
    Assert.assertTrue(result.getDelta() <= 0);
  }

  @Test
  public void testCalcOptionIndexWithAskBidPriceCall() {
    LocalDate settlementDate = LocalDate.of(2027, 1, 1);
    LocalDate expirationDate = LocalDate.of(2027, 6, 1);
    OptionFundamentals result = OptionCalcUtils.calcOptionIndex(
        Right.CALL, 150.0, 145.0, 0.05, 0.02, 10.0, 9.0, settlementDate, expirationDate);
    Assert.assertNotNull(result);
    Assert.assertFalse(Double.isNaN(result.getDelta()));
    Assert.assertFalse(Double.isNaN(result.getGamma()));
    Assert.assertFalse(Double.isNaN(result.getTheta()));
    Assert.assertFalse(Double.isNaN(result.getVega()));
    Assert.assertFalse(Double.isNaN(result.getRho()));
    Assert.assertFalse(Double.isNaN(result.getPredictedValue()));
  }

  @Test
  public void testCalcOptionIndexWithAskBidPricePut() {
    LocalDate settlementDate = LocalDate.of(2027, 1, 1);
    LocalDate expirationDate = LocalDate.of(2027, 6, 1);
    OptionFundamentals result = OptionCalcUtils.calcOptionIndex(
        Right.PUT, 150.0, 155.0, 0.05, 0.02, 12.0, 11.0, settlementDate, expirationDate);
    Assert.assertNotNull(result);
    Assert.assertFalse(Double.isNaN(result.getDelta()));
    Assert.assertTrue(result.getDelta() <= 0);
  }

  @Test
  public void testCalcEuropeanOptionIndexCall() {
    LocalDate settlementDate = LocalDate.of(2027, 1, 1);
    LocalDate expirationDate = LocalDate.of(2027, 6, 1);
    OptionFundamentals result = OptionCalcUtils.calcEuropeanOptionIndex(
        Right.CALL, 150.0, 145.0, 0.05, 0.02, 10.0, 9.0, settlementDate, expirationDate);
    Assert.assertNotNull(result);
    Assert.assertFalse(Double.isNaN(result.getDelta()));
    Assert.assertFalse(Double.isNaN(result.getGamma()));
    Assert.assertFalse(Double.isNaN(result.getTheta()));
    Assert.assertFalse(Double.isNaN(result.getVega()));
    Assert.assertFalse(Double.isNaN(result.getRho()));
    Assert.assertFalse(Double.isNaN(result.getPredictedValue()));
  }

  @Test
  public void testCalcEuropeanOptionIndexPut() {
    LocalDate settlementDate = LocalDate.of(2027, 1, 1);
    LocalDate expirationDate = LocalDate.of(2027, 6, 1);
    OptionFundamentals result = OptionCalcUtils.calcEuropeanOptionIndex(
        Right.PUT, 150.0, 155.0, 0.05, 0.02, 12.0, 11.0, settlementDate, expirationDate);
    Assert.assertNotNull(result);
    Assert.assertFalse(Double.isNaN(result.getDelta()));
    Assert.assertTrue(result.getDelta() <= 0);
  }
}
