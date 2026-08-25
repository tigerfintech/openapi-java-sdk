package com.tigerbrokers.stock.openapi.client.util;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
public class DateUtilsTest {
  @Test
  public void testGetZoneDate_null() { Assert.assertNull(DateUtils.getZoneDate(null, TimeZoneId.NewYork)); }
  @Test
  public void testGetZoneDate_invalidLength() { Assert.assertNull(DateUtils.getZoneDate("2022", TimeZoneId.NewYork)); }
  @Test
  public void testGetZoneDate_dateOnly() { Assert.assertNotNull(DateUtils.getZoneDate("2022-04-25", TimeZoneId.NewYork)); }
  @Test
  public void testGetZoneDate_fullDateTime() { Assert.assertNotNull(DateUtils.getZoneDate("2022-04-25 00:00:00", TimeZoneId.NewYork)); }
  @Test
  public void testGetZoneDate_nullZoneId() { Assert.assertNull(DateUtils.getZoneDate("2022-04-25", null)); }
  @Test
  public void testGetZoneDate_invalidFormat() { Assert.assertNull(DateUtils.getZoneDate("invalid-date", TimeZoneId.NewYork)); }
  @Test
  public void testGetTimestamp_valid() { Assert.assertTrue(DateUtils.getTimestamp("2022-04-25", TimeZoneId.NewYork) > 0); }
  @Test
  public void testGetTimestamp_null() { Assert.assertNull(DateUtils.getTimestamp(null, TimeZoneId.NewYork)); }
  @Test
  public void testIsDateBeforeToday_pastDate() { Assert.assertTrue(DateUtils.isDateBeforeToday("2000-01-01", TimeZoneId.NewYork)); }
  @Test
  public void testIsDateBeforeToday_futureDate() { Assert.assertFalse(DateUtils.isDateBeforeToday("2099-12-31", TimeZoneId.NewYork)); }
  @Test
  public void testIsDateBeforeToday_null() { Assert.assertFalse(DateUtils.isDateBeforeToday(null)); }
  @Test
  public void testIsDateBeforeToday_empty() { Assert.assertFalse(DateUtils.isDateBeforeToday("")); }
  @Test
  public void testParseEpochMill_stringNull() { Assert.assertEquals(0, DateUtils.parseEpochMill((String)null, TimeZoneId.NewYork)); }
  @Test
  public void testParseEpochMill_localDateNull() { Assert.assertEquals(0, DateUtils.parseEpochMill((LocalDate)null, TimeZoneId.NewYork)); }
  @Test
  public void testParseEpochMill_valid() { Assert.assertTrue(DateUtils.parseEpochMill("2022-04-25", TimeZoneId.NewYork) > 0); }
  @Test
  public void testParseEpochMill_localDateValid() { Assert.assertTrue(DateUtils.parseEpochMill(LocalDate.of(2022,4,25), TimeZoneId.NewYork) > 0); }
  @Test
  public void testParseEpochMill_localDateDefault() { Assert.assertTrue(DateUtils.parseEpochMill(LocalDate.of(2022,4,25)) > 0); }
  @Test
  public void testPrintTimeZoneET() { Assert.assertTrue(DateUtils.printTimeZoneET(1659388221000L).contains("2022")); }
  @Test
  public void testPrintDateTime() { Assert.assertTrue(DateUtils.printDateTime(1659388221000L, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"), TimeZoneId.NewYork).contains("2022")); }
  @Test
  public void testPrintDateTime_nullTimeZone() { Assert.assertNotNull(DateUtils.printDateTime(1659388221000L, DateTimeFormatter.ofPattern("yyyy-MM-dd"), null)); }
  @Test
  public void testPrintDate_nullTimeZone() { Assert.assertNotNull(DateUtils.printDate(1659388221000L, null)); }
  @Test
  public void testPrintDate_otherTimeZone() { Assert.assertTrue(DateUtils.printDate(1659388221000L, TimeZoneId.HongKong).contains("2022")); }
  @Test
  public void testPrintSystemDate() { Assert.assertEquals(10, DateUtils.printSystemDate().length()); }
  @Test
  public void testPrintSystemDate_withZone() { Assert.assertEquals(10, DateUtils.printSystemDate(TimeZoneId.NewYork).length()); }
}
