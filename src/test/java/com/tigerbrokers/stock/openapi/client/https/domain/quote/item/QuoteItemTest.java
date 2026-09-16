package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.alibaba.fastjson.JSON;
import com.tigerbrokers.stock.openapi.client.testsupport.PojoTester;
import org.junit.Assert;
import org.junit.Test;

public class QuoteItemTest {
  @Test
  public void pojoSurface() {
    PojoTester.testPackage(getClass().getPackage().getName());
  }

  @Test
  public void deserializeQuoteOvernight() {
    String json = "{\"symbol\":\"AAPL\",\"latestPrice\":230.1,\"askPrice\":230.2,"
        + "\"askSize\":10,\"bidPrice\":230.0,\"bidSize\":20,\"preClose\":229.5,"
        + "\"volume\":1000,\"amount\":230050.0,\"change\":0.6,\"changeRate\":0.26,"
        + "\"amplitude\":0.8,\"timestamp\":1755590400000,\"tradingStatus\":5}";

    QuoteOvernight quote = JSON.parseObject(json, QuoteOvernight.class);

    Assert.assertEquals("AAPL", quote.getSymbol());
    Assert.assertEquals(Double.valueOf(230.1), quote.getLatestPrice());
    Assert.assertEquals(Double.valueOf(230.2), quote.getAskPrice());
    Assert.assertEquals(Long.valueOf(10), quote.getAskSize());
    Assert.assertEquals(Double.valueOf(230.0), quote.getBidPrice());
    Assert.assertEquals(Long.valueOf(20), quote.getBidSize());
    Assert.assertEquals(Double.valueOf(229.5), quote.getPreClose());
    Assert.assertEquals(Long.valueOf(1000), quote.getVolume());
    Assert.assertEquals(Double.valueOf(230050.0), quote.getAmount());
    Assert.assertEquals(Double.valueOf(0.6), quote.getChange());
    Assert.assertEquals(Double.valueOf(0.26), quote.getChangeRate());
    Assert.assertEquals(Double.valueOf(0.8), quote.getAmplitude());
    Assert.assertEquals(Long.valueOf(1755590400000L), quote.getTimestamp());
    Assert.assertEquals(Integer.valueOf(5), quote.getTradingStatus());
  }

  @Test
  public void deserializeKlinePointVolumeDecimal() {
    KlinePoint fractional = JSON.parseObject(
        "{\"volume\":123,\"volumeDecimal\":123.456}", KlinePoint.class);
    KlinePoint absent = JSON.parseObject("{\"volume\":123}", KlinePoint.class);
    KlinePoint explicitNull = JSON.parseObject(
        "{\"volume\":123,\"volumeDecimal\":null}", KlinePoint.class);

    Assert.assertEquals(Double.valueOf(123.456), fractional.getVolumeDecimal());
    Assert.assertNull(absent.getVolumeDecimal());
    Assert.assertNull(explicitNull.getVolumeDecimal());
  }

  @Test
  public void deserializeTimelinePointVolumeDecimal() {
    TimelinePoint fractional = JSON.parseObject(
        "{\"volume\":123,\"volumeDecimal\":123.456}", TimelinePoint.class);
    TimelinePoint absent = JSON.parseObject("{\"volume\":123}", TimelinePoint.class);
    TimelinePoint explicitNull = JSON.parseObject(
        "{\"volume\":123,\"volumeDecimal\":null}", TimelinePoint.class);

    Assert.assertEquals(Double.valueOf(123.456), fractional.getVolumeDecimal());
    Assert.assertNull(absent.getVolumeDecimal());
    Assert.assertNull(explicitNull.getVolumeDecimal());
  }

  @Test
  public void deserializeRealTimeQuoteAmount() {
    RealTimeQuoteItem stockQuote = JSON.parseObject(
        "{\"symbol\":\"AAPL\",\"volume\":123,\"amount\":4567.89,\"volumeDecimal\":123.456}",
        RealTimeQuoteItem.class);

    Assert.assertEquals("AAPL", stockQuote.getSymbol());
    Assert.assertEquals(Long.valueOf(123), stockQuote.getVolume());
    Assert.assertEquals(Double.valueOf(4567.89), stockQuote.getAmount());
    Assert.assertEquals(Double.valueOf(123.456), stockQuote.getVolumeDecimal());

    RealTimeQuoteItem ccQuote = JSON.parseObject(
        "{\"symbol\":\"BTCUSD\",\"volume\":0,\"amount\":987654.32,\"volumeDecimal\":0.123456}",
        RealTimeQuoteItem.class);

    Assert.assertEquals("BTCUSD", ccQuote.getSymbol());
    Assert.assertEquals(Double.valueOf(987654.32), ccQuote.getAmount());
  }
}
