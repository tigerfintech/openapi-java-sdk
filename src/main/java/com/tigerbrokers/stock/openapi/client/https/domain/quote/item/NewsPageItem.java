package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import java.util.List;

/**
 * 资讯列表分页信封，news_stock_list 与 news_highlight_list 共用。
 * 服务端响应为 camelCase，字段名与 JSON key 相同，无需 @JSONField。
 */
public class NewsPageItem {

  private List<NewsItem> items;

  private Integer pageCount;

  private Integer pageSize;

  private Integer totalPage;

  private Integer totalSize;

  public List<NewsItem> getItems() {
    return items;
  }

  public void setItems(List<NewsItem> items) {
    this.items = items;
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

  public Integer getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(Integer totalPage) {
    this.totalPage = totalPage;
  }

  public Integer getTotalSize() {
    return totalSize;
  }

  public void setTotalSize(Integer totalSize) {
    this.totalSize = totalSize;
  }
}
