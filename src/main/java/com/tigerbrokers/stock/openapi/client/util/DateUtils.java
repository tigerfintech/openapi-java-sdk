package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import java.time.LocalDate;
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

  private static final String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
  private static final String SUFFIX = " 00:00:00";
  private static final String FORMAT_DATE = "yyyy-MM-dd";
  public static final DateTimeFormatter DATE_FORMAT =
      DateTimeFormatter.ofPattern(FORMAT_DATE).withZone(ZoneId.of(TimeZoneId.Shanghai.getZoneId()));
  public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern(FORMAT_FULL);

  public static Date getZoneDate(String time, TimeZoneId zoneId) {
    if (time == null || (time.length() != 10 && time.length() != 19) || zoneId == null) {
      return null;
    }

    if (time.length() == 10) {
      time = time + SUFFIX;
    }

    try {
      ZonedDateTime zdt = LocalDateTime.parse(time, DATETIME_FORMAT).atZone(ZoneId.of(zoneId.getZoneId()));
      return Date.from(zdt.toInstant());
    } catch (Exception ex) {
      return null;
    }
  }

  public static boolean isDateBeforeToday(String date) {
    if (date == null || date.isEmpty()) {
      return false;
    }
    LocalDate expiryDate = LocalDate.parse(date, DATE_FORMAT);
    LocalDate now = LocalDate.now(ZoneId.of(TimeZoneId.Shanghai.getZoneId()));
    if (now.compareTo(expiryDate) > 0) {
      return false;
    }
    return true;
  }

  public static long parseEpochMill(String date) {
    if (date == null) {
      return 0;
    }
    return parseEpochMill(LocalDate.parse(date, DATE_FORMAT));
  }

  public static long parseEpochMill(LocalDate localDate) {
    if (localDate == null) {
      return 0;
    }
    return localDate.atStartOfDay(ZoneId.of(TimeZoneId.Shanghai.getZoneId())).toInstant().toEpochMilli();
  }
}
