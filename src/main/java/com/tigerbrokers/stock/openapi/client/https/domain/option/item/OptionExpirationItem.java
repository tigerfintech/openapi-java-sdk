package com.tigerbrokers.stock.openapi.client.https.domain.option.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/26.
 */
public class OptionExpirationItem extends ApiModel {

  private String symbol;
  private List<String> dates;
  private List<Long> timestamps;
  private List<String> periodTags;
  private Integer count;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public List<String> getDates() {
    return dates;
  }

  public void setDates(List<String> dates) {
    this.dates = dates;
  }

  public List<Long> getTimestamps() {
    return timestamps;
  }

  public void setTimestamps(List<Long> timestamps) {
    this.timestamps = timestamps;
  }

  public List<String> getPeriodTags() {
    return periodTags;
  }

  public void setPeriodTags(List<String> periodTags) {
    this.periodTags = periodTags;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  @Override
  public String toString() {
    return "OptionExpirationItem{" +
        "symbol='" + symbol + '\'' +
        ", dates=" + dates +
        ", timestamps=" + timestamps +
        ", count=" + count +
        '}';
  }
}
