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

  private List<String> details;

  private List<SymbolDetail> symbolDetails;

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

  public List<String> getDetails() {
    return details;
  }

  public void setDetails(List<String> details) {
    this.details = details;
  }

  public List<SymbolDetail> getSymbolDetails() {
    return symbolDetails;
  }

  public void setSymbolDetails(List<SymbolDetail> symbolDetails) {
    this.symbolDetails = symbolDetails;
  }

  public static class SymbolDetail {

    private String code;
    private String lastRequestTimestamp;

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }

    public String getLastRequestTimestamp() {
      return lastRequestTimestamp;
    }

    public void setLastRequestTimestamp(String lastRequestTimestamp) {
      this.lastRequestTimestamp = lastRequestTimestamp;
    }
  }
}
