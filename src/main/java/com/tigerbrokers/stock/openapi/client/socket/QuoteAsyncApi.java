package com.tigerbrokers.stock.openapi.client.socket;

import com.tigerbrokers.stock.openapi.client.struct.param.QuoteParameter;
import java.util.List;
import java.util.Set;

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
  void getMarketState(QuoteParameter parameter);

  /**
   * 获取代号列表
   *
   * @param parameter quote param
   */
  void getAllSymbols(QuoteParameter parameter);

  /**
   * 获取代号列表和中文名称
   *
   * @param parameter quote param
   */
  void getAllSymbolNames(QuoteParameter parameter);

  /**
   * 行情摘要
   *
   * @param parameter quote param
   */
  void getBriefInfo(QuoteParameter parameter);

  /**
   * 股票详情
   *
   * @param parameter quote param
   */
  void getStockDetail(QuoteParameter parameter);

  /**
   * 分时数据
   *
   * @param parameter quote param
   */
  void getTimeline(QuoteParameter parameter);

  /**
   * 盘前盘后分时数据
   *
   * @param parameter quote param
   */
  void getHourTradingTimeline(QuoteParameter parameter);

  /**
   * K线数据
   *
   * @param parameter quote param
   */
  void getKline(QuoteParameter parameter);

  /**
   * 逐笔数据
   *
   * @param parameter quote param
   */
  void getTradeTick(QuoteParameter parameter);
}
