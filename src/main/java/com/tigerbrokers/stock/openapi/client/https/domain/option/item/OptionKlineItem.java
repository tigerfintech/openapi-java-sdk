package com.tigerbrokers.stock.openapi.client.https.domain.option.item;

import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.KlineItem;

/**
 * Description:
 * Created by lijiawen on 2019/01/07.
 */
public class OptionKlineItem extends KlineItem {

  /**
   * 股票代码
   */
  private String symbol;

  /**
   * 到期时间，毫秒,当天0点
   */
  private Long expiry;

  /**
   * 看多或看空  CALL  PUT
   */
  private String right;

  /**
   * 行权价
   */
  private String strike;
}
