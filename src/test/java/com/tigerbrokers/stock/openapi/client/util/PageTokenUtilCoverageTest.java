package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.KlinePoint;
import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureKlineItem;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.struct.enums.KType;
import com.tigerbrokers.stock.openapi.client.struct.enums.FutureKType;
import com.tigerbrokers.stock.openapi.client.struct.enums.RightOption;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import org.junit.Assert;
import org.junit.Test;

public class PageTokenUtilCoverageTest {

  @Test(expected = TigerApiException.class)
  public void testNullReturnType() throws TigerApiException {
    PageTokenUtil.getKlineByPage(
        new TigerHttpClient(), null,
        "AAPL", "day", "2024-01-01", "2024-01-28",
        TimeZoneId.NewYork, RightOption.br, 1000, 1000, 2000L);
  }

  @Test(expected = TigerApiException.class)
  public void testInvalidReturnType() throws TigerApiException {
    PageTokenUtil.getKlineByPage(
        new TigerHttpClient(), "invalidType",
        "AAPL", "day", "2024-01-01", "2024-01-28",
        TimeZoneId.NewYork, RightOption.br, 1000, 1000, 2000L);
  }

  @Test(expected = TigerApiException.class)
  public void testNullSymbol() throws TigerApiException {
    PageTokenUtil.getKlineByPage(
        new TigerHttpClient(), PageTokenUtil.RETURN_KLINE,
        null, "day", "2024-01-01", "2024-01-28",
        TimeZoneId.NewYork, RightOption.br, 1000, 1000, 2000L);
  }

  @Test(expected = TigerApiException.class)
  public void testTotalSizeExceedsMax() throws TigerApiException {
    PageTokenUtil.getKlineByPage(
        new TigerHttpClient(), PageTokenUtil.RETURN_KLINE,
        "AAPL", "day", "2024-01-01", "2024-01-28",
        TimeZoneId.NewYork, RightOption.br, 1000, 20000, 2000L);
  }

  @Test
  public void testConstants() {
    Assert.assertEquals(1000, PageTokenUtil.DEFAULT_PAGE_SIZE);
    Assert.assertEquals(10, PageTokenUtil.DEFAULT_BATCH_TIME);
    Assert.assertEquals(10000, PageTokenUtil.MAX_TOTAL_SIZE);
    Assert.assertEquals(2000L, PageTokenUtil.DEFAULT_TIME_INTERVAL);
    Assert.assertNotNull(PageTokenUtil.RETURN_KLINE);
    Assert.assertNotNull(PageTokenUtil.RETURN_FUTURE_KLINE);
  }
}
