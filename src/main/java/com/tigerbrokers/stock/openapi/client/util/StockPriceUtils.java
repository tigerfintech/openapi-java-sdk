package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.TickSizeItem;
import com.tigerbrokers.stock.openapi.client.struct.enums.TickSizeType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Stock price check Util Class
 */
public class StockPriceUtils {
  private static final String INFINITY = "Infinity";
  private static final BigDecimal MAX_PRICE = BigDecimal.valueOf(Double.MAX_VALUE);

  private StockPriceUtils() {
  }

  public static boolean matchTickSize(Double price, List<TickSizeItem> tickSizes) {
    if (price == null) {
      return false;
    }
    Double fixedPrice = fixPriceByTickSize(price, tickSizes);
    BigDecimal difference = BigDecimal.valueOf(price).subtract(BigDecimal.valueOf(fixedPrice));
    return difference.setScale(6, RoundingMode.DOWN).compareTo(BigDecimal.ZERO) == 0;
  }

  public static Double fixPriceByTickSize(Double price, List<TickSizeItem> tickSizes) {
    return fixPriceByTickSize(price, tickSizes, false);
  }

  /**
   * fix price by min tick
   * @param price
   * @param tickSizes
   * @param up default value:false. true:increments the price, false:decrease the price
   * @return
   */
  public static Double fixPriceByTickSize(Double price, List<TickSizeItem> tickSizes, boolean up) {
    if (price == null) {
      return null;
    }
    BigDecimal priceValue = BigDecimal.valueOf(price);
    // ignore
    TickSizeItem curItem = findTickSize(priceValue, tickSizes);
    if (curItem == null) {
      return price;
    }

    BigDecimal minTick = BigDecimal.valueOf(curItem.getTickSize());
    BigDecimal begin = new BigDecimal(curItem.getBegin());
    BigDecimal multiple = priceValue.subtract(begin).divide(minTick);
    if (multiple.stripTrailingZeros().scale() <= 0) {
      return priceValue.stripTrailingZeros().doubleValue();
    }
    if (up) {
      multiple = multiple.add(BigDecimal.ONE);
    }

    return minTick.multiply(multiple.setScale(0, RoundingMode.DOWN)).add(begin).stripTrailingZeros().doubleValue();
  }

  private static TickSizeItem findTickSize(BigDecimal price, List<TickSizeItem> tickSizes) {
    if (price == null || tickSizes == null || tickSizes.size() == 0) {
      return null;
    }
    for (TickSizeItem item : tickSizes) {
      BigDecimal begin = new BigDecimal(item.getBegin());
      BigDecimal end = INFINITY.equals(item.getEnd()) ? MAX_PRICE : new BigDecimal(item.getEnd());
      TickSizeType type = item.getType();

      if (TickSizeType.OPEN == type) {
        if (begin.compareTo(price) < 0 && end.compareTo(price) > 0) {
          return item;
        }
      } else if (TickSizeType.OPEN_CLOSED == type) {
        if (begin.compareTo(price) < 0 && end.compareTo(price) >= 0) {
          return item;
        }
      } else if (TickSizeType.CLOSED_OPEN == type ) {
        if (begin.compareTo(price) <= 0 && end.compareTo(price) > 0) {
          return item;
        }
      } else if (TickSizeType.CLOSED == type) {
        if (begin.compareTo(price) <= 0 && end.compareTo(price) >= 0) {
          return item;
        }
      }
    }
    return null;
  }
}
