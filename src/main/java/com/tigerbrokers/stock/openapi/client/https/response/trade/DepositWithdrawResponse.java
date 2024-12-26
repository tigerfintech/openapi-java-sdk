package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.DepositWithdrawItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

import java.util.List;

/**
 * author：bean
 * date：2024/12/19
 */
public class DepositWithdrawResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<DepositWithdrawItem> depositWithdrawItems;

  public List<DepositWithdrawItem> getDepositWithdrawItems() {
    return depositWithdrawItems;
  }

  public void setDepositWithdrawItems(List<DepositWithdrawItem> depositWithdrawItems) {
    this.depositWithdrawItems = depositWithdrawItems;
  }
}
