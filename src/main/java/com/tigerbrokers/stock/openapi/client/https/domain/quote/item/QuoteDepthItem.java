package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;
import lombok.Data;

/**
 * 作者：ltc
 * 时间：2019/08/13
 */
@Data
public class QuoteDepthItem extends ApiModel {

  /**
   * 股票代码
   */
  private String symbol;

  /**
   * 卖盘
   */
  private List<DepthEntry> asks;

  /**
   * 买盘
   */
  private List<DepthEntry> bids;
}
