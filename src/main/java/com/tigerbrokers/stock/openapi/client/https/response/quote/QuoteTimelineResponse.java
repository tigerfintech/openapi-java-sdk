package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.TimelineItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteTimelineResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<TimelineItem> timelineItems;

  public List<TimelineItem> getTimelineItems() {
    return timelineItems;
  }

  public void setTimelineItems(List<TimelineItem> timelineItems) {
    this.timelineItems = timelineItems;
  }
}
