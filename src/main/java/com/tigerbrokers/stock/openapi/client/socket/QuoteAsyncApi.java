package com.tigerbrokers.stock.openapi.client.socket;

import com.tigerbrokers.stock.openapi.client.struct.param.QuoteParameter;

/**
 * Description:
 * Created by lijiawen on 2018/06/14.
 */
public interface QuoteAsyncApi {

  /**
   * 获取市场状态
   *
   * @param parameter quote param
   */
  String getMarketState(QuoteParameter parameter);

  /**
   * 获取代号列表
   *
   * @param parameter quote param
   */
  String getAllSymbols(QuoteParameter parameter);

  /**
   * 获取代号列表和中文名称
   *
   * @param parameter quote param
   */
  String getAllSymbolNames(QuoteParameter parameter);

  /**
   * 行情摘要
   *
   * @param parameter quote param
   */
  String getBriefInfo(QuoteParameter parameter);

  /**
   * 股票详情
   *
   * @param parameter quote param
   */
  String getStockDetail(QuoteParameter parameter);

  /**
   * 分时数据
   *
   * @param parameter quote param
   */
  String getTimeline(QuoteParameter parameter);

  /**
   * 盘前盘后分时数据
   *
   * @param parameter quote param
   */
  String getHourTradingTimeline(QuoteParameter parameter);

  /**
   * K线数据
   *
   * @param parameter quote param
   */
  String getKline(QuoteParameter parameter);

  /**
   * 逐笔数据
   *
   * @param parameter quote param
   */
  String getTradeTick(QuoteParameter parameter);
}
