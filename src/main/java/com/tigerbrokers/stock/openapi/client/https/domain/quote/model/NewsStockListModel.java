package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * news_stock_list 请求参数。
 * 服务端校验：1 <= symbols.size() <= 20（必填）；page_count / page_size 可选
 * （缺省默认 1 / 20；page_count >= 1，page_size 1-50）。
 * lang 为两接口共用可选参数，见父类 ApiModel.lang
 * （Language 枚举值 zh_CN/zh_TW/en_US 与服务端值域一致；不设置 = 空串 = 中英混合内容，
 * 服务端对非法值回退 en_US）。
 */
public class NewsStockListModel extends ApiModel {

  private List<String> symbols;

  @JSONField(name = "page_count")
  private Integer pageCount;

  @JSONField(name = "page_size")
  private Integer pageSize;

  public NewsStockListModel(List<String> symbols, Integer pageCount, Integer pageSize) {
    this.symbols = symbols;
    this.pageCount = pageCount;
    this.pageSize = pageSize;
  }

  public List<String> getSymbols() {
    return symbols;
  }

  public void setSymbols(List<String> symbols) {
    this.symbols = symbols;
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
