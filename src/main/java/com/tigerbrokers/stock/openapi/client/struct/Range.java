package com.tigerbrokers.stock.openapi.client.struct;

import java.io.Serializable;

/**
 * @author liutongping
 * @date 2023/2/2 3:31 PM
 */
public class Range<T> implements Serializable {
  private static final long serialVersionUID = 1L;

  private T min;
  private T max;

  public Range() {
  }

  public Range(T min, T max) {
    this.min = min;
    this.max = max;
  }

  public T getMax() {
    return max;
  }

  public void setMax(T max) {
    this.max = max;
  }

  public T getMin() {
    return min;
  }

  public void setMin(T min) {
    this.min = min;
  }

  @Override
  public String toString() {
    return "Range{" +
        "min=" + min +
        ", max=" + max +
        '}';
  }
}
