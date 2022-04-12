package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.HistoryTimelineItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2022/04/12.
 */
public class QuoteHistoryTimelineResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<HistoryTimelineItem> timelineItems;

  public List<HistoryTimelineItem> getTimelineItems() {
    return timelineItems;
  }

  public void setTimelineItems(List<HistoryTimelineItem> timelineItems) {
    this.timelineItems = timelineItems;
  }
}
