package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author bean
 * @date 2022/8/2
 */
@RunWith(MockitoJUnitRunner.class)
public class DateUtilsUtilTest {

  @Test
  public void testPrintDate() {
    long time = 1659388221000L;
    String shanghaiDate = DateUtils.printDate(time, TimeZoneId.Shanghai);
    Assert.assertEquals("2022-08-02", shanghaiDate);

    String newYorkDate = DateUtils.printDate(time, TimeZoneId.NewYork);
    Assert.assertEquals("2022-08-01", newYorkDate);
  }
}
