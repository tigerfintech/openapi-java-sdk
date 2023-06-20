package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

public class PositionsItem extends ApiModel {
  @JSONField(name = "items")
  private List<PositionDetail> positions;

  public List<PositionDetail> getPositions() {
    return positions;
  }

  public void setPositions(List<PositionDetail> positions) {
    this.positions = positions;
  }
}
