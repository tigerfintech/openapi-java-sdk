package com.tigerbrokers.stock.openapi.client.util;

/**
 * 字符串工具类。
 */
public abstract class StringUtils {

  private StringUtils() {
  }

  public static boolean isEmpty(String value) {
    int strLen;
    if (value == null || (strLen = value.length()) == 0) {
      return true;
    }
    for (int i = 0; i < strLen; i++) {
      if ((Character.isWhitespace(value.charAt(i)) == false)) {
        return false;
      }
    }
    return true;
  }

  public static boolean areNotEmpty(String... values) {
    boolean result = true;
    if (values == null || values.length == 0) {
      result = false;
    } else {
      for (String value : values) {
        result &= !isEmpty(value);
      }
    }
    return result;
  }

  public static boolean isNumeric(CharSequence cs) {
    if (cs == null || cs.length() == 0) {
      return false;
    } else {
      int sz = cs.length();

      for(int i = 0; i < sz; ++i) {
        if (!Character.isDigit(cs.charAt(i))) {
          return false;
        }
      }

      return true;
    }
  }
}
