package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

import java.util.List;

/**
 * Created on 2025/4/23
 *
 * @author: sukai
 */
public class FundDetailsPageItem extends ApiModel {
  private Long page;
  private Long limit;
  private Long itemCount;
  private Long pageCount;
  private Long timestamp;
  private List<FundDetailsItem> items;

  public Long getPage() {
    return page;
  }

  public void setPage(Long page) {
    this.page = page;
  }

  public Long getLimit() {
    return limit;
  }

  public void setLimit(Long limit) {
    this.limit = limit;
  }

  public Long getItemCount() {
    return itemCount;
  }

  public void setItemCount(Long itemCount) {
    this.itemCount = itemCount;
  }

  public Long getPageCount() {
    return pageCount;
  }

  public void setPageCount(Long pageCount) {
    this.pageCount = pageCount;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public List<FundDetailsItem> getItems() {
    return items;
  }

  public void setItems(List<FundDetailsItem> items) {
    this.items = items;
  }
}
