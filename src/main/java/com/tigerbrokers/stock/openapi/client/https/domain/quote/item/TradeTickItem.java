package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class TradeTickItem extends ApiModel {

  private Long beginIndex;

  private Long endIndex;

  private List<TickPoint> items;

  public Long getBeginIndex() {
    return beginIndex;
  }

  public void setBeginIndex(Long beginIndex) {
    this.beginIndex = beginIndex;
  }

  public Long getEndIndex() {
    return endIndex;
  }

  public void setEndIndex(Long endIndex) {
    this.endIndex = endIndex;
  }

  public List<TickPoint> getItems() {
    return items;
  }

  public void setItems(List<TickPoint> items) {
    this.items = items;
  }

  @Override
  public String toString() {
    return "TradeTickItem{" +
        "beginIndex=" + beginIndex +
        ", endIndex=" + endIndex +
        ", items=" + items +
        '}';
  }
}
