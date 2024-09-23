package com.tigerbrokers.stock.openapi.client.https.domain.future.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by bean on 2023/08/29.
 */
public class FutureHistoryContractItem extends ApiModel {

  private long time;
  private String referContractCode;

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public String getReferContractCode() {
    return referContractCode;
  }

  public void setReferContractCode(String referContractCode) {
    this.referContractCode = referContractCode;
  }

  @Override
  public String toString() {
    return "FutureKlineItem{" +
        "time=" + time +
        ", referContractCode=" + referContractCode +
        '}';
  }
}
