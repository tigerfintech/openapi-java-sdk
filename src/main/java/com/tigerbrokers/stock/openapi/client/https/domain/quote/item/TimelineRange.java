package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class TimelineRange implements Serializable {

  /**
   * 分时数据数组
   */
  private List<TimelinePoint> items;

  private Long beginTime;

  private Long endTime;

  public List<TimelinePoint> getItems() {
    return items;
  }

  public void setItems(List<TimelinePoint> items) {
    this.items = items;
  }

  public Long getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(Long beginTime) {
    this.beginTime = beginTime;
  }

  public Long getEndTime() {
    return endTime;
  }

  public void setEndTime(Long endTime) {
    this.endTime = endTime;
  }

  @Override
  public String toString() {
    return "TimelineRange{" +
        "items=" + items +
        ", beginTime=" + beginTime +
        ", endTime=" + endTime +
        '}';
  }
}
