package com.tigerbrokers.stock.openapi.client.https.domain.option.item;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 * Created on 2023/02/02.
 */
public class WarrantFilterItem implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer page;

  private Integer totalPage;

  private Integer totalCount;

  private List<WarrantItem> items;

  private FilterBounds bounds;

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(Integer totalPage) {
    this.totalPage = totalPage;
  }

  public Integer getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  public List<WarrantItem> getItems() {
    return items;
  }

  public void setItems(List<WarrantItem> items) {
    this.items = items;
  }

  public FilterBounds getBounds() {
    return bounds;
  }

  public void setBounds(FilterBounds bounds) {
    this.bounds = bounds;
  }
}
