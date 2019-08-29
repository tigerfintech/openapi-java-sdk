package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Description:
 * Created by lijiawen on 2018/06/22.
 */
public class DateUtils {

  public static final String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
  private static final String SUFFIX = " 00:00:00";

  public static Date getZoneDate(String time, TimeZoneId zoneId) {
    if (time == null || (time.length() != 10 && time.length() != 19) || zoneId == null) {
      return null;
    }

    if (time.length() == 10) {
      time = time + SUFFIX;
    }

    try {
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern(FORMAT_FULL);
      ZonedDateTime zdt = LocalDateTime.parse(time, dtf).atZone(ZoneId.of(zoneId.getZoneId()));
      return Date.from(zdt.toInstant());
    } catch (Exception ex) {
      return null;
    }
  }
}
