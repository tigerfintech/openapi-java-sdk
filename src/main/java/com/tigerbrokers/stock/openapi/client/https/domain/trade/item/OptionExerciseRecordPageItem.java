package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;

public class OptionExerciseRecordPageItem {

  @JSONField(name = "pageNum")
  private Integer pageNum;
  @JSONField(name = "pageSize")
  private Integer pageSize;
  @JSONField(name = "itemCount")
  private Integer itemCount;
  @JSONField(name = "pageCount")
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
