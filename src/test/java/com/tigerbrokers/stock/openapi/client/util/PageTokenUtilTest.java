package com.tigerbrokers.stock.openapi.client.util;
import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.struct.enums.RightOption;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import org.junit.Test;
public class PageTokenUtilTest {
  @Test(expected = TigerApiException.class)
  public void testGetKlineByPage_nullReturnType() throws TigerApiException {
    PageTokenUtil.getKlineByPage(null, "AAPL", "day", "2022-04-25 00:00:00", "2022-04-28 00:00:00", TimeZoneId.NewYork, RightOption.br, 1000, 5000, 2000);
  }
  @Test(expected = TigerApiException.class)
  public void testGetKlineByPage_invalidReturnType() throws TigerApiException {
    PageTokenUtil.getKlineByPage("invalid", "AAPL", "day", "2022-04-25 00:00:00", "2022-04-28 00:00:00", TimeZoneId.NewYork, RightOption.br, 1000, 5000, 2000);
  }
  @Test(expected = TigerApiException.class)
  public void testGetKlineByPage_nullSymbol() throws TigerApiException {
    PageTokenUtil.getKlineByPage(PageTokenUtil.RETURN_KLINE, null, "day", "2022-04-25 00:00:00", "2022-04-28 00:00:00", TimeZoneId.NewYork, RightOption.br, 1000, 5000, 2000);
  }
  @Test(expected = TigerApiException.class)
  public void testGetKlineByPage_totalSizeExceedsMax() throws TigerApiException {
    PageTokenUtil.getKlineByPage(PageTokenUtil.RETURN_KLINE, "AAPL", "day", "2022-04-25 00:00:00", "2022-04-28 00:00:00", TimeZoneId.NewYork, RightOption.br, 1000, 10001, 2000);
  }
  @Test
  public void testGetKlineByPage_nullPeriod() { try { PageTokenUtil.getKlineByPage(PageTokenUtil.RETURN_KLINE, "AAPL", null, "2022-04-25 00:00:00", "2022-04-28 00:00:00", TimeZoneId.NewYork, RightOption.br, 0, 0, 500); } catch (Exception e) {} }
  @Test
  public void testGetKlineByPage_invalidPeriod() { try { PageTokenUtil.getKlineByPage(PageTokenUtil.RETURN_KLINE, "AAPL", "invalid", "2022-04-25 00:00:00", "2022-04-28 00:00:00", TimeZoneId.NewYork, RightOption.br, 1000, 5000, 2000); } catch (Exception e) {} }
  @Test
  public void testConstants() {
    org.junit.Assert.assertEquals(1000, PageTokenUtil.DEFAULT_PAGE_SIZE);
    org.junit.Assert.assertEquals(10, PageTokenUtil.DEFAULT_BATCH_TIME);
    org.junit.Assert.assertEquals(10000, PageTokenUtil.MAX_TOTAL_SIZE);
    org.junit.Assert.assertEquals(2000L, PageTokenUtil.DEFAULT_TIME_INTERVAL);
    org.junit.Assert.assertNotNull(PageTokenUtil.RETURN_KLINE);
    org.junit.Assert.assertNotNull(PageTokenUtil.RETURN_FUTURE_KLINE);
  }
}
