package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import java.time.Instant;
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
  private static final String FORMAT_FULL_WITH_ZONE = "yyyy-MM-dd HH:mm:ss.SSS";
  private static final String SUFFIX = " 00:00:00";
  private static final String FORMAT_DATE = "yyyy-MM-dd";
  public static final DateTimeFormatter DATE_FORMAT =
      DateTimeFormatter.ofPattern(FORMAT_DATE).withZone(ZoneId.of(TimeZoneId.Shanghai.getZoneId()));
  public static final DateTimeFormatter DATE_FORMAT_NY =
      DateTimeFormatter.ofPattern(FORMAT_DATE).withZone(ZoneId.of(TimeZoneId.NewYork.getZoneId()));
  public static final DateTimeFormatter DATE_FORMAT_EST =
      DateTimeFormatter.ofPattern(FORMAT_FULL_WITH_ZONE).withZone(ZoneId.of(TimeZoneId.NewYork.getZoneId()));
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

  public static Long getTimestamp(String time, TimeZoneId zoneId) {
    Date date = DateUtils.getZoneDate(time, zoneId);
    return date == null ? null : date.getTime();
  }

  /**
   * Is the date before today
   * @param date "yyyy-MM-dd"
   * @return
   */
  public static boolean isDateBeforeToday(String date) {
    return isDateBeforeToday(date, ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone());
  }

  /**
   * Is the date before today
   * @param date "yyyy-MM-dd"
   * @param zoneId TimeZoneId
   * @return
   */
  public static boolean isDateBeforeToday(String date, TimeZoneId zoneId) {
    if (date == null || date.isEmpty()) {
      return false;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATE)
        .withZone(ZoneId.of(zoneId.getZoneId()));
    LocalDate expiryDate = LocalDate.parse(date, formatter);
    LocalDate now = LocalDate.now(ZoneId.of(zoneId.getZoneId()));
    if (now.compareTo(expiryDate) > 0) {
      return false;
    }
    return true;
  }

  /**
   * parse date
   * @param date "yyyy-MM-dd"
   * @param zoneId TimeZoneId
   * @return
   */
  public static long parseEpochMill(String date, TimeZoneId zoneId) {
    if (date == null) {
      return 0;
    }
    return parseEpochMill(LocalDate.parse(date, DATE_FORMAT), zoneId);
  }

  public static long parseEpochMill(LocalDate localDate) {
    return parseEpochMill(localDate, ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone());
  }

  public static long parseEpochMill(LocalDate localDate, TimeZoneId zoneId) {
    if (localDate == null) {
      return 0;
    }
    return localDate.atStartOfDay(ZoneId.of(zoneId.getZoneId())).toInstant().toEpochMilli();
  }

  /**
   * Convert to Eastern Time
   * @param timestamp
   * @return
   */
  public static String printTimeZoneET(long timestamp) {
    return DATE_FORMAT_EST.format(Instant.ofEpochMilli(timestamp));
  }

  /**
   * Convert to yyyy-MM-dd
   * @param timestamp
   * @return
   */
  public static String printDate(long timestamp, TimeZoneId timeZoneId) {
    timeZoneId = timeZoneId == null ? ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone() : timeZoneId;
    if (TimeZoneId.Shanghai == timeZoneId) {
      return DATE_FORMAT.format(Instant.ofEpochMilli(timestamp));
    } else if (TimeZoneId.NewYork == timeZoneId) {
      return DATE_FORMAT_NY.format(Instant.ofEpochMilli(timestamp));
    } else {
      return DateTimeFormatter.ofPattern(FORMAT_DATE).withZone(ZoneId.of(timeZoneId.getZoneId()))
          .format(Instant.ofEpochMilli(timestamp));
    }
  }

  /**
   * get system date(yyyy-MM-dd)
   * @return
   */
  public static String printSystemDate() {
    return printDate(System.currentTimeMillis(), ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone());
  }
}
