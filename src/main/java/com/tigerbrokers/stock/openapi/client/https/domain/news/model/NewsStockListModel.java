package com.tigerbrokers.stock.openapi.client.https.domain.news.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * news_stock_list 请求参数。
 * symbol 单标的（必填、非空）——下游资讯接口实际不支持批量查询，只收一个标的；
 * page_count / page_size 可选
 * （缺省默认 1 / 20；page_count >= 1，page_size 1-50）。
 * lang 为两接口共用可选参数，见父类 ApiModel.lang
 * （Language 枚举值 zh_CN/zh_TW/en_US 与服务端值域一致；不设置 = 空串 = 中英混合内容，
 * 服务端对非法值回退 en_US）。
 */
public class NewsStockListModel extends ApiModel {

  /** 必填。单标的：下游资讯接口实际不支持批量查询。 */
  private String symbol;

  @JSONField(name = "page_count")
  private Integer pageCount;

  @JSONField(name = "page_size")
  private Integer pageSize;

  public NewsStockListModel(String symbol, Integer pageCount, Integer pageSize) {
    this.symbol = symbol;
    this.pageCount = pageCount;
    this.pageSize = pageSize;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Integer getPageCount() {
    return pageCount;
  }

  public void setPageCount(Integer pageCount) {
    this.pageCount = pageCount;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }
}
