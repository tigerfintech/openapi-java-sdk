package com.tigerbrokers.stock.openapi.client.struct.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * Created by liutongping on 2022/06/20.
 */
public enum USTradeCond {
  US_AUTOMATCH_NORMAL(' '),
  US_BUNCHED_TRADE('B'),
  US_CASH_TRADE('C'),
  US_INTERMARKET_SWEEP('F'),
  US_BUNCHED_SOLD_TRADE('G'),
  US_PRICE_VARIATION_TRADE('H'),
  US_ODD_LOT_TRADE('I'),
  US_RULE_127_OR_155_TRADE('K'),
  US_SOLD_LAST('L'),
  US_MARKET_CENTER_CLOSE_PRICE('M'),
  US_NEXT_DAY_TRADE('N'),
  US_MARKET_CENTER_OPENING_TRADE('O'),
  US_PRIOR_REFERENCE_PRICE('P'),
  US_MARKET_CENTER_OPEN_PRICE('Q'),
  US_SELLER('R'),
  US_FORM_T('T'),
  US_EXTENDED_TRADING_HOURS('U'),
  US_CONTINGENT_TRADE('V'),
  US_AVERAGE_PRICE_TRADE('W'),
  US_CROSS_TRADE('X'),
  US_SOLD_OUT_OF_SEQUENCE('Z'),
  US_ODD_LOST_CROSS_TRADE('0'),
  US_DERIVATIVELY_PRICED('4'),
  US_MARKET_CENTER_RE_OPENING_TRADE('5'),
  US_MARKET_CENTER_CLOSING_TRADE('6'),
  US_QUALIFIED_CONTINGENT_TRADE('7'),
  US_CONSOLIDATED_LAST_PRICE_PER_LISTING_PACKET('9');

  private char code;

  private USTradeCond(char code) {
    this.code = code;
  }

  public char getCode() {
    return code;
  }

  private static Map<Character, USTradeCond> usTradeCondMap = new HashMap();

  static {
    for (USTradeCond item : values()) {
      usTradeCondMap.put(item.getCode(), item);
    }
  }

  public static USTradeCond getTradeCondByCode(char code) {
    USTradeCond tradeCond = usTradeCondMap.get(code);
    return tradeCond == null ? US_AUTOMATCH_NORMAL : tradeCond;
  }
}

