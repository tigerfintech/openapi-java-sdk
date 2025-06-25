package com.tigerbrokers.stock.openapi.client.https.response.option;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionTimelineItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

import java.util.List;

public class OptionTimelineResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<OptionTimelineItem> timelineItems;

  public List<OptionTimelineItem> getTimelineItems() {
    return timelineItems;
  }

  public void setTimelineItems(List<OptionTimelineItem> timelineItems) {
    this.timelineItems = timelineItems;
  }
}