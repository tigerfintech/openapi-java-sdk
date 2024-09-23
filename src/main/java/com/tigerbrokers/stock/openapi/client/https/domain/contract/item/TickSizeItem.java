package com.tigerbrokers.stock.openapi.client.https.domain.contract.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.TickSizeType;

/**
 * @author bean
 * @date 2022/7/8 下午5:20
 */
public class TickSizeItem extends ApiModel {

  private String begin;
  private String end;
  private TickSizeType type;
  private Double tickSize;

  public String getBegin() {
    return begin;
  }

  public void setBegin(String begin) {
    this.begin = begin;
  }

  public String getEnd() {
    return end;
  }

  public void setEnd(String end) {
    this.end = end;
  }

  public TickSizeType getType() {
    return type;
  }

  public void setType(TickSizeType type) {
    this.type = type;
  }

  public Double getTickSize() {
    return tickSize;
  }

  public void setTickSize(Double tickSize) {
    this.tickSize = tickSize;
  }
}
