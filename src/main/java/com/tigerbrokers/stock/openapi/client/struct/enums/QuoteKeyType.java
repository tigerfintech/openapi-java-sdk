package com.tigerbrokers.stock.openapi.client.struct.enums;

import com.tigerbrokers.stock.openapi.client.constant.QuoteChangeKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by liutongping on 2021/11/01.
 */
public enum QuoteKeyType {

  /** 成交数据,包含的字段: open,high,low,close,preClose,volume,latestPrice,latestTime  */
  TRADE("TRADE", QuoteChangeKey.open, QuoteChangeKey.high, QuoteChangeKey.low, QuoteChangeKey.close,
      QuoteChangeKey.preClose, QuoteChangeKey.volume, QuoteChangeKey.latestPrice, QuoteChangeKey.latestTime),
  /** 盘口数据,包含的字段: askPrice,askSize,bidPrice,bidSize */
  QUOTE("QUOTE", QuoteChangeKey.askPrice, QuoteChangeKey.askSize, QuoteChangeKey.bidPrice, QuoteChangeKey.bidSize),
  /** 分时数据,包含的字段: p: 最新价； a:当日截至当前的平均价; t:所属分钟的时间戳; v: 成交量 */
  TIMELINE("TIMELINE", QuoteChangeKey.minute),
  ALL("ALL", QuoteChangeKey.open, QuoteChangeKey.high, QuoteChangeKey.low, QuoteChangeKey.close,
      QuoteChangeKey.preClose, QuoteChangeKey.volume, QuoteChangeKey.latestPrice, QuoteChangeKey.latestTime,
      QuoteChangeKey.askPrice, QuoteChangeKey.askSize, QuoteChangeKey.bidPrice, QuoteChangeKey.bidSize,
      QuoteChangeKey.minute),
  ;

  private final String keyType;
  private final List<String> focusKeys;

  QuoteKeyType(String keyType, String... focusKey) {
    this.keyType = keyType;
    this.focusKeys = new ArrayList<>();
    if (focusKey != null) {
      for (String key : focusKey) {
        focusKeys.add(key);
      }
    }
  }

  public String getKeyType() {
    return keyType;
  }

  public List<String> getFocusKeys() {
    return focusKeys;
  }
}
