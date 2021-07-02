package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.struct.OptionSymbol;

/**
 * 作者：ltc
 * 时间：2019/09/04
 */
public class SymbolUtil {

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
}
