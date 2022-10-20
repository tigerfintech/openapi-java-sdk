package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.struct.OptionSymbol;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import java.util.regex.Pattern;

/**
 * author：ltc
 * date：2019/09/04
 */
public class SymbolUtil {

  private static Pattern CHAR_SYMBOL_PATTERN = Pattern.compile("[A-Z]+(.[A-Z0-9]+)?");
  private static final Pattern FUTURE_PATTERN = Pattern.compile("^[0-9A-Z]+(main|[0-9]{4})$");
  private static final Pattern NOT_FUTURE_PATTERN = Pattern.compile("^[0-9]{5,}$");

  public static OptionSymbol convertToOptionSymbolObject(String identifier) throws TigerApiException {
    if (identifier == null || identifier.length() != 21) {
      throw new TigerApiException("option identifier format error");
    }

    String[] symbolKeys = identifier.split(" +");
    if (symbolKeys != null && symbolKeys.length != 1 && symbolKeys.length != 2) {
      throw new TigerApiException("option identifier format error");
    }

    String expiryRightStrike = identifier.substring(6);

    OptionSymbol optionSymbol = new OptionSymbol();
    optionSymbol.setSymbol(identifier.substring(0, 6).trim());
    optionSymbol.setExpiry("20" + expiryRightStrike.substring(0, 2) + "-"
        + expiryRightStrike.substring(2, 4) + "-"
        + expiryRightStrike.substring(4, 6));
    optionSymbol.setRight(expiryRightStrike.substring(6, 7).equals("C") ? "CALL" : "PUT");
    optionSymbol.setStrike(
        Integer.parseInt(expiryRightStrike.substring(7, 12)) + "." + expiryRightStrike.substring(12, 13));

    return optionSymbol;
  }

  public static boolean isUsStockSymbol(String symbol) {
    if (symbol == null || symbol.isEmpty()) {
      return false;
    }
    if (CHAR_SYMBOL_PATTERN.matcher(symbol).matches()) {
      return true;
    }
    return false;
  }

  public static TimeZoneId getZoneIdBySymbol(String symbol) {
    if (StringUtils.isEmpty(symbol)) {
      return ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone();
    }
    return SymbolUtil.isUsStockSymbol(symbol) ? TimeZoneId.NewYork : TimeZoneId.Shanghai;
  }

  public static boolean isFutureSymbol(String symbol) {
    if (null != symbol && symbol.length() > 4 && !symbol.startsWith("BK") && symbol.length() < 12) {
      if (NOT_FUTURE_PATTERN.matcher(symbol).matches()) {
        return false;
      } else {
        return FUTURE_PATTERN.matcher(symbol).matches();
      }
    } else {
      return false;
    }
  }
}
