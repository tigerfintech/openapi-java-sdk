package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

import java.util.List;

public class OptionExerciseRecordPageItem {

  private Integer pageNum;
  private Integer pageSize;
  private Integer itemCount;
  private Integer pageCount;
  private List<OptionExerciseRecordItem> items;

  public Integer getPageNum() {
    return pageNum;
  }

  public void setPageNum(Integer pageNum) {
    this.pageNum = pageNum;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getItemCount() {
    return itemCount;
  }

  public void setItemCount(Integer itemCount) {
    this.itemCount = itemCount;
  }

  public Integer getPageCount() {
    return pageCount;
  }

  public void setPageCount(Integer pageCount) {
    this.pageCount = pageCount;
  }

  public List<OptionExerciseRecordItem> getItems() {
    return items;
  }

  public void setItems(List<OptionExerciseRecordItem> items) {
    this.items = items;
  }
}
