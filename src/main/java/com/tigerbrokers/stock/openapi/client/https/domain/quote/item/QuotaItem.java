package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * Description:
 * Created by bean on 2023/06/13.
 */
public class QuotaItem extends ApiModel {

  private int remain;

  private int used;

  private String method;

  private List<DetailItem> details;

  public int getRemain() {
    return remain;
  }

  public void setRemain(int remain) {
    this.remain = remain;
  }

  public int getUsed() {
    return used;
  }

  public void setUsed(int used) {
    this.used = used;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public List<DetailItem> getDetails() {
    return details;
  }

  public void setDetails(
      List<DetailItem> details) {
    this.details = details;
  }

  public static class DetailItem {
    private String symbol;
    private String time;
    private long timestamp;

    public String getSymbol() {
      return symbol;
    }

    public void setSymbol(String symbol) {
      this.symbol = symbol;
    }

    public String getTime() {
      return time;
    }

    public void setTime(String time) {
      this.time = time;
    }

    public long getTimestamp() {
      return timestamp;
    }

    public void setTimestamp(long timestamp) {
      this.timestamp = timestamp;
    }
  }
}
