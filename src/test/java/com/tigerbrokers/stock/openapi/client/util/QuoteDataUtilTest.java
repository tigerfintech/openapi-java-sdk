package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteBBOData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteBasicData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.SocketCommon;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Tests for {@link QuoteDataUtil}.
 */
@RunWith(MockitoJUnitRunner.class)
public class QuoteDataUtilTest {

  @Test
  public void testConvertToAskBidDataNull() {
    Assert.assertNull(QuoteDataUtil.convertToAskBidData(null));
  }

  @Test
  public void testConvertToAskBidDataTypeNull() {
    QuoteData data = QuoteData.newBuilder().setSymbol("AAPL").build();
    Assert.assertNull(QuoteDataUtil.convertToAskBidData(data));
  }

  @Test
  public void testConvertToAskBidDataTypeBasic() {
    QuoteData data = QuoteData.newBuilder()
        .setSymbol("AAPL")
        .setType(SocketCommon.QuoteType.BASIC)
        .build();
    Assert.assertNull(QuoteDataUtil.convertToAskBidData(data));
  }

  @Test
  public void testConvertToAskBidDataTypeAll() {
    QuoteData data = QuoteData.newBuilder()
        .setSymbol("AAPL")
        .setType(SocketCommon.QuoteType.ALL)
        .setTimestamp(1000L)
        .setAskPrice(150.0)
        .setAskSize(100)
        .setBidPrice(149.0)
        .setBidSize(200)
        .build();
    QuoteBBOData result = QuoteDataUtil.convertToAskBidData(data);
    Assert.assertNotNull(result);
    Assert.assertEquals("AAPL", result.getSymbol());
    Assert.assertEquals(SocketCommon.QuoteType.BBO, result.getType());
    Assert.assertEquals(1000L, result.getTimestamp());
    Assert.assertEquals(150.0, result.getAskPrice(), 0.001);
    Assert.assertEquals(100, result.getAskSize());
    Assert.assertEquals(149.0, result.getBidPrice(), 0.001);
    Assert.assertEquals(200, result.getBidSize());
  }

  @Test
  public void testConvertToAskBidDataTypeBBO() {
    QuoteData data = QuoteData.newBuilder()
        .setSymbol("00700")
        .setType(SocketCommon.QuoteType.BBO)
        .setTimestamp(2000L)
        .setAskPrice(300.0)
        .setAskSize(500)
        .setBidPrice(299.0)
        .setBidSize(600)
        .setAskTimestamp(1000L)
        .setBidTimestamp(2000L)
        .build();
    QuoteBBOData result = QuoteDataUtil.convertToAskBidData(data);
    Assert.assertNotNull(result);
    Assert.assertEquals("00700", result.getSymbol());
    Assert.assertEquals(SocketCommon.QuoteType.BBO, result.getType());
    Assert.assertEquals(2000L, result.getTimestamp());
    Assert.assertEquals(300.0, result.getAskPrice(), 0.001);
    Assert.assertEquals(500, result.getAskSize());
    Assert.assertEquals(299.0, result.getBidPrice(), 0.001);
    Assert.assertEquals(600, result.getBidSize());
    Assert.assertEquals(1000L, result.getAskTimestamp());
    Assert.assertEquals(2000L, result.getBidTimestamp());
  }

  @Test
  public void testConvertToBasicDataNull() {
    Assert.assertNull(QuoteDataUtil.convertToBasicData(null));
  }

  @Test
  public void testConvertToBasicDataTypeNull() {
    QuoteData data = QuoteData.newBuilder().setSymbol("AAPL").build();
    Assert.assertNull(QuoteDataUtil.convertToBasicData(data));
  }

  @Test
  public void testConvertToBasicDataTypeBBO() {
    QuoteData data = QuoteData.newBuilder()
        .setSymbol("AAPL")
        .setType(SocketCommon.QuoteType.BBO)
        .build();
    Assert.assertNull(QuoteDataUtil.convertToBasicData(data));
  }

  @Test
  public void testConvertToBasicDataTypeAll() {
    QuoteData data = QuoteData.newBuilder()
        .setSymbol("AAPL")
        .setType(SocketCommon.QuoteType.ALL)
        .setTimestamp(1000L)
        .setServerTimestamp(2000L)
        .setAvgPrice(148.0)
        .setLatestPrice(150.0)
        .setLatestPriceTimestamp(1500L)
        .setLatestTime("2023-01-01 10:00:00")
        .setPreClose(145.0)
        .setVolume(1000000)
        .setVolumeDecimal(1000000.0)
        .setAmount(150000000.0)
        .setOpen(146.0)
        .setHigh(152.0)
        .setLow(145.0)
        .setHourTradingTag("pre")
        .setMarketStatus("TRADING")
        .build();
    QuoteBasicData result = QuoteDataUtil.convertToBasicData(data);
    Assert.assertNotNull(result);
    Assert.assertEquals("AAPL", result.getSymbol());
    Assert.assertEquals(SocketCommon.QuoteType.BASIC, result.getType());
    Assert.assertEquals(1000L, result.getTimestamp());
    Assert.assertEquals(2000L, result.getServerTimestamp());
    Assert.assertEquals(148.0, result.getAvgPrice(), 0.001);
    Assert.assertEquals(150.0, result.getLatestPrice(), 0.001);
    Assert.assertEquals(1500L, result.getLatestPriceTimestamp());
    Assert.assertEquals("2023-01-01 10:00:00", result.getLatestTime());
    Assert.assertEquals(145.0, result.getPreClose(), 0.001);
    Assert.assertEquals(1000000, result.getVolume());
    Assert.assertEquals(1000000.0, result.getVolumeDecimal(), 0.001);
    Assert.assertEquals(150000000.0, result.getAmount(), 0.001);
    Assert.assertEquals(146.0, result.getOpen(), 0.001);
    Assert.assertEquals(152.0, result.getHigh(), 0.001);
    Assert.assertEquals(145.0, result.getLow(), 0.001);
    Assert.assertEquals("pre", result.getHourTradingTag());
    Assert.assertEquals("TRADING", result.getMarketStatus());
  }

  @Test
  public void testConvertToBasicDataTypeBasic() {
    QuoteData data = QuoteData.newBuilder()
        .setSymbol("00700")
        .setType(SocketCommon.QuoteType.BASIC)
        .setTimestamp(3000L)
        .setLatestPrice(300.0)
        .setLatestTime("2023-06-01 14:30:00")
        .setPreClose(295.0)
        .setVolume(500000)
        .setVolumeDecimal(500000.0)
        .build();
    QuoteBasicData result = QuoteDataUtil.convertToBasicData(data);
    Assert.assertNotNull(result);
    Assert.assertEquals("00700", result.getSymbol());
    Assert.assertEquals(SocketCommon.QuoteType.BASIC, result.getType());
    Assert.assertEquals(3000L, result.getTimestamp());
    Assert.assertEquals(300.0, result.getLatestPrice(), 0.001);
    Assert.assertEquals("2023-06-01 14:30:00", result.getLatestTime());
    Assert.assertEquals(295.0, result.getPreClose(), 0.001);
    Assert.assertEquals(500000, result.getVolume());
  }
}
