package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Description:
 * Created by lijiawen on 2018/06/22.
 */
public class DateUtils {

  public static final String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
  public static final String FORMAT_YYYYMMDD = "yyyy-MM-dd";

  public static boolean isCustomizedFormat(String str, String format) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    try {
      dateFormat.setLenient(false);
      dateFormat.parse(str);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public static Date setZoneDate(String time, TimeZoneId zoneId) {
    if (time == null || (time.length() != 10 && time.length() != 19)) {
      return null;
    }
    String formatString = time.length() == 10 ? DateUtils.FORMAT_YYYYMMDD : DateUtils.FORMAT_FULL;

    if (isCustomizedFormat(time, formatString)) {
      SimpleDateFormat format = new SimpleDateFormat(formatString);
      format.setTimeZone(TimeZone.getTimeZone(zoneId.getZoneId()));
      try {
        return format.parse(time);
      } catch (ParseException e) {
        return null;
      }
    }
    return null;
  }
}
