package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import lombok.Data;

/**
 * Description:
 * Created by sk on 2021/11/18.
 */
@Data
public class QuoteDelayItem extends ApiModel {

  /**
   * 股票代码
   */
  private String symbol;

  /**
   * 开盘价
   */
  private Double open;

  /**
   * 最高价
   */
  private Double high;

  /**
   * 最低价
   */
  private Double low;

  /**
   * 收盘价
   */
  private Double close;

  /**
   * 昨日收盘价
   */
  private Double preClose;

  /**
   * 个股状态 (0: 正常 3: 停牌 4: 退市 7: 新股 8: 变更)
   */
  private Double halted;

  /**
   * 最后的行情时间
   */
  private Long time;

  /**
   * 当日成交量
   */
  private Long volume;

}
